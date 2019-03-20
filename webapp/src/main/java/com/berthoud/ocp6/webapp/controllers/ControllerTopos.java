package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceGuidebook;
import com.berthoud.ocp6.business.ServiceLocation;
import com.berthoud.ocp6.business.ServiceSpot;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Spot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

import static java.lang.Integer.parseInt;

@SessionAttributes(value = {"alertTopo"})
@Controller
public class ControllerTopos {

    @Autowired
    ServiceLocation serviceLocation;

    @Autowired
    ServiceGuidebook serviceGuidebook;

    @Autowired
    ServiceSpot serviceSpot;


    private static final Logger logger = LogManager.getLogger();

    /**
     * This controller-method takes as inputs the serach parameters entered by the user, look in the DB for the
     * matching guidebooks and pass the list of matching guidebooks to the view
     *
     * @param locationInputForTopo the location search-parameter entered by the user
     * @param loanRequired         search-parameter entered by the user. If loanRequired is true, only guidebooks available for loan
     *                             are retrieved.
     * @param model                //
     * @return topos.jsp, the page where the the guidebooks are presented
     */
    @RequestMapping(value = "/topos", method = RequestMethod.POST)
    public String getResultTopos(@RequestParam(value = "locationInputForTopo") String locationInputForTopo,
                                 @RequestParam(value = "loanRequired", required = false) boolean loanRequired,
                                 ModelMap model) {

        List<Location> resultLocations = serviceLocation.detailledInfoBasedOnLocation(locationInputForTopo);
        List<Guidebook> guidebookListWithoutDuplicates = serviceLocation.editGuidebookListWithoutDuplicate(resultLocations);
        serviceGuidebook.filterGuidebooksByLoanAvailable(guidebookListWithoutDuplicates, loanRequired);

        model.put("locationInputForTopo", locationInputForTopo);
        model.put("guidebookListWithoutDuplicates", guidebookListWithoutDuplicates);
        model.put("alertTopo", "ok");
        return "topos";
    }


    /**
     * This controller-method retrieved a list of guidebooks matching with a spot
     *
     * @param spotId the id of the spot
     * @param model  //
     * @return
     */
    @RequestMapping(value = "/topos", method = RequestMethod.GET)
    public String getResultTopos(@RequestParam(value = "spotId") String spotId, ModelMap model) {

        Spot selectedSpot = serviceSpot.findSpotBasedOnId(parseInt(spotId));

        String locationInputForTopo = "Site " + selectedSpot.getNameSpot() + ", secteur " + selectedSpot.getNameArea();
        List<Guidebook> guidebooks = selectedSpot.getGuidebooks();

        model.put("locationInputForTopo", locationInputForTopo);
        model.put("guidebookListWithoutDuplicates", guidebooks);

        return "topos";
    }


}
