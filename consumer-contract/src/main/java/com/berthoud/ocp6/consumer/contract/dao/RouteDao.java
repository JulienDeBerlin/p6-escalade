package com.berthoud.ocp6.consumer.contract.dao;

import com.berthoud.ocp6.model.bean.Route;

import java.util.List;

public interface RouteDao {
    List<Route> findRoutesBasedOnSpot(int spotId);
    Route insertRoute(Route route);

    void updateRoute(Route route);

    void deleteRoute(int routeId);
}
