package com.berthoud.ocp6.consumer.contract.dao;
import com.berthoud.ocp6.model.bean.Spot;
import java.util.List;


public interface SpotDao {
//    public List<Spot> findSpotsByLocation(String location);
    public List<Spot> findSpotsByLocationId(String location);

}
