package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.MemberDao;
import com.berthoud.ocp6.model.bean.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDaoImpl extends AbstractDaoImpl implements MemberDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", new BeanPropertyRowMapper(Member.class));
    }
}
