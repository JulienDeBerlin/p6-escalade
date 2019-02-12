package com.berthoud.ocp6.business;
import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

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
     * @param tableColomn the category of input, basically the colomn of table "Location"the research is based on.
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

}







