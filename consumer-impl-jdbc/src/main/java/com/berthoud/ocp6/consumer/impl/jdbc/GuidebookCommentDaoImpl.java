package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.GuidebookCommentDao;
import com.berthoud.ocp6.model.bean.GuidebookComment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GuidebookCommentDaoImpl extends AbstractDaoImpl implements GuidebookCommentDao {

    @Override
    public List<GuidebookComment> findCommentGuidebooksByGuidebookId(int GuidebookId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "select * from comment_guidebook where comment_guidebook.guidebook_id = ?";
        List<GuidebookComment> guidebookComments = jdbcTemplate.query
                (sqlRequest, new Object[]{GuidebookId}, new BeanPropertyRowMapper<>(GuidebookComment.class));
        return guidebookComments;
    }


}
