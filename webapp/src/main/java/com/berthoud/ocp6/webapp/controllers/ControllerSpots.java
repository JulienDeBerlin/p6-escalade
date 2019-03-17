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

import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

@SessionAttributes(value = {"alert", "locationInput", "onlySpotsWithBoltedRoutes", "ratingMin", "ratingMax", "guidebookIdForSpots"})
@Controller
public class ControllerSpots {

    @Autowired
    ServiceLocation serviceLocation;

    @Autowired
    ServiceGuidebook serviceGuidebook;

    @Autowired
    ServiceSpot serviceSpot;

    private static final Logger logger = LogManager.getLogger();

    /**
     * This controller-method is used in the process of spots-search and -display. It is only responsible for retrieving the
     * search parameters entered by the user and putting them in the session.
     *
     * @param locationInput             the location where the spots are to be looked for
     * @param onlySpotsWithBoltedRoutes filter
     * @param ratingMin                 filter
     * @param ratingMax                 filter
     * @param model                     ///
     * @return Once the search-params are stored in the session, the request is redirected to the
     * controller-method {@link #displaySpots(String, boolean, String, String, int, ModelMap)}
     */

    @RequestMapping(value = "/spots", method = RequestMethod.POST)
    public String saveSpotRequestParams(@RequestParam(value = "locationInput") String locationInput,
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
     * This controller-method is responsible for displaying a list of spots, according to the filters that may have been set by the user.
     *
     * @param locationInput             the location where the spots are to be looked for. Value is retrieved from the session.
     * @param onlySpotsWithBoltedRoutes filter. Value is retrieved from the session.
     * @param ratingMin                 filter. Value is retrieved from the session.
     * @param ratingMax                 filter. Value is retrieved from the session.
     * @param idSpotToBeCommented       This parameter is only used in case the user want to comment a site.
     *                                  It determines for which spots the form "add-a-comment" has to be displayed in the view.
     *                                  In case a comment should be add, the "add-a-comment-request" is first sent to {@link ControllerAddComment},
     *                                  the {@param idSpotToBeCommented} is put in the model then and redirect to this controller.
     *                                  In case no {@param idSpotToBeCommented} is available in the request (which is the case for the standard
     *                                  display of spots without comments), the default value provided by the method {@link #getIdSpotToBeCommented}
     * @param model                     ///
     * @return spots.jsp, the page where the spots are displayed or the home page if any exception happens that make the spot search and display
     * impossible.
     */
    @RequestMapping(value = "/displaySpots", method = RequestMethod.GET)
    public String displaySpots(@SessionAttribute(value = "locationInput") String locationInput,
                               @SessionAttribute(value = "onlySpotsWithBoltedRoutes", required = false)
                                       boolean onlySpotsWithBoltedRoutes,
                               @SessionAttribute(value = "ratingMin") String ratingMin,
                               @SessionAttribute(value = "ratingMax") String ratingMax,
                               @RequestParam(value = "idSpotToBeCommented") int idSpotToBeCommented,
                               ModelMap model) {

        logger.info("enter method displaySpots");

        List<Location> resultLocations = serviceLocation.detailledInfoBasedOnLocation(locationInput);
        logger.info("after call method detailledInfoBasedOnLocation");

        resultLocations = serviceLocation.filterLocations(resultLocations, onlySpotsWithBoltedRoutes, parseInt(ratingMin), parseInt(ratingMax));
        logger.info("after call method filterLocations");

        serviceLocation.sortLocations(resultLocations);

        model.put("resultLocations", resultLocations);
        model.put("idSpotToBeCommented", idSpotToBeCommented);
        model.put("locationInput", locationInput);
        model.put("alert", "ok");
        return "spots";
    }


    /**
     * Spots can be researched not only according to parameters such as location, rating.. but also based on a guidebook.
     * This controller-method is responsible for retrieving the id of the guidebook selected by the user and place it in the session.
     *
     * @param guidebookIdForSpots the id of the guidebook, the user would like to display the matching spots of.
     * @param model               //
     * @return
     */
    @RequestMapping(value = "/spotsFromGuidebook", method = RequestMethod.GET)
    public String saveRequestParamSpotForAGuidebook(@RequestParam(value = "guidebookIdForSpots") String guidebookIdForSpots,
                                                    ModelMap model) {

        model.put("guidebookIdForSpots", guidebookIdForSpots);
        return "redirect:/escalade/displaySpotsFromGuidebook";
    }


    /**
     * Spots can be researched not only according to parameters such as location, rating.. but also based on a guidebook.
     * This controller-method is responsible for displaying the spots covered by a single guidebook.
     * It is called when a user has made a guidebook-search and then wants to display the spots matching to a guidebook.
     *
     * @param guidebookIdForSpots the Id of the guidebook whose matching spots should be displayed.
     * @param idSpotToBeCommented This parameter is only used in case the user want to comment a site.
     *                            It determines for which spots the form "add-a-comment" has to be displayed in the view.
     *                            In case a comment should be add, the "add-a-comment-request" is first sent to {@link ControllerAddComment},
     *                            the {@param idSpotToBeCommented} is put in the model then and redirect to this controller.
     *                            In case no {@param idSpotToBeCommented} is available in the request (which is the case for the standard
     *                            display of spots without comments), the default value provided by the method {@link #getIdSpotToBeCommented}
     * @param model               ///
     * @return spotsFromGuidebook.jsp, a view that looks like the main spots-view (spots.jsp)
     */
    @RequestMapping(value = "/displaySpotsFromGuidebook", method = RequestMethod.GET)
    public String displaySpotsFromGuidebook(@SessionAttribute(value = "guidebookIdForSpots") String guidebookIdForSpots,
                                            @RequestParam(value = "idSpotToBeCommented") int idSpotToBeCommented,
                                            ModelMap model) {

        logger.info("enter method displaySpotsBasedOnAGuidebook");

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyId(parseInt(guidebookIdForSpots));
        logger.info("after call method findGuidebookbyId");

        List<Spot> spotsForGuidebooks = serviceSpot.findSpotsBasedOnGuidebookId(Integer.parseInt(guidebookIdForSpots));
        logger.info("after call method findSpotsBasedOnGuidebookId");

        selectedGuidebook.setSpots(spotsForGuidebooks);

        model.put("selectedGuidebook", selectedGuidebook);
        model.put("idSpotToBeCommented", idSpotToBeCommented);

        return "spotsFromGuidebook";
    }


    /**
     * This method is used in combination with the methods {@link #displaySpots} and {@link #displaySpotsFromGuidebook}
     * which require a parameter "idSpotToBeCommented". This method provides a default value for this parameter, in case
     * the request addressed to {@link #displaySpots} or {@link #displaySpotsFromGuidebook}  has no such param.
     *
     * @return a default value for idSpotToBeCommented
     */
    @ModelAttribute(value = "idSpotToBeCommented")
    public int getIdSpotToBeCommented() {
        int getIdSpotToBeCommented = 0;
        return getIdSpotToBeCommented;
    }


}







