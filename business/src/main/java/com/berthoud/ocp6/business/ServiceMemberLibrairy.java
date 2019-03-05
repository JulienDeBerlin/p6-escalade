package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.MemberLibrairyDao;
import com.berthoud.ocp6.model.bean.Booking;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.MemberLibrairy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ServiceMemberLibrairy {

    @Autowired
    MemberLibrairyDao memberLibrairy;

    @Transactional
    public MemberLibrairy insertGuidebook(Guidebook selectedGuidebook, Member user){
        return memberLibrairy.insertGuidebook(selectedGuidebook, user);
    }

    @Transactional
    public void removeGuidebook(Guidebook selectedGuidebook, Member user){
        memberLibrairy.removeGuidebook(selectedGuidebook, user);
    }

    public boolean isAlredayListed(Guidebook selectedGuidebook, List<Guidebook> listGuidebook) {

        List<Integer> listIdGuidebooks = new ArrayList<>();

        for (Iterator<Guidebook> i = listGuidebook.iterator(); i.hasNext(); ) {
            Guidebook guidebook = i.next();
            listIdGuidebooks.add(guidebook.getId());
        }

        if (listIdGuidebooks.contains(selectedGuidebook.getId())) {
            return true;
        }
        return false;
    }

    public MemberLibrairy getMemberLibrairy(Guidebook selectedGuidebook, Member user){
        MemberLibrairy privateLibrairy;
        privateLibrairy = memberLibrairy.findMemberLibrairy(selectedGuidebook, user);
        return  privateLibrairy;
    }


    @Transactional
    public Booking insertBooking (MemberLibrairy privateGuidebook, Booking booking){
        return memberLibrairy.insertBooking(privateGuidebook,booking);
    }



}
