package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceGuidebook;
import com.berthoud.ocp6.business.ServiceLocation;
import com.berthoud.ocp6.business.ServiceSpot;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@SessionAttributes(value = {"selectedGuidebook", "isbn13", "user", "actualYear"})
@Controller
public class ControllerAddGuidebook {

    private static final Logger logger = LogManager.getLogger();


    @Autowired
    ServiceGuidebook serviceGuidebook;
    @Autowired
    ServiceLocation serviceLocation;
    @Autowired
    ServiceSpot serviceSpot;


    /**
     * This controller-method checks if a member is logged in the session. If yes, the page "newGuidebook" is displayed.
     * If no, the user is redirected to the login page.
     *
     * @param model ///
     * @return login- or newGuidebook-page
     */
    @RequestMapping(value = "/addcontent/guidebook", method = RequestMethod.GET)
    public String goToAddGuidebook(ModelMap model) {

        LocalDate actualDate = LocalDate.now();
        int actualYear = actualDate.getYear();
        model.put("actualYear", actualYear);


        if (model.containsAttribute("user")) {
            return "newGuidebook";

        } else {
            String message = "onlyMembers";
            model.put("message", message);
            model.put("jspAfterLogin", "newGuidebook");
            return "login";
        }
    }


    /**
     * In order to register a new guidebook in the DB, the first step is to enter its ISBN nb.
     * This controller-method checks if the ISBN matches with a guidebook already stored in the DB.
     * If yes, it means that this guidebook has already been referenced. It is still possible to link it to further spots (step 3)
     * If no, the guidebook has to be referenced first (step 2)
     *
     * @param isbn13 the ISBN number entered by the user
     * @param model  ///
     * @return newGuidebook-page (at step 2 or 3)
     */
    @RequestMapping(value = "/addcontent/guidebook/isbn")
    public String testIsbn(@RequestParam(value = "isbn13") String isbn13,
                           ModelMap model) {

        Guidebook selectedGuidebook = new Guidebook();

        // If the guidebook whose ISBN has been entered doesn't exist in the DB, it should be referenced first (step2)
        if (serviceGuidebook.findGuidebookbyIsbn(isbn13) == null) {
            selectedGuidebook.setId(-1);
            model.put("step", "step2");
        }
        //Otherwise we can skip directly to the referencing of the matching routes (step3)
        else {
            selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);
            model.put("step", "step3");
        }
        model.put("selectedGuidebook", selectedGuidebook);
        model.put("isbn13", isbn13);

        return "newGuidebook";
    }

    /**
     * This controller-method is used for referencing a new guidebook (step2 of the process).
     * All the details of the guidebooks entered by the user in the form are retrieved by the controller and put in a Guidebook bean.
     * which is then inserted in the DB.
     *
     * @return back to the newGuidebook-page
     */
    @RequestMapping(value = "/addcontent/guidebook", method = RequestMethod.POST)
    public String saveDetailsGuidebook(@RequestParam(value = "name") String name,
                                       @RequestParam(value = "firstnameAuthor") String firstnameAuthor,
                                       @RequestParam(value = "surnameAuthor") String surnameAuthor,
                                       @RequestParam(value = "yearPublication") short yearPublication,
                                       @RequestParam(value = "publisher") String publisher,
                                       @RequestParam(value = "language") String language,
                                       @RequestParam(value = "summary") String summary,
                                       @SessionAttribute(value = "isbn13") String isbn13,
                                       ModelMap model) {

        Guidebook newGuidebookWithoutKey = new Guidebook();
        newGuidebookWithoutKey.setIsbn13(isbn13);
        newGuidebookWithoutKey.setName(name);
        newGuidebookWithoutKey.setFirstnameAuthor(firstnameAuthor);
        newGuidebookWithoutKey.setSurnameAuthor(surnameAuthor);
        newGuidebookWithoutKey.setPublisher(publisher);
        newGuidebookWithoutKey.setYearPublication(yearPublication);
        newGuidebookWithoutKey.setLanguage(language);
        newGuidebookWithoutKey.setSummary(summary);

        Guidebook newGuidebookWithKey = serviceGuidebook.insertGuidebook(newGuidebookWithoutKey);

        model.put("selectedGuidebook", newGuidebookWithKey);
        model.put("step", "step3");

        return "newGuidebook";
    }


    /**
     * This controller-method is used for linking spots to the guidebook (step 3 of the process).The linking works like this:
     * 1/ based on the location input entered by the user, a list of Locations is generated (with included objects like spots)
     * 2/ the spots that are already linked to the guidebook are removed from the list
     *
     * @param locationSpotsForGuidebook the location input entered by the user.
     * @param selectedGuidebook         the guidebook the user is willing to link spots to
     * @param model                     ///
     * @return
     */
    @RequestMapping(value = "/spotsForGuidebook", method = RequestMethod.GET)
    public String displaySpots(@RequestParam(value = "locationSpotsForGuidebook") String locationSpotsForGuidebook,
                               @ModelAttribute(value = "selectedGuidebook") Guidebook selectedGuidebook,
                               ModelMap model) {
        try {
            List<Location> listMatchingLocations = serviceLocation.detailledInfoBasedOnLocation(locationSpotsForGuidebook);
            listMatchingLocations = serviceLocation.removeSpotsAlreadyLinked(listMatchingLocations, selectedGuidebook);
            boolean noSpot = serviceLocation.testIfNoSpot(listMatchingLocations);

            model.put("listMatchingLocations", listMatchingLocations);

            if (noSpot) {
                model.put("alert", "noSpot");
            } else {
                model.put("alert", "ok");
            }

        } catch (Exception e) {
            logger.info(e);
            model.put("alert", "notFound");
        }
        model.put("step", "step3");
        return "newGuidebook";
    }


    /**
     * TThis controller-method is used for linking spots to the guidebook. The spots selected by the user are link to the guidebook
     *
     * @param selectedGuidebook the guidebook the user is willing to link spots to
     * @param listSpotId        the list of the Ids of the spots selected by the user
     * @param model             ///
     * @return
     */
    @RequestMapping(value = "/spotsForGuidebook", method = RequestMethod.POST)
    public String addSpotstoGuide(@ModelAttribute(value = "selectedGuidebook") Guidebook selectedGuidebook,
                                  @RequestParam(value = "selectedSpots") List<Integer> listSpotId,
                                  ModelMap model) {

        serviceGuidebook.insertRelationGuidebookSpots(listSpotId, selectedGuidebook);

        selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(selectedGuidebook.getIsbn13());
        model.put("selectedGuidebook", selectedGuidebook);
        model.put("step", "step3");
        return "newGuidebook";
    }
}
