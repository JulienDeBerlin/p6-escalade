package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.SpotCommentDao;
import com.berthoud.ocp6.model.bean.SpotComment;
import com.berthoud.ocp6.model.bean.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class SpotCommentDaoImpl extends AbstractDaoImpl implements SpotCommentDao {


    /**
     * This method returns a full SpotComment object (incl matching member) based on the spotId
     * @param spotId
     * @return
     */
    @Override
    public List<SpotComment> findCommentSpotBySpotId(int spotId) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String mySqlRequest = "select * from comment_spot where comment_spot.spot_id = ?";
        List<SpotComment> spotComments = jdbcTemplate.query(mySqlRequest, new Object[]{spotId}, new BeanPropertyRowMapper<>(SpotComment.class));

        for (Iterator<SpotComment> i = spotComments.iterator(); i.hasNext(); ) {
            SpotComment spotComment = i.next();

            mySqlRequest = "select * from member where member.id in (select comment_spot.member_id from comment_spot where comment_spot.id = ?)";
            Member member = jdbcTemplate.queryForObject(mySqlRequest, new Object[]{spotComment.getId()}, new BeanPropertyRowMapper<>(Member.class));

            spotComment.setMember(member);

        }
        return spotComments;
    }
}
