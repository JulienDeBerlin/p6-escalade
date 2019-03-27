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
    public Member insertNewMember(Member member) {
        return memberDao.insertNewMember(member);
    }

    @Transactional
    public void deleteMemberAccount(int memberId) {
        memberDao.deleteMemberAccount(memberId);
    }

    /**
     * This method checks in the DB if an email if available for member registration, meaning not already used by another member
     *
     * @param email the email to be checked
     * @return true if the email is not used yet, false otherwise
     */
    public boolean isEmailAvailable(String email) {
        return memberDao.findMemberByEmail(email) == null;
    }

    /**
     * This method checks in the DB if a nickname if available for member registration, meaning not already used by another member
     *
     * @param nickname the nickname to be checked
     * @return true if the nickname is not used yet, false otherwise
     */
    public boolean isNicknameAvailable(String nickname) {
        return memberDao.findMemberbyNickname(nickname) == null;
    }


}
