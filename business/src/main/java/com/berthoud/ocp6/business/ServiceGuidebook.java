package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.GuidebookDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Member;
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
     * This method takes as input a list of guidebooks and - if loan is required -  removes the ones that are not available for loan.
     *
     * @param guidebooks   the list of guidebooks to be filtered
     * @param loanRequired is true if user want to display only guidebooks that are available for loan
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
     * This method find a guidebook, based on its Id
     *
     * @param guidebookId the id of the guidebook
     * @return the guidebook
     */
    public Guidebook findGuidebookbyId(int guidebookId) {
        return guidebookDao.findGuidebookById(guidebookId);
    }

    /**
     * This method find the private guidebooks owned by a member and available for loan
     *
     * @param member //
     * @return the list of guidebooks of the member
     */
    public List<Guidebook> getGuidebooksForLoan(Member member) {
        return guidebookDao.getGuidebooksForLoan(member);

    }

    /**
     * Return a Guidebook object based on its unique isbn13 number
     *
     * @param isbn13 //
     * @return the guidebook
     */
    public Guidebook findGuidebookbyIsbn(String isbn13) {
        return guidebookDao.findGuidebookByIsbn(isbn13);
    }

    /**
     * This method links a guidebooks with the spots covered by the guidebook.
     *
     * @param listSpotId a list of the id of the spots that should be linked to the spot
     * @param guidebook  the guidebook that should be linked to the spots
     */
    @Transactional
    public void insertRelationGuidebookSpots(List<Integer> listSpotId, Guidebook guidebook) {
        guidebookDao.insertRelationGuidebookSpots(listSpotId, guidebook);
    }

    /**
     * This method breaks an existing link between a guidebook and a spot.
     *
     * @param spotId      the id of the spot
     * @param guidebookId the id of the guidebook
     */
    @Transactional
    public void deleteRelationGuidebookSpot(int spotId, int guidebookId) {
        guidebookDao.deleteRelationGuidebookSpot(spotId, guidebookId);
    }

    @Transactional
    public Guidebook insertGuidebook(Guidebook guidebook) {
        return guidebookDao.insertGuidebook(guidebook);
    }

    @Transactional
    public void updateGuidebook(Guidebook selectedGuidebook) {
        guidebookDao.updateGuidebook(selectedGuidebook);
    }

    @Transactional
    public void deleteGuidebook(Guidebook selectedGuidebook) {
        guidebookDao.deleteGuidebook(selectedGuidebook);
    }
}

