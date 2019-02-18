package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.model.bean.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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


    @Override
    public List <String> getLocationProposals(String query) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        List<String> matches = new ArrayList<>();

        String sqlQuery1 =   "SELECT DISTINCT region FROM location WHERE region ILIKE  '%"+ query+  "%'";
        String sqlQuery2 =   "SELECT DISTINCT departement_name FROM location WHERE departement_name ILIKE  '%"+ query+  "%'";
        String sqlQuery3 =   "SELECT DISTINCT departement_name FROM location WHERE departement_id ILIKE '"+ query+  "%'";
        String sqlQuery4 =   "SELECT DISTINCT city_name FROM location WHERE city_name ILIKE  '%"+ query+  "%'";
        String sqlQuery5 =   "SELECT DISTINCT city_name FROM location WHERE zip_code ILIKE  '"+ query+  "%'";

        List<String> myResults1 =jdbcTemplate.queryForList(sqlQuery1,String.class);
        List<String> myResults2 =jdbcTemplate.queryForList(sqlQuery2,String.class);
        List<String> myResults3 =jdbcTemplate.queryForList(sqlQuery3,String.class);
        List<String> myResults4 =jdbcTemplate.queryForList(sqlQuery4,String.class);
        List<String> myResults5 =jdbcTemplate.queryForList(sqlQuery5,String.class);

        matches.addAll(myResults1);
        matches.addAll(myResults2);
        matches.addAll(myResults3);
        matches.addAll(myResults4);
        matches.addAll(myResults5);


        return matches;
    }
}
