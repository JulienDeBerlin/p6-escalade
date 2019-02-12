package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.MemberLibrairyDao;
import com.berthoud.ocp6.model.bean.MemberLibrairy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberLibrairyDaoImpl extends AbstractDaoImpl implements MemberLibrairyDao

{
    @Override
    public List<MemberLibrairy> findMemberLibrairyByGuidebookId(int GuidebookId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "select * from member_librairy where member_librairy.guidebook_id = ?";
        List<MemberLibrairy> memberLibrairies = jdbcTemplate.query
                (sqlRequest, new Object[]{GuidebookId}, new BeanPropertyRowMapper<>(MemberLibrairy.class));
        return memberLibrairies;
    }
}
