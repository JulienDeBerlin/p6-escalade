package com.berthoud.ocp6.consumer.contract.dao;

import com.berthoud.ocp6.model.bean.CommentGuidebook;

import java.util.List;

public interface CommentGuidebookDao {


    public List<CommentGuidebook> findCommentGuidebooksByGuidebookId(int GuidebookId);

}
