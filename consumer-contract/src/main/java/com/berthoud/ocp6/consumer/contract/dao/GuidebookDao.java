package com.berthoud.ocp6.consumer.contract.dao;

import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.Spot;

import java.util.List;

public interface GuidebookDao {

     List<Guidebook> findGuidebooksBasedOnSpot(int spotId);

     Guidebook findGuidebookById(int guidebookId);

     List<Guidebook> getGuidebooksForLoan(Member member);

     Guidebook findGuidebookByIsbn(String isbn);

     Guidebook insertGuidebook (Guidebook guidebook);

     void insertRelationGuidebookSpots(List <Integer> listSpotId, Guidebook guidebook);

     void updateGuidebook(Guidebook selectedGuidebook);

     void deleteGuidebook (Guidebook selectedGuidebook);

     void deleteRelationGuidebookSpot (int spotId, int guidebookId);

}