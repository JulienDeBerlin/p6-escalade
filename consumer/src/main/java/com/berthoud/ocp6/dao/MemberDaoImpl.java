//package com.berthoud.ocp6.dao;
//
//import com.berthoud.ocp6.beans.Member;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class MemberDaoImpl implements MemberDao {
//
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    @Override
//    public List<Member> findAll() {
//        return jdbcTemplate.query("select * from member", new BeanPropertyRowMapper(Member.class));
//    }
//}
