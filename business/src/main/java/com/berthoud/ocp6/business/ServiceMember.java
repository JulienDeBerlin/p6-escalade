package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.MemberDao;
import com.berthoud.ocp6.model.bean.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ServiceMember {

    @Autowired
    MemberDao memberDao;

    @Transactional
    public Member insertNewMember(Member member){
        return memberDao.insertNewMember(member);
    }


    public boolean isEmailValid(String email){
        if (memberDao.findMemberByEmail(email)==null){
            return true;
        }else return false;
    }

    public boolean isNicknameValid(String nickname){
        if (memberDao.findMemberbyNickname(nickname)==null){
            return true;
        }else return false;
    }

    @Transactional
    public void deleteMemberAccount(int memberId) {
        memberDao.deleteMemberAccount(memberId);
    }

}
