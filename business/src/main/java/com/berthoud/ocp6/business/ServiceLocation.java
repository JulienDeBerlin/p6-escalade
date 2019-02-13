package com.berthoud.ocp6.business;
import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ServiceLocation {

    @Autowired
    private LocationDao locationDao;

    // OR AUTOWIRED, OR STATIC METHODS,  WHAT IS BEST?
    private ServiceSpot serviceSpot = new ServiceSpot();
    private ServiceGuidebook serviceGuidebook = new ServiceGuidebook();

//    @Autowired
//    private ServiceSpot serviceSpot;

    /**
     * Finds the full location details (incl. matching spots and routes) based on location
     * @param locationInput the location input entered by the user
     * @param tableColomn the category of input, basically the colomn of table "Location"the research is based on (i.e. region, departement_name...)
     * @return
     */
    public List<Location> detailledInfoBasedOnLocation(String locationInput, String tableColomn){
       return locationDao.findLocationsByTableColomn(locationInput,tableColomn);
    }


    /**
     * This method takes a list of Locations as parameter and for each location and each spot remove the routes that don't
     * fulfil the research criteria (level too low or route not bolted) and remove the spots with no remaining routes after filtering.
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
}







