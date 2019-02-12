package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.CommentGuidebookDao;
import com.berthoud.ocp6.model.bean.CommentGuidebook;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentGuidebookDaoImpl extends AbstractDaoImpl implements CommentGuidebookDao {

    @Override
    public List<CommentGuidebook> findCommentGuidebooksByGuidebookId(int GuidebookId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "select * from comment_guidebook where comment_guidebook.guidebook_id = ?";
        List<CommentGuidebook> guidebookComments = jdbcTemplate.query
                (sqlRequest, new Object[]{GuidebookId}, new BeanPropertyRowMapper<>(CommentGuidebook.class));
        return guidebookComments;
    }
}
