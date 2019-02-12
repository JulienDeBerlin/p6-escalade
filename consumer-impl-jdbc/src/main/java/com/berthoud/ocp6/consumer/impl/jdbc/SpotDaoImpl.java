package com.berthoud.ocp6.consumer.impl.jdbc;
import com.berthoud.ocp6.consumer.contract.dao.SpotDao;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;


@Component
public class SpotDaoImpl extends AbstractDaoImpl implements SpotDao {


    @Autowired
    RouteDaoImpl routeDao;
    @Autowired
    GuidebookDaoImpl guidebookDao;


    /**
     * Finds a list of spots based on a location
     * @param locationId foreign key for table "Location" in table "Spot"
     * @return
     */
    @Override
    public List<Spot> findSpotsByLocationId(String locationId) {

        List<Spot> myResults;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String mySqlRequest =   "select * from spot where spot.location_id = ?";
        myResults= jdbcTemplate.query (mySqlRequest, new Object[]{locationId}, new BeanPropertyRowMapper(Spot.class));

        for (Iterator<Spot> i = myResults.iterator(); i.hasNext(); ) {
            Spot spot = i.next();
            spot.setRoutes(routeDao.findRoutesBasedOnSpot(spot.getId()));
            spot.setGuidebooks(guidebookDao.findGuidebooksBasedOnSpot(spot.getId()));
        }

        return myResults;


    }

}
