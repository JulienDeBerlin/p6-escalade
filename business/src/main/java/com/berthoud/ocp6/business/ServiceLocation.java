package com.berthoud.ocp6.business;
import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Route;
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
     *  fullfit the research criteria (level too low or route not bolted)
     * @param locations
     * @param onlyBoltedRoutes
     * @param levelMin
     * @return
     */
    public List<Location> filterLocation (List<Location> locations, boolean onlyBoltedRoutes, int levelMin){

        for (Iterator<Location> i = locations.iterator(); i.hasNext(); ) {
            Location location = i.next();
            List<Spot> spots = location.getSpots();
            serviceSpot.filterSpots(spots, onlyBoltedRoutes, levelMin);
        }
     return locations;
    }

}







