package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.consumer.contract.dao.SpotDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ServiceLocation {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private SpotDao spotDao;

    @Autowired
    private ServiceSpot serviceSpot;

    @Autowired
    private ServiceGuidebook serviceGuidebook;




    /**
     * Finds the full location details (incl. matching spots and routes) based on location
     * @param locationInput the location input entered by the user
     * @return
     */
    public List<Location> detailledInfoBasedOnLocation(String locationInput) throws Exception{
       return locationDao.findLocationsByTableColomn(locationInput);
    }


    /**
     * This method takes a list of Locations as parameter and for each location and each spot remove the routes that don't
     * fulfil the research criteria (level too low or route not bolted) and remove the locations with no remaining spot after filtering.
     * @param locations
     * @param onlyBoltedRoutes
     * @param levelMin
     * @param levelMax
     * @return
     */
    public List<Location> filterLocations(List<Location> locations, boolean onlyBoltedRoutes, int levelMin, int levelMax){

        for (Iterator<Location> i = locations.iterator(); i.hasNext(); ) {
            Location location = i.next();
            List<Spot> spots = location.getSpots();
            serviceSpot.filterSpots(spots, onlyBoltedRoutes, levelMin, levelMax);
            if (spots.isEmpty()){
                i.remove();
            }
        }
     return locations;
    }


    /**
     * This method take as input a list of Location objects and remove from the list the objects whose matching Guidebook object do not
     * fulfil the "loanRequired" - requirement
     * @param locations the input list of locations
     * @param loanRequired is true if user want to display only guidebook available for loan
     * @return
     */
    public List<Location> filterLocationsForTopos (List<Location> locations, boolean loanRequired) {
        for (Iterator<Location> i = locations.iterator(); i.hasNext(); ) {
            Location location = i.next();
            List<Spot> spots = location.getSpots();

            for (Iterator<Spot> j = spots.iterator(); j.hasNext(); ) {
                Spot spot = j.next();
                List<Guidebook> guidebooks = spot.getGuidebooks();
                serviceGuidebook.filterGuidebooksByLoanAvailable(guidebooks, loanRequired);
            }
        }
        return locations;
    }

    /**
     * This method takes a list of Location objects and return a list of matching Guidebook objects without any duplicates.
     * @param locations
     * @return
     */
    public List<Guidebook> editGuidebookListWithoutDuplicate(List<Location> locations) {

        List<Guidebook> guidebooksWithoutRepetition = new ArrayList<Guidebook>();

        for (Iterator<Location> k = locations.iterator(); k.hasNext(); ) {
            Location location = k.next();
            List<Spot> spots = location.getSpots();

            for (Iterator<Spot> i = spots.iterator(); i.hasNext(); ) {
                Spot spot = i.next();
                List<Guidebook> listGuidebookToBeAdded = spot.getGuidebooks();

                for (Iterator<Guidebook> j = listGuidebookToBeAdded.iterator(); j.hasNext(); ) {
                    Guidebook guidebook = j.next();

                    if (!guidebooksWithoutRepetition.contains(guidebook)) {
                        guidebooksWithoutRepetition.add(guidebook);
                    }
                }
            }
        }
        return guidebooksWithoutRepetition;
    }

    public List<String>  getLocationProposals( String query){
        return locationDao.getLocationProposals(query);
    }

    public Location findLocationByNameAndDepartement( String cityName, String departementName){
        try {
            return locationDao.findLocationByNameAndDepartement(cityName, departementName);
        } catch (Exception e){
            return null;
        }
    }


    @Transactional
    public Spot insertLocationAndItsSpot(Location location, Spot spot) {

            Location newLocationWithKey = locationDao.insertLocation(location);
            spot.setLocation(newLocationWithKey);
            Spot newSpotwithKey = spotDao.insertSpot(spot);
            newSpotwithKey.setLocation(newLocationWithKey);
            return newSpotwithKey;

    }


    /**
     * This method takes as parameter a list of Location objects (incl. matching spot objects...) and a Guidebook object. It remove for each Location object and each
     * matching spots all the spots already included in the spots matching with the Guidebook object.
     * @param listLocations
     * @param selectedGuidebook
     * @return
     */
    public List<Location> removeSpotsAlreadyLinked(List<Location> listLocations, Guidebook selectedGuidebook){

        List <Integer> spotIdFromSelectedGuidebook = new ArrayList<>();
        for (Spot s: selectedGuidebook.getSpots()) {
            spotIdFromSelectedGuidebook.add(s.getId());
        }

        for (Iterator<Location> i = listLocations.iterator(); i.hasNext(); ) {
            Location location = i.next();
            List<Spot> spots = location.getSpots();

            for (Iterator<Spot> j = spots.iterator(); j.hasNext(); ) {
                Spot spot = j.next();
                if ((spotIdFromSelectedGuidebook.contains(spot.getId()))){
                    j.remove();
                }
            }
        }
       return listLocations;
    }


    public boolean testIfNoSpot(List<Location> listLocations) {

        for (Iterator<Location> i = listLocations.iterator(); i.hasNext(); ) {
            Location location = i.next();
            List<Spot> spots = location.getSpots();
            if (!spots.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}







