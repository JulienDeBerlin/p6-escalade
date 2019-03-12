package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.GuidebookDao;
import com.berthoud.ocp6.model.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;


@Component
public class ServiceGuidebook {

    @Autowired
    GuidebookDao guidebookDao;

    /**
     * This method takes a list of guidebooks and - if loan is required -  removes the ones that do not fulfil this condition.
     *
     * @param guidebooks
     * @param loanRequired user want to display only guidebooks that are proposed for loan
     * @return
     */
    public List<Guidebook> filterGuidebooksByLoanAvailable(List<Guidebook> guidebooks, boolean loanRequired) {

        if (loanRequired) {
            for (Iterator<Guidebook> i = guidebooks.iterator(); i.hasNext(); ) {
                Guidebook guidebook = i.next();
                if (guidebook.getMemberLibrairies().isEmpty()) {
                    i.remove();
                }
            }
        }
        return guidebooks;
    }


    /**
     *
     *
     * @param guidebookId
     * @return
     */
    public Guidebook findGuidebookbyId(int guidebookId) {
        return guidebookDao.findGuidebookById(guidebookId);
    }


    /**
     * This method displays the guidebook owned by a member and available for loan
     *
     * @param member
     * @return
     */
    public List<Guidebook> getGuidebooksForLoan(Member member) {
        return guidebookDao.getGuidebooksForLoan(member);

    }


    /**
     * Return a Guidebook object based on its unique isbn13 number
     *
     * @param isbn13
     * @return
     */
    public Guidebook findGuidebookbyIsbn(String isbn13) {
        return guidebookDao.findGuidebookByIsbn(isbn13);
    }


    @Transactional
    public Guidebook insertGuidebook(Guidebook guidebook) {
        return guidebookDao.insertGuidebook(guidebook);
    }



    @Transactional
    public void insertRelationGuidebookSpots(List<Integer> listSpotId, Guidebook guidebook){
        guidebookDao.insertRelationGuidebookSpots(listSpotId, guidebook);
    }

    @Transactional
    public void deleteRelationGuidebookSpot(int spotId, int guidebookId){
        guidebookDao.deleteRelationGuidebookSpot(spotId, guidebookId);
    }


    @Transactional
    public void updateGuidebook(Guidebook selectedGuidebook){
         guidebookDao.updateGuidebook(selectedGuidebook);
    }

    @Transactional
    public void deleteGuidebook(Guidebook selectedGuidebook){
        guidebookDao.deleteGuidebook(selectedGuidebook);
    }


}

