package com.berthoud.ocp6.consumer.contract.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DaoFactory {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private SpotDao spotDao;

    public MemberDao getMemberDao() {
        return memberDao;
    }

    public SpotDao getSpotDao() {
        return spotDao;
    }

}
