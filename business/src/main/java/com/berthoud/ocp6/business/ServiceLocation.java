package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.consumer.contract.dao.SpotDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Spot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


    private static final Logger logger = LogManager.getLogger();


    /**
     * Finds the full location details (incl. matching spots and routes) based on location
     * @param locationInput the location input entered by the user
     * @return
     */
    public List<Location> detailledInfoBasedOnLocation(String locationInput) {

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


    public List<String> getCityProposalsForUpdateSpots(String query) {
        return locationDao.getCityProposalsForUpdateSpots(query);
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
     * This method takes as input a list of Locations objects and removes all spots
     * Each location may have many spots, each spot may have many guidebooks. The methods removes from the spot objects included in the location objects all the spots already included in the spots matching with the Guidebook object.
     *
     * @param listLocations a list of locations to be cleaned
     * @param selectedGuidebook the spots that are already in this guidebook object will be removed from the list of locations
     * @return the list of location after removal of the already linked spots.
     */
    public List<Location> removeSpotsAlreadyLinked(List<Location> listLocations, Guidebook selectedGuidebook) {

        // Check if some spots are already stored in the selectedGuidebook
        if (selectedGuidebook.getSpots() == null) {
            return listLocations;
        } else {

            // if, yes get a list of the Ids of the spots already linked to the selectedGuidebook
            List<Integer> spotIdsMatchingWithSelectedGuidebook = new ArrayList<>();
            for (Spot s : selectedGuidebook.getSpots()) {
                spotIdsMatchingWithSelectedGuidebook.add(s.getId());
            }

            // the spot objects matching with these IDs are then removed from the original list of locations
            for (Iterator<Location> i = listLocations.iterator(); i.hasNext(); ) {
                Location location = i.next();
                List<Spot> spots = location.getSpots();

                for (Iterator<Spot> j = spots.iterator(); j.hasNext(); ) {
                    Spot spot = j.next();
                    if ((spotIdsMatchingWithSelectedGuidebook.contains(spot.getId()))) {
                        j.remove();
                    }
                }
            }
            return listLocations;
        }
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







