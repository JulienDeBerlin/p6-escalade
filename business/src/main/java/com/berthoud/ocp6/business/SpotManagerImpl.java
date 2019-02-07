package com.berthoud.ocp6.business;
import com.berthoud.ocp6.consumer.contract.dao.RouteDao;
import com.berthoud.ocp6.consumer.contract.dao.SpotDao;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;


@Component
public class SpotManagerImpl implements SpotManager {

    @Autowired
    SpotDao spotDao;

    @Autowired
    RouteDao routeDao;


    /**
     * Looks only for spots, without infos about the routes
     * @param location
     * @return
     */
    @Override
    public List<Spot> lookForSpotsBasedOnLocation(String location) {
      return spotDao.findSpotByLocation(location);
    }


    /**
     * Looks for spots, including the matching routes
     * @param location
     * @return
     */
    @Override
    public List<Spot> lookForDetailledSpotsBasedOnLocation(String location) {
        List<Spot> myResultatSetSpots = lookForSpotsBasedOnLocation(location);

        for (Iterator<Spot> i = myResultatSetSpots.iterator(); i.hasNext();){
            Spot obj = i.next();
            obj.setRoutes(routeDao.findRoutesBasedOnSpot(obj.getId()));
        }

            return myResultatSetSpots;
    }
}
