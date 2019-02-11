package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.RouteDao;
import com.berthoud.ocp6.model.bean.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RouteDaoImpl extends AbstractDaoImpl implements RouteDao {


    /**
     * Finds all the routes based on a spot
     * @param spotId primary key of table "Spot"
     * @return
     */
    @Override
    public List<Route> findRoutesBasedOnSpot(int spotId) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        String mySqlRequest =   "select * from route where route.spot_id = ?";

        return jdbcTemplate.query (mySqlRequest, new Object[]{spotId}, new BeanPropertyRowMapper<> (Route.class));
    }
}
