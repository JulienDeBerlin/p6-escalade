package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.model.bean.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class LocationDaoImpl extends AbstractDaoImpl implements LocationDao {


    @Autowired
    SpotDaoImpl spotDao;


    private static final Logger logger = LogManager.getLogger();


    /**
     * Finds Location object based on Id (Primary key of table "Location")
     *
     * @param locationId the id (= colomn "id" in SQL table)
     * @return
     */
    @Override
    public Location findLocationById(int locationId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        Location myResult = jdbcTemplate.queryForObject(sqlQueryBuilder("id"), new Object[]{locationId}, new BeanPropertyRowMapper<>(Location.class));
        myResult.setSpots(spotDao.findSpotsByLocationId(myResult.getId()));
        return myResult;
    }


    /**
     * Finds a list of Location objects, based on one of the information contained in SQL table "Location"
     *
     * @param locationInput is the input entered by the user formated as follows: "location (category)" i.e.
     *                      "Auvergne-Rhône-Alpes (région)" ou encore "Courchevel (ville du département: Savoie)"
     * @return
     */
    @Override
    public List<Location> findLocationsByTableColomn(String locationInput) {

        String colomnInTableLocation = getColomnInTableLocation(locationInput);
        String cleanedLocation = cleanedLocation(locationInput);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        List<Location> myResults;
        if (colomnInTableLocation == "city_name") {
            String departement = cleanedDepartement(locationInput);
            String myRequest = "select * from location where location.city_name = ? and location.departement_name=?";
            myResults = jdbcTemplate.query(myRequest, new Object[]{cleanedLocation, departement},
                    new BeanPropertyRowMapper<>(Location.class));
        } else {
            myResults = jdbcTemplate.query(sqlQueryBuilder(colomnInTableLocation), new Object[]{cleanedLocation},
                    new BeanPropertyRowMapper<>(Location.class));
        }

        for (Iterator<Location> i = myResults.iterator(); i.hasNext(); ) {
            Location location = i.next();
            location.setSpots(spotDao.findSpotsByLocationId(location.getId()));
        }

        return myResults;

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
     * From a string formatted as "location (category)", extracts "location"
     */
    private String cleanedLocation(String locationInput) {
        return locationInput.substring(0, locationInput.lastIndexOf("(") - 1);
    }

    /**
     * From a string formatted as "location (ville du département: ain)", extracts "ain"
     */
    private String cleanedDepartement(String locationInput) {
        return locationInput.substring(locationInput.lastIndexOf(":") + 2, locationInput.lastIndexOf(")"));
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
     * table for items that match with the @param. For each of the match, adds its category and return the results in form of a
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
        String sqlQuery4 = "SELECT DISTINCT city_name, departement_name FROM location WHERE city_name ILIKE  '%" + query + "%'";
        String sqlQuery5 = "SELECT DISTINCT city_name, departement_name FROM location WHERE zip_code ILIKE  '" + query + "%'";

        List<String> myResults1 = jdbcTemplate.queryForList(sqlQuery1, String.class);
        List<String> myResults2 = jdbcTemplate.queryForList(sqlQuery2, String.class);
        List<String> myResults3 = jdbcTemplate.queryForList(sqlQuery3, String.class);
        List<String> myResults4 = jdbcTemplate.query(sqlQuery4, new CityLocationMapper());
        List<String> myResults5 = jdbcTemplate.query(sqlQuery5, new CityLocationMapper());

        for (String s : myResults1) {
            matches.add(s + " (région)");
        }

        for (String s : myResults2) {
            matches.add(s + " (département)");
        }

        for (String s : myResults3) {
            matches.add(s + " (département)");
        }

        for (String s : myResults4) {
            matches.add(s);
        }

        for (String s : myResults5) {
            matches.add(s);
        }

        return matches;

    }

    /**
     * This method is used for the autocompletion field in page update/delete spots or routes. It returns city proposals for which at least 1 spot is
     * available in the DB.
     *
     * @param query = the input entered by the user
     * @return
     */
    @Override
    public List<String> getCityProposalsForUpdateSpots(String query) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        List<String> matches = new ArrayList<>();

        String sqlQuery1 = "SELECT DISTINCT city_name, departement_name FROM location WHERE city_name ILIKE  '%" + query + "%'";
        String sqlQuery2 = "SELECT DISTINCT city_name, departement_name FROM location WHERE zip_code ILIKE  '" + query + "%'";

        List<String> myResults1 = jdbcTemplate.query(sqlQuery1, new CityLocationMapper());
        List<String> myResults2 = jdbcTemplate.query(sqlQuery2, new CityLocationMapper());

        for (String s : myResults1) {
            matches.add(s);
        }

        for (String s : myResults2) {
            matches.add(s);
        }

        return matches;
    }

    /**
     * This methods insert an object location in the DB
     *
     * @param l = the location object to be created
     * @return
     */
    @Override
    public Location insertLocation(Location l) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        KeyHolder holder = new GeneratedKeyHolder();

        String sqlQuery = "insert into location(region, departement_name, departement_id, city_name, zip_code) values(:region, :departement_name, :departement_id, :city_name, :zip_code)";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("region", l.getRegion());
        sqlParameterSource.addValue("departement_name", l.getDepartementName());
        sqlParameterSource.addValue("departement_id", l.getDepartementId());
        sqlParameterSource.addValue("city_name", l.getCityName());
        sqlParameterSource.addValue("zip_code", l.getZipCode());

        jdbcTemplate.update(sqlQuery, sqlParameterSource, holder, new String[]{"id"});

        int id = holder.getKey().intValue();
        l.setId(id);

        return l;
    }


    @Override
    public Location findLocationByNameAndDepartement(String cityName, String departementName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        String myRequest = "select * from location where city_name=? and departement_name=?";
        Location myResult = jdbcTemplate.queryForObject(myRequest, new Object[]{cityName, departementName}, new BeanPropertyRowMapper<>(Location.class));
        myResult.setSpots(spotDao.findSpotsByLocationId(myResult.getId()));
        return myResult;
    }


    @Override
    public Location findLocationBasedOnSpotId(int SpotId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        String myRequest = "select * from location where id in (select location_id from spot where spot.id = ?)";
        Location selectedLocation = jdbcTemplate.queryForObject(myRequest, new Object[]{SpotId}, new BeanPropertyRowMapper<>(Location.class));
        return selectedLocation;
    }


    /**
     * Mapper for Location table
     */
    class CityLocationMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            String s = rs.getString("city_name") + " (ville du département: " +
                    rs.getString("departement_name") + ")";
            return s;
        }
    }


    @Override
    public void deleteLocation(int locationId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "delete from location where id = ?";
        jdbcTemplate.update(sqlRequest, locationId);
    }
}
