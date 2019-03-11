package com.berthoud.ocp6.consumer.contract.dao;
import com.berthoud.ocp6.model.bean.Location;

import java.util.List;
import java.util.Map;

public interface LocationDao {

    Location findLocationById(int locationInput);

    Location findLocationByNameAndDepartement(String cityName, String departementName);

    List<Location> findLocationsByTableColomn(String locationInput) throws Exception;


    List <String> getLocationProposals(String query);

    List<String> getCityProposalsForUpdateSpots(String query);

    Location insertLocation(Location location);

    Location findLocationBasedOnSpotId (int SpotId);


}
