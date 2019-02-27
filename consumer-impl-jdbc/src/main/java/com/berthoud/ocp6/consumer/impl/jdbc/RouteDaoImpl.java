package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.RouteDao;
import com.berthoud.ocp6.model.bean.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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


    /**
     * this method inserts a new route in the DB
     */
    @Override
    public Route insertRoute(Route r) {

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        KeyHolder holder = new GeneratedKeyHolder();

        String sqlQuery = "insert into route(name, nb_pitch, index_pitch, rating, bolted, spot_id) " +
                "values (:name, :nb_pitch, :index_pitch, :rating, :bolted, :spot_id)";

        SqlParameterSource sqlParameterSource= new MapSqlParameterSource();
        ((MapSqlParameterSource) sqlParameterSource).addValue("name", r.getName());
        ((MapSqlParameterSource) sqlParameterSource).addValue("nb_pitch", r.getNbPitch());
        ((MapSqlParameterSource) sqlParameterSource).addValue("index_pitch", r.getIndexPitch());
        ((MapSqlParameterSource) sqlParameterSource).addValue("rating", r.getRating());
        ((MapSqlParameterSource) sqlParameterSource).addValue("bolted", r.isBolted());
        ((MapSqlParameterSource) sqlParameterSource).addValue("spot_id", r.getSpot().getId());

        jdbcTemplate.update(sqlQuery,sqlParameterSource, holder, new String[]{"id"});

        int id = holder.getKey().intValue();
        r.setId(id);

        return r;
    }
}
