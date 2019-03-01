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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@SessionAttributes(value= {"IdSpotToBeCommented", "user", "selectedSpot"})

public class ControllerAddComment {

    @Autowired
    ServiceSpotComment serviceSpotComment;
    @Autowired
    ServiceSpot serviceSpot;

    private static final Logger logger = LogManager.getLogger();

    @RequestMapping (value = "/toNewComment", method = RequestMethod.GET)
    public String displayFormNewComment (@RequestParam (value = "IdSpotToBeCommented") int IdSpotToBeCommented,
                                               ModelMap model)
    {
        Spot selectedSpot = serviceSpot.findSpotBasedOnId(IdSpotToBeCommented);
        model.put("selectedSpot", selectedSpot);

        if (!model.containsAttribute("user")){
            model.put("jspAfterLogin", "redirect:/escalade/displaySpots");
            model.put("message", "onlyMembers");
            return ("login");
        }else{
            return ("redirect:/escalade/displaySpots");
        }
    }



    @RequestMapping (value = "/addComment", method = RequestMethod.POST)
    public String addComment (@RequestParam (value = "comment") String comment,
                                @SessionAttribute (value = "user") Member user,
                                @SessionAttribute (value = "selectedSpot") Spot spotToBeCommented,
                                ModelMap model)


    {

        SpotComment newCommentWithoutKey = new SpotComment();
        newCommentWithoutKey.setComment(comment);
        newCommentWithoutKey.setMember(user);
        newCommentWithoutKey.setSpot(spotToBeCommented);
        serviceSpotComment.insertSpotComment(newCommentWithoutKey);

        spotToBeCommented.setId(-1);
        model.put("selectedSpot", spotToBeCommented);
        logger.info(model);

        return ("redirect:/escalade/displaySpots");

    }




}
