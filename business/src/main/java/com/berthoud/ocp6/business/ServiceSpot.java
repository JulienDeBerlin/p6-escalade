package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.SpotDao;
import com.berthoud.ocp6.model.bean.Route;
import com.berthoud.ocp6.model.bean.Spot;
import com.berthoud.ocp6.model.bean.SpotComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class ServiceSpot {

    @Autowired
    ServiceRoute serviceRoute;

    @Autowired
    SpotDao spotDao;

    @Autowired
    ServiceSpotComment serviceSpotComment;


    /**
     * This method takes a list of spots as parameter and for each spot removes the routes that don't
     * fulfil the research criteria (level too low or route not bolted)
     *
     * @param spots            the list of spots to be filtered
     * @param onlyBoltedRoutes true if filter is activated
     * @param levelMin         only routes with level = or > to levelMin remain
     * @param levelMax         only routes with level = or < to levelMin remain
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
//            if (routes.isEmpty()) {
//                i.remove();
//            }
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


    @Transactional
    public Spot insertSpot(Spot spot) {
        return spotDao.insertSpot(spot);
    }


    @Transactional
    public void updateSpot(Spot spot) {
        spotDao.updateSpot(spot);
    }


    @Transactional
    public void deleteSpot(int spotId) {
        spotDao.deleteSpot(spotId);
    }


    public List<Spot> findSpotsBasedOnGuidebookId(int guidebookId) {
        return spotDao.findSpotsBasedOnGuidebookId(guidebookId);
    }


    /**
     * This method sorts the spots contained in a list by ascending order of the nameSpots.
     * It further calls the sorting methods that sort the object-attributes contained in a Location:
     * - comments are sorted by date, from the latest till the oldest
     * - routes are sorted by ascending order of their name
     *
     * @param spots the list to be sorted
     * @return the sorted list
     */
    public List<Spot> sortSpots(List<Spot> spots) {

        if (spots != null) {
            Collections.sort(spots);

            for (Iterator<Spot> j = spots.iterator(); j.hasNext(); ) {
                Spot spot = j.next();
                serviceSpotComment.sortSpotComments(spot.getComments());
                serviceRoute.sortRoutes(spot.getRoutes());
            }
        }
        return spots;
    }


}