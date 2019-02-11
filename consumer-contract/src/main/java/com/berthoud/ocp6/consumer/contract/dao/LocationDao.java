package com.berthoud.ocp6.consumer.contract.dao;
import com.berthoud.ocp6.model.bean.Location;

import java.util.List;

public interface LocationDao {

    public Location findLocationById(String locationInput);

    public List<Location> findLocationsByTableColomn(String locationInput, String tableColomn);



}
