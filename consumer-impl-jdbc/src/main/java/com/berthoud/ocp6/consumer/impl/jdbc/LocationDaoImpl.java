package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.model.bean.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class LocationDaoImpl extends AbstractDaoImpl implements LocationDao {

    private Location myResult;
    private List<Location> myResults;


    @Autowired
    SpotDaoImpl spotDao;

    /**
     * Finds Location object based on Id (Primary key of table "Location")
     * @param locationInput the id (= colomn "id" in SQL table)
     * @return
     */
    @Override
    public Location findLocationById(String locationInput) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        myResult = jdbcTemplate.queryForObject (sqlQueryBuilder("id"), new Object[]{locationInput}, new BeanPropertyRowMapper<>(Location.class));
        myResult.setSpots(spotDao.findSpotsByLocationId(myResult.getId()));
        return myResult;
    }


    /**
     * Finds a list of Location objects, based on one of the information contained in SQL table "Location"
     * @param locationInput is the input entered by the user
     * @param tableColomn is the colomn of the table the query is based on (i.e. : departement_name, region...)
     * @return
     */
    @Override
    public List<Location> findLocationsByTableColomn(String locationInput, String tableColomn) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        myResults = jdbcTemplate.query(sqlQueryBuilder(tableColomn), new Object[]{locationInput}, new BeanPropertyRowMapper<>(Location.class));
        for (Iterator<Location> i = myResults.iterator(); i.hasNext(); ) {
            Location location = i.next();
            location.setSpots(spotDao.findSpotsByLocationId(location.getId()));
        }

        return myResults;
    }

    /**
     * Builds a find-all sqlQuery based on the tableColomn param
     * @param tableColomn is the colomn of the table the query is based on (i.e. : departement_name, region...)
     * @return
     */
    private String sqlQueryBuilder(String tableColomn){
        String myRequest;
        myRequest =  "select * from location where location."+ tableColomn +  "= ?";
        return myRequest;
    }


}
