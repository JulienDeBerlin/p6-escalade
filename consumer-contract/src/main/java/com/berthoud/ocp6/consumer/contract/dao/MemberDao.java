package com.berthoud.ocp6.consumer.contract.dao;

import com.berthoud.ocp6.model.bean.Member;
import java.util.List;

public interface MemberDao {
    public List<Member> findAll ();
}
