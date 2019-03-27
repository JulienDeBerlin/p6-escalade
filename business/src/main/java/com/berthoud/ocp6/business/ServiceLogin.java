package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.MemberDao;
import com.berthoud.ocp6.model.bean.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class ServiceLogin {

    @Autowired
    MemberDao memberDao;

    /**
     * Get a list of all Member objects
     *
     * @return
     */
    public List<Member> getAllMembers() {
        return memberDao.findAll();
    }

    /**
     * Find a Member object based on its email address
     *
     * @param inputEmail
     * @return
     */
    public Member findMemberByEmail(String inputEmail) {
        return memberDao.findMemberByEmail(inputEmail);
    }

    /**
     * This method checks if the password entered by the user matches with the email entered.
     *
     * @param inputPassword
     * @param member
     * @return
     */
    public boolean checkPassword(String inputPassword, Member member) {
        return member.getPassword().equals(inputPassword);
    }

    @Transactional
    public int updatePassword(Member member) {
        return memberDao.updatePassword(member);
    }
}
