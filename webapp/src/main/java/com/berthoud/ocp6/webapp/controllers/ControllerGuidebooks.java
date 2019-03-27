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

import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
public class ControllerGuidebooks {

    @Autowired
    ServiceLocation serviceLocation;

    @Autowired
    ServiceGuidebook serviceGuidebook;

    @Autowired
    ServiceSpot serviceSpot;


    private static final Logger logger = LogManager.getLogger();

    /**
     * This controller-method takes as inputs the search parameters entered by the user, looks in the DB for the
     * matching guidebooks and passes the list of matching guidebooks to the view
     *
     * @param locationInputForTopo the location search-parameter entered by the user
     * @param loanRequired         search-parameter entered by the user. If loanRequired is true, only guidebooks available for loan
     *                             are retrieved.
     * @param model                //
     * @return guidebooks.jsp, the page where the the guidebooks are presented
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
        return "guidebooks";
    }


    /**
     * This controller-method retrieves a list of guidebooks matching with a spot
     *
     * @param spotId the id of the spot
     * @param model  //
     * @return
     */
    @RequestMapping(value = "/topos", method = RequestMethod.GET)
    public String getResultTopos(@RequestParam(value = "spotId") String spotId, ModelMap model) {

        Spot selectedSpot = serviceSpot.findSpotBasedOnId(parseInt(spotId));

        String locationInputForTopo = selectedSpot.getNameSpot();
        if (!selectedSpot.getNameArea().equals("")) {
            locationInputForTopo += ", secteur: " + selectedSpot.getNameArea();
        }
        List<Guidebook> guidebooks = selectedSpot.getGuidebooks();

        model.put("locationInputForTopo", locationInputForTopo);
        model.put("guidebookListWithoutDuplicates", guidebooks);

        return "guidebooks";
    }


}
