package com.berthoud.ocp6.consumer.contract.dao;

import com.berthoud.ocp6.model.bean.Member;
import java.util.List;

public interface MemberDao {
     List<Member> findAll ();
     Member findMemberByEmail(String email);
     Member insertNewMember(Member member);
     Member findMemberbyNickname( String nickname);
     Member findMemberById (int id);

    void deleteMemberAccount(int memberId);
}
