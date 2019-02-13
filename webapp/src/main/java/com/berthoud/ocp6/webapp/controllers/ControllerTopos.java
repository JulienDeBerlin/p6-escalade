package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceGuidebook;
import com.berthoud.ocp6.business.ServiceLocation;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



@Controller
public class ControllerTopos {

    @Autowired
    ServiceLocation serviceLocation;

    @Autowired
    ServiceGuidebook serviceGuidebook;


    @RequestMapping(value = "/topos", method = RequestMethod.POST)
    public String getResultTopos(@RequestParam (value= "locationInputForTopo") String locationInputForTopo,
                                 @RequestParam (value = "loanRequired", required =false) boolean loanRequired,
                                ModelMap model){

        List<Location> resultLocations =serviceLocation.detailledInfoBasedOnLocation(locationInputForTopo, "departement_name");
        List<Guidebook> guidebookListWithoutDuplicates = serviceLocation.editGuidebookListWithoutDuplicate(resultLocations);
        serviceGuidebook.filterGuidebooksByLoanAvailable(guidebookListWithoutDuplicates, loanRequired);

        model.put("locationInputForTopo", locationInputForTopo);
        model.put("guidebookListWithoutDuplicates", guidebookListWithoutDuplicates);

        return "topos";
    }


}
