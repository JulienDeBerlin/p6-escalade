package com.berthoud.ocp6.consumer.impl.jdbc;
import com.berthoud.ocp6.consumer.contract.dao.SpotDao;
import com.berthoud.ocp6.model.bean.Route;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SpotDaoImpl extends AbstractDaoImpl implements SpotDao {

    @Override
    public List<Spot> findSpotByLocation(String location) {
        List<Spot> resultSetSpot;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        String mySqlRequest =   "select * from spot " +
                                "where spot.location_id in " +
                                        "(select location.id from location "+
                                        "where location.city_name = ? )";

        resultSetSpot= jdbcTemplate.query (mySqlRequest, new Object[]{location}, new BeanPropertyRowMapper(Spot.class));

        return resultSetSpot;
    }
}
