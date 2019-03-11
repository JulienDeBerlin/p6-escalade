package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceGuidebook;
import com.berthoud.ocp6.business.ServiceMember;
import com.berthoud.ocp6.business.ServiceSpotComment;
import com.berthoud.ocp6.model.bean.Guidebook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
public class ControllerAdmin {

    @Autowired
    ServiceGuidebook serviceGuidebook;

    @Autowired
    ServiceSpotComment serviceSpotComment;

    @Autowired
    ServiceMember serviceMember;

    private static final Logger logger = LogManager.getLogger();


    @RequestMapping(value = "/admin/guidebooks", method = RequestMethod.GET)
    public String goToAdminGuidebooks (){
        return "adminGuidebooks";
    }


    @RequestMapping(value = "/admin/guidebooks/isbn", method = RequestMethod.GET)
    public String checkIsbn (@RequestParam(value = "isbn13") String isbn13,
                             ModelMap model){


        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);
        if ( selectedGuidebook == null){
            model.put("message", "notFound");

        } else{
            model.put("selectedGuidebook", selectedGuidebook);
            model.put("step", "guidebookSelected");
        }
        return "adminGuidebooks";
    }


    @RequestMapping(value = "/admin/guidebooks/update", method = RequestMethod.POST)
    public String updateGuidebook (@RequestParam (value = "isbn13") String isbn13,
                                   @RequestParam (value = "name") String name,
                                   @RequestParam (value = "firstnameAuthor") String firstnameAuthor,
                                   @RequestParam (value = "surnameAuthor") String surnameAuthor,
                                   @RequestParam (value = "yearPublication") short yearPublication,
                                   @RequestParam (value = "publisher") String publisher,
                                   @RequestParam (value = "language") String language,
                                   @RequestParam (value = "summary") String summary,
                                   ModelMap model){

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
    public String deleteGuidebook (@RequestParam (value = "isbn13") String isbn13,
                                    ModelMap model){

        serviceGuidebook.deleteGuidebook(serviceGuidebook.findGuidebookbyIsbn(isbn13));
        model.put("message", "guidebookDeleted");

        return "adminGuidebooks";
    }


    @RequestMapping(value = "/admin/guidebooks/deleteLinkGuidebookSpot", method = RequestMethod.GET)
    public String deleteLinkGuidebookSpot (@RequestParam (value = "isbn13") String isbn13,
                                           @RequestParam (value = "spotId") int spotId,
                                           ModelMap model){

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);

        serviceGuidebook.deleteRelationGuidebookSpot(spotId, selectedGuidebook.getId());

        selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);
        model.put("selectedGuidebook", selectedGuidebook);

        model.put("message", "spotDeleted");
        model.put("step", "guidebookSelected");

        return "adminGuidebooks";
    }


    @RequestMapping(value = "admin/deleteComment", method = RequestMethod.GET)
    public String deleteComment(@RequestParam(value = "commentId") int commentId) {

        serviceSpotComment.deleteComment(commentId);
        return ("redirect:/escalade/displaySpots");
    }

    @RequestMapping(value = "admin/delete/memberAccount", method = RequestMethod.POST)
    public String deleteMemberAccount(@RequestParam(value = "userId") int userId) {

        serviceMember.deleteMemberAccount(userId);

        return ("redirect:/escalade/logout");
    }






}
