package com.berthoud.ocp6.business;
import com.berthoud.ocp6.consumer.contract.dao.SpotDao;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class SpotManagerImpl implements SpotManager {

    @Autowired
    SpotDao spotDao;

    @Override
    public List<Spot> lookForSpotsBasedOnLocation(String location) {
        return spotDao.findSpotByLocation(location);
    }
}
