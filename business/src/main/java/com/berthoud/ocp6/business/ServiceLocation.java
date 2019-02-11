package com.berthoud.ocp6.business;
import com.berthoud.ocp6.consumer.impl.jdbc.LocationDaoImpl;
import com.berthoud.ocp6.model.bean.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLocation {

    @Autowired
    private LocationDaoImpl locationDao;

    /**
     * Finds the full location details (incl. matching spots and routes) based on location
     * @param locationInput the location input entered by the user
     * @param tableColomn the category of input, basically the colomn of table "Location"the research is based on.
     * @return
     */
    public List<Location> detailledInfoBasedOnLocation(String locationInput, String tableColomn){
       return locationDao.findLocationsByTableColomn(locationInput,tableColomn);

    }
}
