package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.SpotDao;
import com.berthoud.ocp6.model.bean.Route;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class ServiceSpot {

    @Autowired
    ServiceRoute serviceRoute;

    @Autowired
    SpotDao spotDao;


    /**
     * This method takes a list of spots as parameter and for each spot removes the routes that don't
     * fulfil the research criteria (level too low or route not bolted or spot without any remaining routes after filtering)
     *
     * @param spots            the list of spots to be filtered
     * @param onlyBoltedRoutes true if filter is activated
     * @param levelMin         only routes with level = or > to levelMin remain
     * @return
     */
    public List<Spot> filterSpots(List<Spot> spots, boolean onlyBoltedRoutes, int levelMin, int levelMax) {

        for (Iterator<Spot> i = spots.iterator(); i.hasNext(); ) {
            Spot spot = i.next();
            List<Route> routes = spot.getRoutes();

            for (Iterator<Route> j = routes.iterator(); j.hasNext(); ) {
                Route route = j.next();

                if ((!route.isBolted() && onlyBoltedRoutes) || serviceRoute.getRatingAsInt(route) < levelMin ||
                        serviceRoute.getRatingAsInt(route) > levelMax) {
                    j.remove();
                }
            }
            if (routes.isEmpty()) {
                i.remove();
            }
        }
        return spots;
    }


    /**
     * The method returns a full Spot object based on its id
     *
     * @param spotId
     * @return
     */
    public Spot findSpotBasedOnId(int spotId) {
        return spotDao.findSpotBySpotId(spotId);
    }


    public void addSpot(){

    }

}