package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.MemberDao;
import com.berthoud.ocp6.model.bean.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MemberDaoImpl extends AbstractDaoImpl implements MemberDao {


    @Override
    public List<Member> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        return jdbcTemplate.query("select * from member", new BeanPropertyRowMapper<>(Member.class));
    }


    @Override
    public Member findMemberByEmail(String email) {

        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
            return jdbcTemplate.queryForObject("select * from member where email = ?", new Object[]{email},
                    new BeanPropertyRowMapper<>(Member.class));
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
