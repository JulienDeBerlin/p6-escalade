package com.berthoud.ocp6.consumer.contract.dao;

import com.berthoud.ocp6.model.bean.Route;

import java.util.List;

public interface RouteDao {
    public List<Route> findRoutesBasedOnSpot(int spotId);
    int insertRoute(Route route);
}
