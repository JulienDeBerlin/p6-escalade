package com.berthoud.ocp6.consumer.contract.dao;
import com.berthoud.ocp6.model.bean.Location;

import java.util.List;
import java.util.Map;

public interface LocationDao {

    Location findLocationById(String locationInput);

    List<Location> findLocationsByTableColomn(String locationInput) throws Exception;

    List <String> getLocationProposals(String query);

    int insertLocation(Location location);



}
