package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceSpot;
import com.berthoud.ocp6.business.ServiceSpotComment;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.Spot;
import com.berthoud.ocp6.model.bean.SpotComment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@SessionAttributes(value = {"user", "selectedSpot"})

public class ControllerAddComment {

    @Autowired
    ServiceSpotComment serviceSpotComment;
    @Autowired
    ServiceSpot serviceSpot;

    private static final Logger logger = LogManager.getLogger();

    /**
     * This controller-method is the first step when a user, consulting a list of spots after a standard spots-research,
     * want to add a new comment to a spot. It checks if a member is logged in the session.
     * If yes, the request is redirected to {@link ControllerSpots}, the controller in charge of displaying the spots.
     * If no, the user is redirected to the login page.
     *
     * @param idSpotToBeCommented the id of the spot that the user is willing to comment.
     *                            For this spot, a add-a-comment-form will be displayed.
     * @param model               ///
     * @return redirection to the controller in charge of displaying the spots or to the login-page
     */
    @RequestMapping(value = "/toNewComment", method = RequestMethod.GET)
    public String displayFormNewComment1(@RequestParam(value = "idSpotToBeCommented") int idSpotToBeCommented,
                                         ModelMap model) {

        // Situation 1: a user has made a spot-search, and is willing to comment one of the displayed spot
        Spot selectedSpot = serviceSpot.findSpotBasedOnId(idSpotToBeCommented);
        model.put("selectedSpot", selectedSpot);
        model.put("idSpotToBeCommented", idSpotToBeCommented);

        if (!model.containsAttribute("user")) {
            model.put("jspAfterLogin", "redirect:/escalade/displaySpots?idSpotToBeCommented=" + idSpotToBeCommented);
            model.put("message", "onlyMembers");
            return ("login");
        } else {

            return ("redirect:/escalade/displaySpots");
        }

    }


    /**
     * This controller-method is the first step when a user, consulting a list of spots based on a guidebook,
     * want to add a new comment to a spot. It checks if a member is logged in the session.
     * If yes, the request is redirected to {@link ControllerSpots}, the controller in charge of displaying the spots.
     * If no, the user is redirected to the login page.
     *
     * @param idSpotToBeCommented the id of the spot that the user is willing to comment.
     *                            For this spot, a add-a-comment-form will be displayed.
     * @param model               ///
     * @return redirection to the controller in charge of displaying the spots or to the login-page
     */
    @RequestMapping(value = "/toNewCommentForSpotFromGuidebook", method = RequestMethod.GET)
    public String displayFormNewComment2(@RequestParam(value = "idSpotToBeCommented") int idSpotToBeCommented,
                                         ModelMap model) {

        // Situation 2: a user has made a guidebook-search, then has displayed the spots matching to a guidebook, and from there
        // is willing to comment one of the displayed spot

        Spot selectedSpot = serviceSpot.findSpotBasedOnId(idSpotToBeCommented);
        model.put("selectedSpot", selectedSpot);
        model.put("idSpotToBeCommented", idSpotToBeCommented);

        if (!model.containsAttribute("user")) {
            model.put("jspAfterLogin", "redirect:/escalade/displaySpotsFromGuidebook?idSpotToBeCommented=" + idSpotToBeCommented);
            model.put("message", "onlyMembers");
            return ("login");
        } else {

            return ("redirect:/escalade/displaySpotsFromGuidebook");
        }
    }


    /**
     * This controller-method is responsible for adding a new comment to a spot, inserting it in the DB and redirect the
     * request to the right controller method in order to display the same list of spots as before the insert of the comment.
     *
     * @param comment           comment entered by the user
     * @param user              the user who wrote the comment
     * @param spotToBeCommented the commented spot
     * @param redirect          this parameter is only retrieved when sending the add-comment-form from the spotsFromGuidebook.jsp
     *                          It is used to determine where the request should be redirected to.
     * @param model             ///
     * @return the request is redirected to {@link ControllerSpots#displaySpots(String, boolean, String, String, int, ModelMap)} or
     * {@link ControllerSpots#displaySpotsFromGuidebook(String, int, ModelMap)} depending on the list of spots that should be retrieved.
     * <br/>
     * Note: In the redirect-url, idSpotToBeCommented is set to null.
     * This is because such a param is required by the controller-method the request is redirected to, but since the add-a-comment-form
     * shouldn't be displayed anymore, it is set to null.
     */
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam(value = "comment") String comment,
                             @ModelAttribute(value = "redirect") String redirect,
                             @SessionAttribute(value = "user") Member user,
                             @SessionAttribute(value = "selectedSpot") Spot spotToBeCommented,
                             ModelMap model) {

        SpotComment newCommentWithoutKey = new SpotComment();
        newCommentWithoutKey.setComment(comment);
        newCommentWithoutKey.setMember(user);
        newCommentWithoutKey.setSpot(spotToBeCommented);
        serviceSpotComment.insertSpotComment(newCommentWithoutKey);

        model.put("selectedSpot", spotToBeCommented);
        logger.info(model);

        if (redirect.equals("displaySpotsFomGuidebook")) {
            return "redirect:/escalade/displaySpotsFromGuidebook?idSpotToBeCommented=0";

        } else {
            return "redirect:/escalade/displaySpots?idSpotToBeCommented=0";
        }
    }

}
