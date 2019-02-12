package com.berthoud.ocp6.consumer.impl.jdbc;
import com.berthoud.ocp6.consumer.contract.dao.GuidebookDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;


@Component
public class GuidebookDaoImpl extends AbstractDaoImpl implements GuidebookDao {

    @Autowired
    CommentGuidebookDaoImpl commentGuidebookDao;

    @Autowired
    MemberLibrairyDaoImpl memberLibrairyDao;


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
        List<Guidebook> resultGuidebooks = jdbcTemplate.query (sqlRequest, new Object[]{spotId}, new BeanPropertyRowMapper<> (Guidebook.class));

        for (Iterator<Guidebook> i = resultGuidebooks.iterator(); i.hasNext(); ) {
            Guidebook guidebook = i.next();
            guidebook.setCommentsGuidebook(commentGuidebookDao.findCommentGuidebooksByGuidebookId(guidebook.getId()));
            guidebook.setMemberLibrairies(memberLibrairyDao.findMemberLibrairyByGuidebookId(guidebook.getId()));
        }

        return resultGuidebooks;
    }
}
