package com.berthoud.ocp6.consumer.contract.dao;

import com.berthoud.ocp6.model.bean.GuidebookComment;

import java.util.List;

public interface GuidebookCommentDao {


    public List<GuidebookComment> findCommentGuidebooksByGuidebookId(int GuidebookId);

}
