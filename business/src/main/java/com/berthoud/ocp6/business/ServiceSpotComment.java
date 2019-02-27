package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.SpotCommentDao;
import com.berthoud.ocp6.model.bean.SpotComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ServiceSpotComment {

    @Autowired
    SpotCommentDao spotCommentDao;

    @Transactional
    public SpotComment insertSpotComment(SpotComment spotComment){
       return spotCommentDao.insertSpotComment(spotComment);
    }



}
