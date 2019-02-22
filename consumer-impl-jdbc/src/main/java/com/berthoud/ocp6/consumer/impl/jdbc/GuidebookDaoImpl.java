package com.berthoud.ocp6.consumer.impl.jdbc;
import com.berthoud.ocp6.consumer.contract.dao.SpotCommentDao;
import com.berthoud.ocp6.consumer.contract.dao.GuidebookDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;


@Component
public class GuidebookDaoImpl extends AbstractDaoImpl implements GuidebookDao {

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
     *
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
     * This method returns a full guidebook object (incl. matching spots) based on its id
     *
     * @param guidebookId
     * @return
     */
    @Override
    public Guidebook findGuidebookById(int guidebookId) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        // GET DATA FROM GUIDEBOOK TABLE
        String sqlRequest = "select * from guidebook where guidebook.id = ?";
        Guidebook selectedGuidebook = jdbcTemplate.queryForObject(sqlRequest, new Object[]{guidebookId}, new BeanPropertyRowMapper<>(Guidebook.class));

        //ADD DATA FOR MATCHING SPOTS
        sqlRequest = "select * from spot where spot.id in " +
                "(select spot_id from association_spot_guidebook " +
                "where association_spot_guidebook.guidebook_id = ?)";
        List<Spot> spots = jdbcTemplate.query(sqlRequest, new Object[]{guidebookId}, new BeanPropertyRowMapper<>(Spot.class));

        //ADD DATA FOR MATCHING LOCATION
        sqlRequest = "select * from location where location.id in " +
                "(select location_id from spot " +
                "where spot.id = ?)";

        for (Iterator<Spot> i = spots.iterator(); i.hasNext(); ) {
            Spot spot = i.next();
            Location location = jdbcTemplate.queryForObject(sqlRequest, new Object[]{spot.getId()},
                    new BeanPropertyRowMapper<>(Location.class));

            spot.setLocation(location);
            spot. setRoutes(routeDao.findRoutesBasedOnSpot(spot.getId()));

            //ADD DATA FOR MATCHING COMMENTS
            spot.setComments(spotCommentDao.findCommentSpotBySpotId(spot.getId()));
        }

            selectedGuidebook.setSpots(spots);

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





}
