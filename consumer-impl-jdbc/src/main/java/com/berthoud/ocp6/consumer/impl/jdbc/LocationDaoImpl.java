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

@Component
public class LocationDaoImpl extends AbstractDaoImpl implements LocationDao {

    private Location myResult;
    private List<Location> myResults;


    @Autowired
    SpotDaoImpl spotDao;

    /**
     * Finds Location object based on Id (Primary key of table "Location")
     *
     * @param locationInput the id (= colomn "id" in SQL table)
     * @return
     */
    @Override
    public Location findLocationById(String locationInput) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        myResult = jdbcTemplate.queryForObject(sqlQueryBuilder("id"), new Object[]{locationInput}, new BeanPropertyRowMapper<>(Location.class));
        myResult.setSpots(spotDao.findSpotsByLocationId(myResult.getId()));
        return myResult;
    }


    /**
     * Finds a list of Location objects, based on one of the information contained in SQL table "Location"
     *
     * @param locationInput is the input entered by the user formated as follows: "location (category)" i.e.
     *                      "Auvergne-Rhône-Alpes (région)"
     * @return
     */
    @Override
    public List<Location> findLocationsByTableColomn(String locationInput) throws Exception {

        try {
            String cleanedLocation = cleanedLocation(locationInput);
            String colomnInTableLocation = getColomnInTableLocation(locationInput);

            JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
            myResults = jdbcTemplate.query(sqlQueryBuilder(colomnInTableLocation), new Object[]{cleanedLocation}, new BeanPropertyRowMapper<>(Location.class));
            for (Iterator<Location> i = myResults.iterator(); i.hasNext(); ) {
                Location location = i.next();
                location.setSpots(spotDao.findSpotsByLocationId(location.getId()));
            }

            return myResults;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Builds a find-all sqlQuery based on the tableColomn param
     *
     * @param tableColomn is the colomn of the table the query is based on (i.e. : departement_name, region...)
     * @return
     */
    private String sqlQueryBuilder(String tableColomn) {
        String myRequest;
        myRequest = "select * from location where location." + tableColomn + "= ?";
        return myRequest;
    }


    /**
     * From a string formated as "location (category)", extracts "location"
     */
    private String cleanedLocation(String locationInput) {
        return locationInput.substring(0, locationInput.lastIndexOf("(") - 1);
    }


    /**
     * From a string formated as "location (category)", extracts the name of the SQL table location
     * that matches with "category"
     */
    private String getColomnInTableLocation(String locationInput) {
        String colomnInTableLocation = "";
        if (locationInput.charAt(locationInput.lastIndexOf("(") + 1) == 'r') {
            colomnInTableLocation = "region";
        }

        if (locationInput.charAt(locationInput.lastIndexOf("(") + 1) == 'd') {
            colomnInTableLocation = "departement_name";
        }

        if (locationInput.charAt(locationInput.lastIndexOf("(") + 1) == 'v') {
            colomnInTableLocation = "city_name";
        }
        return colomnInTableLocation;
    }

    /**
     * This method is used for the autocompletion in the site-search-field. It looks in different colomns of the "location"
     * table for items that match with the @param. For each of the match, add its category and return the results in form of a
     * String List.
     *
     * @param query = the input entered by the user
     * @return
     */
    @Override
    public List<String> getLocationProposals(String query) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        List<String> matches = new ArrayList<>();

        String sqlQuery1 = "SELECT DISTINCT region FROM location WHERE region ILIKE  '%" + query + "%'";
        String sqlQuery2 = "SELECT DISTINCT departement_name FROM location WHERE departement_name ILIKE  '%" + query + "%'";
        String sqlQuery3 = "SELECT DISTINCT departement_name FROM location WHERE departement_id ILIKE '" + query + "%'";
        String sqlQuery4 = "SELECT DISTINCT city_name FROM location WHERE city_name ILIKE  '%" + query + "%'";
        String sqlQuery5 = "SELECT DISTINCT city_name FROM location WHERE zip_code ILIKE  '" + query + "%'";

        List<String> myResults1 = jdbcTemplate.queryForList(sqlQuery1, String.class);
        List<String> myResults2 = jdbcTemplate.queryForList(sqlQuery2, String.class);
        List<String> myResults3 = jdbcTemplate.queryForList(sqlQuery3, String.class);
        List<String> myResults4 = jdbcTemplate.queryForList(sqlQuery4, String.class);
        List<String> myResults5 = jdbcTemplate.queryForList(sqlQuery5, String.class);

        for (String s : myResults1) {
            matches.add(s + " (région)");
        }

        for (String s : myResults2) {
            matches.add(s + " (département)");
        }

        for (String s : myResults3) {
            matches.add(s + " (départemention)");
        }

        for (String s : myResults4) {
            matches.add(s + " (ville)");
        }

        for (String s : myResults5) {
            matches.add(s + " (ville)");
        }

        return matches;

    }

    /**
     * This methods insert an object location in the DB
     * @param l = the location object to be created
     * @return
     */
    @Override
    public int insertLocation(Location l) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        String sqlQuery = "insert into location(region, departement_name, departement_id, city_name, zip_code";
        return jdbcTemplate.update(sqlQuery, (new Object [] {l.getRegion(), l.getDepartementName(),
                l.getDepartementId(), l.getCityName(), l.getZipCode()}));
    }

}
