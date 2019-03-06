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
import java.util.List;



@SessionAttributes(value= {"selectedGuidebook", "isbn13", "user"})
@Controller
public class ControllerAddGuidebook {

    private static final Logger logger = LogManager.getLogger();


    @Autowired
    ServiceGuidebook serviceGuidebook;
    @Autowired
    ServiceLocation serviceLocation;
    @Autowired
    ServiceSpot serviceSpot;

    @RequestMapping(value = "addcontent/guidebook", method = RequestMethod.GET)
    public String goToAddGuidebook (ModelMap model) {
        if (model.containsAttribute("user")) {
            return "newGuidebook";
        }
        else {
            String message = "onlyMembers";
            model.put("message", message);
            model.put("jspAfterLogin", "newGuidebook");
            return "login";
        }
    }


    @RequestMapping(value = "addcontent/guidebook/isbn", method = RequestMethod.POST)
    public String testIsbn (@RequestParam(value = "isbn13") String isbn13,
                            ModelMap model) {

        Guidebook selectedGuidebook = new Guidebook();

        if (serviceGuidebook.findGuidebookbyIsbn(isbn13)==null){
            selectedGuidebook.setId(-1);
            model.put("step", "step2");
        } else {
            selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);
            model.put("step", "step3");
        }
        model.put("selectedGuidebook", selectedGuidebook);
        model.put("isbn13", isbn13);
        return "newGuidebook";
    }


    @RequestMapping(value = "addcontent/guidebook", method = RequestMethod.POST)
    public String saveDetailsGuidebook (@RequestParam (value = "name") String name,
                                        @RequestParam (value = "firstnameAuthor") String firstnameAuthor,
                                        @RequestParam (value = "surnameAuthor") String surnameAuthor,
                                        @RequestParam (value = "yearPublication") short yearPublication,
                                        @RequestParam (value = "publisher") String publisher,
                                        @RequestParam (value = "language") String language,
                                        @RequestParam (value = "summary") String summary,
                                        @SessionAttribute (value = "isbn13") String isbn13,
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

    @RequestMapping(value = "/spotsForGuidebook", method = RequestMethod.GET)
    public String displaySpots (@RequestParam (value = "locationSpotsForGuidebook") String locationSpotsForGuidebook,
                                     @ModelAttribute (value = "selectedGuidebook") Guidebook selectedGuidebook,
                                     ModelMap model) {
        try {
            List<Location> listMatchingLocations = serviceLocation.detailledInfoBasedOnLocation(locationSpotsForGuidebook);
            listMatchingLocations = serviceLocation.removeSpotsAlreadyLinked(listMatchingLocations, selectedGuidebook);
            boolean noSpot = serviceLocation.testIfNoSpot(listMatchingLocations);

            model.put("listMatchingLocations", listMatchingLocations);

            if (noSpot == true){
                model.put("alert", "noSpot");
            }else {
                model.put("alert", "ok");
            }

        } catch (Exception e) {
            logger.info(e);
            model.put("alert", "notFound");
        }
        model.put("step", "step3");
        return "newGuidebook";
    }


//    @RequestMapping(value = "/spotsForGuidebook1", method = RequestMethod.POST)
//    public String addSpotstoGuide (@RequestParam (value = "selectedSpots") List<Integer> listSpotId,
//                                         RedirectAttributes redirectAttrs,
//                                         ModelMap model) {
//
//        if(!model.containsAttribute("spotsLinkedToGuidebook")) {
//            model.addAttribute("spotsLinkedToGuidebook", new ArrayList<Spot>());
//        }
//
//        redirectAttrs.addFlashAttribute("listSpotId", listSpotId);
//        return "redirect:/spotsForGuidebook2";
//    }




    @RequestMapping(value = "/spotsForGuidebook", method = RequestMethod.POST)
    public String addSpotstoGuide (@ModelAttribute (value = "selectedGuidebook") Guidebook selectedGuidebook,
                                   @RequestParam (value = "selectedSpots") List<Integer> listSpotId,
                                   ModelMap model) {

        serviceGuidebook.insertRelationGuidebookSpots(listSpotId, selectedGuidebook);
        selectedGuidebook = serviceGuidebook.findGuidebookbyId(selectedGuidebook.getId());
        model.put("selectedGuidebook", selectedGuidebook);
        model.put("step", "step3");
        return "newGuidebook";
    }


//    @ModelAttribute("spotsLinkedToGuidebook")
//    public List <Spot> getAttribute_SpotsLinkedToGuidebook(){
//    return new ArrayList<>();
//    }
//







}