package com.berthoud.ocp6.consumer.dao.contract;

import com.berthoud.ocp6.model.bean.Member;

import java.util.List;

public interface MemberDao {

    public List<Member> findAll ();
}
