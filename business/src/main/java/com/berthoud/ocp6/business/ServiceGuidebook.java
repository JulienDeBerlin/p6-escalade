package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.GuidebookDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
     * This method returns a full guidebook object (incl. matching spots) based on its id
     * @param guidebookId
     * @return
     */
public  Guidebook findGuidebookbyId (int guidebookId){
     return guidebookDao.findGuidebookById(guidebookId);
}


}

