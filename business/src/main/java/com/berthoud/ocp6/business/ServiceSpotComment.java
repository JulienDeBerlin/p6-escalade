package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.SpotCommentDao;
import com.berthoud.ocp6.model.bean.SpotComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Component
public class ServiceSpotComment {

    @Autowired
    SpotCommentDao spotCommentDao;

    @Transactional
    public SpotComment insertSpotComment(SpotComment spotComment) {
        return spotCommentDao.insertSpotComment(spotComment);
    }

    @Transactional
    public void deleteComment(int commentId) {
        spotCommentDao.deleteComment(commentId);
    }

    /**
     * This method sorts the spotComments contained in a list by date, from the latest till the oldest
     *
     * @param spotComments the list to be sorted
     * @return the sorted list
     */
    public List<SpotComment> sortSpotComments(List<SpotComment> spotComments) {

        if (spotComments != null) {
            Collections.sort(spotComments);
        }
        return spotComments;
    }


}
