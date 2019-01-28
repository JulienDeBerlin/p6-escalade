package com.berthoud.ocp6.dao;

import com.berthoud.ocp6.beans.Member;

import java.util.List;

public interface MemberDao {

    public List<Member> findAll ();
}
