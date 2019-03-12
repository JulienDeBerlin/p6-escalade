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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;

@SessionAttributes(value = {"alert","locationInput", "onlySpotsWithBoltedRoutes", "ratingMin", "ratingMax" })
@Controller
public class ControllerSpots {

    @Autowired
    ServiceLocation serviceLocation;

    @Autowired
    ServiceGuidebook serviceGuidebook;

    @Autowired
    ServiceSpot serviceSpot;

    private static final Logger logger = LogManager.getLogger();



    @RequestMapping(value = "/spots", method = RequestMethod.POST)
    public String saveRequestParams(@RequestParam(value = "locationInput") String locationInput,
                                    @RequestParam(value = "onlySpotsWithBoltedRoutes", required = false)
                                            boolean onlySpotsWithBoltedRoutes,
                                    @RequestParam(value = "ratingMin") String ratingMin,
                                    @RequestParam(value = "ratingMax") String ratingMax,
                                    ModelMap model) {


        model.put("locationInput", locationInput);
        model.put("onlySpotsWithBoltedRoutes", onlySpotsWithBoltedRoutes);
        model.put("ratingMin", ratingMin);
        model.put("ratingMax", ratingMax);

        return "redirect:/escalade/displaySpots";
    }

    /**
     * Note: the returned Guidebook object contains the matching spots and location!
     *
     * @param guidebookId
     * @param model
     * @return
     */
    @RequestMapping(value = "/spots", method = RequestMethod.GET)
    public String getResultTopos(@RequestParam(value = "guidebookId") String guidebookId,
                                 ModelMap model) {

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyId(parseInt(guidebookId));
        List<Spot> spotsForGuidebooks = serviceSpot.findSpotsBasedOnGuidebookId(Integer.parseInt(guidebookId));
        selectedGuidebook.setSpots(spotsForGuidebooks);
        model.put("selectedGuidebook", selectedGuidebook);

        return "spotsFromGuidebook";
    }


    @RequestMapping(value = "/displaySpots", method = RequestMethod.GET)
    public String getResultSpots(@SessionAttribute(value = "locationInput") String locationInput,
                                 @SessionAttribute (value="onlySpotsWithBoltedRoutes", required = false)
                                         boolean onlySpotsWithBoltedRoutes,
                                 @SessionAttribute (value = "ratingMin") String ratingMin,
                                 @SessionAttribute (value = "ratingMax") String ratingMax,
                                 @RequestParam(value = "idSpotToBeCommented") int idSpotToBeCommented,
                                 ModelMap model) {
        String alert;

        try {
            List<Location> resultLocations = serviceLocation.detailledInfoBasedOnLocation(locationInput);
            resultLocations = serviceLocation.filterLocations(resultLocations, onlySpotsWithBoltedRoutes, parseInt(ratingMin), parseInt(ratingMax));
            model.put("resultLocations", resultLocations);
            model.put("idSpotToBeCommented", idSpotToBeCommented);
            model.put("locationInput", locationInput);
            alert = "ok";
            model.put("alert", alert);
            return "spots";
        }catch (Exception e){
            alert = "notFound";
            model.put("alert", alert);
            return "index";
        }
    }




    @ModelAttribute(value = "idSpotToBeCommented")
    public int getIdSpotToBeCommented() {
        int getIdSpotToBeCommented = 0;
        return getIdSpotToBeCommented;
    }




}







