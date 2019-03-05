package com.berthoud.ocp6.consumer.impl.jdbc;

import com.berthoud.ocp6.consumer.contract.dao.MemberLibrairyDao;
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
}
