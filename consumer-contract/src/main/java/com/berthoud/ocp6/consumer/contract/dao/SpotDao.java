package com.berthoud.ocp6.consumer.contract.dao;

import com.berthoud.ocp6.model.bean.Spot;

import java.util.List;


public interface SpotDao {
     List<Spot> findSpotsByLocationId(String location);
     Spot findSpotBySpotId(int spotId);
     int insertSpot(Spot spot);



}
