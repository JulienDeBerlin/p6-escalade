package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.GuidebookDao;
import com.berthoud.ocp6.consumer.contract.dao.SpotCommentDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.Spot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;


@Component
public class GuidebookDaoImpl extends AbstractDaoImpl implements GuidebookDao {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    GuidebookCommentDaoImpl commentGuidebookDao;

    @Autowired
    MemberLibrairyDaoImpl memberLibrairyDao;

    @Autowired
    RouteDaoImpl routeDao;

    @Autowired
    SpotCommentDao spotCommentDao;


    /**
     * This method finds all the guidebooks matching with a single spot identified with its primary key.
     * @param spotId
     * @return
     */
    @Override
    public List<Guidebook> findGuidebooksBasedOnSpot(int spotId) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "select * from guidebook where guidebook.id in " +
                "(select guidebook_id from association_spot_guidebook " +
                "where association_spot_guidebook.spot_id = ?)";
        List<Guidebook> resultGuidebooks = jdbcTemplate.query(sqlRequest, new Object[]{spotId}, new BeanPropertyRowMapper<>(Guidebook.class));

        for (Iterator<Guidebook> i = resultGuidebooks.iterator(); i.hasNext(); ) {
            Guidebook guidebook = i.next();
            guidebook.setCommentsGuidebook(commentGuidebookDao.findCommentGuidebooksByGuidebookId(guidebook.getId()));
            guidebook.setMemberLibrairies(memberLibrairyDao.findMemberLibrairyByGuidebookId(guidebook.getId()));
        }

        return resultGuidebooks;
    }

    /**
     * This method returns a guidebook object based on its id
     *
     * @param guidebookId
     * @return
     */
    @Override
    public Guidebook findGuidebookById(int guidebookId) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "select * from guidebook where guidebook.id = ?";
        Guidebook selectedGuidebook = jdbcTemplate.queryForObject(sqlRequest, new Object[]{guidebookId}, new BeanPropertyRowMapper<>(Guidebook.class));


        return selectedGuidebook;
    }


    @Override
    public List<Guidebook> getGuidebooksForLoan(Member member) {


        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "select * from guidebook where guidebook.id in " +
                "(select guidebook_id from member_librairy " +
                "where member_id = ?)";

        List <Guidebook> guidebooksForLoan = jdbcTemplate.query(sqlRequest, new Object[]{member.getId()},
                new BeanPropertyRowMapper<>(Guidebook.class));
        return guidebooksForLoan;
    }


    @Override
    public Guidebook findGuidebookByIsbn(String isbn) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        try {

            String mySqlRequest = "select id from guidebook where isbn13 = ?";
            Guidebook selectedGuidebook = jdbcTemplate.queryForObject(mySqlRequest, new Object[]{isbn}, new BeanPropertyRowMapper<>(Guidebook.class));
            selectedGuidebook = findGuidebookById(selectedGuidebook.getId());
            return selectedGuidebook;

        } catch (EmptyResultDataAccessException e) {
            logger.info("The query has no result.", e);
            return null;
        }
    }


    @Override
    public Guidebook insertGuidebook(Guidebook g) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        KeyHolder holder = new GeneratedKeyHolder();

        String sqlQuery = "insert into guidebook(isbn13, name, year_publication, publisher, language, summary, firstname_author, surname_author)" +
                " values (:isbn13, :name, :year_publication, :publisher, :language, :summary, :firstname_author, :surname_author)";

        SqlParameterSource sqlParameterSource= new MapSqlParameterSource();
        ((MapSqlParameterSource) sqlParameterSource).addValue("isbn13", g.getIsbn13());
        ((MapSqlParameterSource) sqlParameterSource).addValue("name", g.getName());
        ((MapSqlParameterSource) sqlParameterSource).addValue("year_publication", g.getYearPublication());
        ((MapSqlParameterSource) sqlParameterSource).addValue("publisher", g.getPublisher());
        ((MapSqlParameterSource) sqlParameterSource).addValue("language", g.getLanguage());
        ((MapSqlParameterSource) sqlParameterSource).addValue("summary", g.getSummary());
        ((MapSqlParameterSource) sqlParameterSource).addValue("firstname_author", g.getFirstnameAuthor());
        ((MapSqlParameterSource) sqlParameterSource).addValue("surname_author", g.getSurnameAuthor());

        jdbcTemplate.update(sqlQuery,sqlParameterSource, holder, new String[]{"id"});

        int id = holder.getKey().intValue();
        g.setId(id);

        return g;

    }


    @Override
    public void insertRelationGuidebookSpots(List<Integer> listSpotId, Guidebook guidebook) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        String sqlQuery = "insert into association_spot_guidebook(spot_id, guidebook_id) values (?,?)";

        for ( Integer i : listSpotId) {
            jdbcTemplate.update(sqlQuery, i, guidebook.getId());
        }

    }

    @Override
    public void updateGuidebook(Guidebook g) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String SQL = "update guidebook set name = ?, year_publication=?, publisher=?, language=?, summary=?," +
                "firstname_author=?, surname_author=? where id = ?";
        jdbcTemplate.update(SQL, g.getName(),g.getYearPublication(), g.getPublisher(), g.getLanguage(), g.getSummary(),
                                    g.getFirstnameAuthor(), g.getSurnameAuthor(), g.getId());

    }

    @Override
    public void deleteGuidebook(Guidebook g) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "delete from guidebook where id = ?";
        jdbcTemplate.update(sqlRequest, g.getId());
    }


    @Override
    public void deleteRelationGuidebookSpot(int spotId, int guidebookId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "delete from association_spot_guidebook where spot_id = ? and guidebook_id = ? ";
        jdbcTemplate.update(sqlRequest, spotId,guidebookId) ;

    }

}

