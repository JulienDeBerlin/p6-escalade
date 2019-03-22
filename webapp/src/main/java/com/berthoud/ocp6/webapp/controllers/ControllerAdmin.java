package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.*;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Route;
import com.berthoud.ocp6.model.bean.Spot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SessionAttributes(value = {"locationInput", "selectedLocations", "selectedSpot"})
@Controller
public class ControllerAdmin {

    @Autowired
    ServiceGuidebook serviceGuidebook;

    @Autowired
    ServiceSpotComment serviceSpotComment;

    @Autowired
    ServiceMember serviceMember;

    @Autowired
    ServiceLocation serviceLocation;

    @Autowired
    ServiceSpot serviceSpot;

    @Autowired
    ServiceRoute serviceRoute;


    private static final Logger logger = LogManager.getLogger();

    /**
     * This controller-method is used to display the page dedicated to the moderation of referenced guidebooks (update or delete items)
     *
     * @return adminGuidebooks.jsp
     */
    @RequestMapping(value = "/admin/guidebooks", method = RequestMethod.GET)
    public String goToAdminGuidebooks() {
        return "adminGuidebooks";
    }


    /**
     * This controller-methods check the ISBN number entered by the administrator and retrieve from the DB the matching guidebook
     *
     * @param isbn13 the isbn number of the guidebook to be edited
     * @param model  //
     * @return adminGuidebooks.jsp
     */
    @RequestMapping(value = "/admin/guidebooks/isbn", method = RequestMethod.GET)
    public String checkIsbn(@RequestParam(value = "isbn13") String isbn13,
                            ModelMap model) {


        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);
        if (selectedGuidebook == null) {
            model.put("message", "notFound");

        } else {
            model.put("selectedGuidebook", selectedGuidebook);
            model.put("step", "guidebookSelected");
        }
        return "adminGuidebooks";
    }


    @RequestMapping(value = "/admin/guidebooks/update", method = RequestMethod.POST)
    public String updateGuidebook(@RequestParam(value = "isbn13") String isbn13,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "firstnameAuthor") String firstnameAuthor,
                                  @RequestParam(value = "surnameAuthor") String surnameAuthor,
                                  @RequestParam(value = "yearPublication") short yearPublication,
                                  @RequestParam(value = "publisher") String publisher,
                                  @RequestParam(value = "language") String language,
                                  @RequestParam(value = "summary") String summary,
                                  ModelMap model) {

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);
        selectedGuidebook.setName(name);
        selectedGuidebook.setFirstnameAuthor(firstnameAuthor);
        selectedGuidebook.setSurnameAuthor(surnameAuthor);
        selectedGuidebook.setYearPublication(yearPublication);
        selectedGuidebook.setPublisher(publisher);
        selectedGuidebook.setLanguage(language);
        selectedGuidebook.setSummary(summary);

        serviceGuidebook.updateGuidebook(selectedGuidebook);

        model.put("selectedGuidebook", selectedGuidebook);
        model.put("message", "guidebookUpdated");
        model.put("step", "guidebookSelected");

        return "adminGuidebooks";
    }


    @RequestMapping(value = "/admin/guidebooks/delete", method = RequestMethod.POST)
    public String deleteGuidebook(@RequestParam(value = "isbn13") String isbn13,
                                  ModelMap model) {

        serviceGuidebook.deleteGuidebook(serviceGuidebook.findGuidebookbyIsbn(isbn13));
        model.put("message", "guidebookDeleted");

        return "adminGuidebooks";
    }


    /**
     * Each guidebook is linked to the spots it describes. This controller-method is used to delete an existing ling between a guidebook
     * and a spot.
     *
     * @param isbn13 the isbn number of the guidebook to be edited
     * @param spotId the ID of the spot whose link with the guidebook should be deleted
     * @param model  //
     * @return adminGuidebooks.jsp
     */
    @RequestMapping(value = "/admin/guidebooks/deleteLinkGuidebookSpot", method = RequestMethod.GET)
    public String deleteLinkGuidebookSpot(@RequestParam(value = "isbn13") String isbn13,
                                          @RequestParam(value = "spotId") int spotId,
                                          ModelMap model) {

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);

        serviceGuidebook.deleteRelationGuidebookSpot(spotId, selectedGuidebook.getId());

        selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);
        model.put("selectedGuidebook", selectedGuidebook);

        model.put("message", "spotDeleted");
        model.put("step", "guidebookSelected");

        return "adminGuidebooks";
    }


    @RequestMapping(value = "/admin/deleteComment", method = RequestMethod.GET)
    public String deleteComment(@RequestParam(value = "commentId") int commentId) {

        serviceSpotComment.deleteComment(commentId);
        return ("redirect:/escalade/displaySpots?idSpotToBeCommented=0");
    }

    /**
     * This controller-method is used to display the page dedicated to the moderation of referenced spots and routes (update or delete items)
     *
     * @return adminSpots.jsp
     */
    @RequestMapping(value = "/admin/spots", method = RequestMethod.GET)
    public String gotToAdminSpots() {
        return "adminSpots";
    }


    /**
     * This controller-method is used in the process of updating (or deleting) a spot of a route. It saves the name of the city where
     * the spot to be edited is located
     *
     * @param locationInput the city where the spot to be edited is located.
     * @param model         //
     * @return the request is then redirected to another controller-method in charge of displaying the adminSpots.jsp
     */
    @RequestMapping(value = "/admin/spots/locationInput", method = RequestMethod.POST)
    public String saveParamLocationInput(@RequestParam(value = "locationInput") String locationInput,
                                         ModelMap model) {

        model.put("locationInput", locationInput);
        model.put("step", "step2");
        return "redirect:locationInput/displaySpot";
    }

    /**
     * This controller-method is used to display the spots-admin-page, where the admin can edit spots and routes.
     *
     * @param locationInput the city where the spot to be edited is located.
     * @param step          this attribute is used to hide or display some part of the JSP, depending on which step of the editing-process has been reached.
     * @param model         //
     * @return adminSpots.jsp
     */
    @RequestMapping(value = "/admin/spots/locationInput/displaySpot")
    public String displaySpots(@SessionAttribute(value = "locationInput") String locationInput,
                               @RequestParam(value = "step") String step,
                               ModelMap model) {

        try {
            List<Location> selectedLocations = serviceLocation.detailledInfoBasedOnLocation(locationInput);
            serviceLocation.sortLocations(selectedLocations);

            model.put("step", step);
            model.put("locationInput", locationInput);
            model.put("selectedLocations", selectedLocations);

        } catch (Exception e) {
            logger.info(e);
        }
        return "adminSpots";
    }


    @RequestMapping(value = "/admin/spots/update", method = RequestMethod.POST)
    public String updateSpot(@RequestParam(value = "spotId") int spotId,
                             @RequestParam(value = "nameSpot") String nameSpot,
                             @RequestParam(value = "nameArea") String nameArea,
                             @RequestParam(value = "spotAccess") String spotAccess,
                             ModelMap model) {

        Spot spotUpdated = new Spot();
        spotUpdated.setId(spotId);
        spotUpdated.setNameSpot(nameSpot);
        spotUpdated.setNameArea(nameArea);
        spotUpdated.setAccess(spotAccess);
        serviceSpot.updateSpot(spotUpdated);

        model.put("step", "step2");

        return "redirect:locationInput/displaySpot";
    }


    @RequestMapping(value = "/admin/spots/delete", method = RequestMethod.POST)
    public String deleteSpot(@RequestParam(value = "spotId") int spotId) {

        serviceSpot.deleteSpot(spotId);

        return "redirect:locationInput/displaySpot";
    }

    /**
     * This controller-method is used to display the routes matching to the selected spot, in order to edit these routes
     *
     * @param spotId the id of the spot whose routes should be edited
     * @param model  //
     * @return the request is then redirected to another controller-method in charge of displaying the adminSpots.jsp
     */
    @RequestMapping(value = "/admin/spots/accessRoute", method = RequestMethod.POST)
    public String accessRoute(@RequestParam(value = "spotId") int spotId,
                              ModelMap model) {

        Spot selectedSpot = serviceSpot.findSpotBasedOnId(spotId);
        serviceRoute.sortRoutes(selectedSpot.getRoutes());

        model.put("selectedSpot", selectedSpot);
        model.put("step", "step3");

        return "redirect:locationInput/displaySpot";
    }


    @RequestMapping(value = "/admin/routes/update", method = RequestMethod.POST)
    public String updateRoute(@RequestParam(value = "name") String name,
                              @RequestParam(value = "id") int id,
                              @RequestParam(value = "nbPitch") byte nbPitch,
                              @RequestParam(value = "indexPitch") byte indexPitch,
                              @RequestParam(value = "rating") String rating,
                              @RequestParam(value = "nbAnchor") int nbAnchor,
                              @SessionAttribute(value = "selectedSpot") Spot selectedSpot,
                              ModelMap model) {

        Route routeUpdated = new Route();
        routeUpdated.setId(id);
        routeUpdated.setName(name);
        routeUpdated.setNbPitch(nbPitch);
        routeUpdated.setIndexPitch(indexPitch);
        routeUpdated.setRating(rating);
        routeUpdated.setNbAnchor(nbAnchor);

        serviceRoute.updateRoute(routeUpdated);

        selectedSpot = serviceSpot.findSpotBasedOnId(selectedSpot.getId());
        serviceRoute.sortRoutes(selectedSpot.getRoutes());

        model.put("selectedSpot", selectedSpot);
        model.put("step", "step3");

        return "redirect:/escalade/admin/spots/locationInput/displaySpot";
    }


    @RequestMapping(value = "/admin/routes/delete", method = RequestMethod.POST)
    public String deleteRoute(@RequestParam(value = "id") int id,
                              @SessionAttribute(value = "selectedSpot") Spot selectedSpot,
                              ModelMap model) {

        serviceRoute.deleteRoute(id);
        selectedSpot = serviceSpot.findSpotBasedOnId(selectedSpot.getId());
        serviceRoute.sortRoutes(selectedSpot.getRoutes());

        model.put("selectedSpot", selectedSpot);
        model.put("step", "step3");

        return "redirect:/escalade/admin/spots/locationInput/displaySpot";
    }


}
