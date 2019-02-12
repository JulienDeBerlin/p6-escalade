package com.berthoud.ocp6.consumer.contract.dao;

import com.berthoud.ocp6.model.bean.Guidebook;

import java.util.List;

public interface GuidebookDao {

    public List<Guidebook> findGuidebooksBasedOnSpot(int spotId);
}
