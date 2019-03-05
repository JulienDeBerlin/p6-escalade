package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.MemberLibrairyDao;
import com.berthoud.ocp6.model.bean.Booking;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.MemberLibrairy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

import java.util.List;

@Component
public class MemberLibrairyDaoImpl extends AbstractDaoImpl implements MemberLibrairyDao

{
    @Override
    public List<MemberLibrairy> findMemberLibrairyByGuidebookId(int GuidebookId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "select * from member_librairy where member_librairy.guidebook_id = ?";
        List<MemberLibrairy> memberLibrairies = jdbcTemplate.query
                (sqlRequest, new Object[]{GuidebookId},  new BeanPropertyRowMapper<>(MemberLibrairy.class));
        return memberLibrairies;
    }

    @Override
    public MemberLibrairy insertGuidebook(Guidebook selectedGuidebook, Member user) {

        MemberLibrairy ml = new MemberLibrairy();
        ml.setGuidebook(selectedGuidebook);
        ml.setMember(user);

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        KeyHolder holder = new GeneratedKeyHolder();

        String sqlQuery = "insert into member_librairy(member_id, guidebook_id) values (:member_id, :guidebook_id)";

        SqlParameterSource sqlParameterSource= new MapSqlParameterSource();
        ((MapSqlParameterSource) sqlParameterSource).addValue("member_id", user.getId());
        ((MapSqlParameterSource) sqlParameterSource).addValue("guidebook_id", selectedGuidebook.getId());

        jdbcTemplate.update(sqlQuery,sqlParameterSource, holder, new String[]{"id"});

        int id = holder.getKey().intValue();
        ml.setId(id);


        return ml;
    }

    @Override
    public void removeGuidebook(Guidebook selectedGuidebook, Member user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        String sqlRequest = "delete from member_librairy where guidebook_id = ? and member_id = ?";
        jdbcTemplate.update(sqlRequest, selectedGuidebook.getId(), user.getId());
    }


    @Override
    public MemberLibrairy findMemberLibrairy(Guidebook selectedGuidebook, Member user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

        MemberLibrairy privateGuidebook;
        String sqlRequest = "select * from member_librairy where guidebook_id = ? and member_id = ?";
        privateGuidebook = jdbcTemplate.queryForObject(sqlRequest, new Object[]{selectedGuidebook.getId(), user.getId()},
                new BeanPropertyRowMapper<>(MemberLibrairy.class));

        // + Add bookings to privateGuidebook
        List<Booking> bookings;
        String sqlRequest2 = "select * from booking where member_librairy_id = ?";
        bookings = jdbcTemplate.query(sqlRequest2, new Object[]{privateGuidebook.getId()},
                new BeanPropertyRowMapper<>(Booking.class));

        privateGuidebook.setBookings(bookings);
        privateGuidebook.setMember(user);
        privateGuidebook.setGuidebook(selectedGuidebook);

        return privateGuidebook;
    }

    @Override
    public Booking insertBooking(MemberLibrairy privateGuidebook, Booking booking) {

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        KeyHolder holder = new GeneratedKeyHolder();

        String sqlQuery = "insert into booking(booked_by, date_from, date_until, email, phone, member_librairy_id) " +
                "values (:booked_by, :date_from, :date_until, :email, :phone, :member_librairy_id)";

        SqlParameterSource sqlParameterSource= new MapSqlParameterSource();
        ((MapSqlParameterSource) sqlParameterSource).addValue("booked_by", booking.getBookedBy());
        ((MapSqlParameterSource) sqlParameterSource).addValue("date_from", booking.getDateFrom());
        ((MapSqlParameterSource) sqlParameterSource).addValue("date_until", booking.getDateUntil());
        ((MapSqlParameterSource) sqlParameterSource).addValue("email", booking.getEmail());
        ((MapSqlParameterSource) sqlParameterSource).addValue("phone", booking.getPhone());
        ((MapSqlParameterSource) sqlParameterSource).addValue("member_librairy_id", privateGuidebook.getId());

        jdbcTemplate.update(sqlQuery,sqlParameterSource, holder, new String[]{"id"});

        int id = holder.getKey().intValue();
        booking.setId(id);

        return booking;

    }
}
