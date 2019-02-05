package com.berthoud.ocp6.business;
import com.berthoud.ocp6.model.bean.Spot;

import java.util.List;



public interface SpotManager {

    List<Spot> lookForSpotsBasedOnLocation(String location);
}
