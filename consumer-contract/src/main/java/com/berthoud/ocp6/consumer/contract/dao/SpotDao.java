package com.berthoud.ocp6.consumer.contract.dao;
import com.berthoud.ocp6.model.bean.Spot;
import java.util.List;


public interface SpotDao {
    public List<Spot> findSpotsByLocationId(String location);
    public Spot findSpotBySpotId(int spotId);


}
