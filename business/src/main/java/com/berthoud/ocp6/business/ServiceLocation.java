package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.consumer.contract.dao.SpotDao;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Location;
import com.berthoud.ocp6.model.bean.Spot;
import com.berthoud.ocp6.model.bean.SpotComment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class ServiceLocation {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private SpotDao spotDao;

    @Autowired
    private ServiceSpot serviceSpot;

    @Autowired
    private ServiceGuidebook serviceGuidebook;

    @Autowired
    private ServiceSpotComment serviceSpotComment;


    private static final Logger logger = LogManager.getLogger();


    /**
     * Based on the location input entered by the user, this method retrieves a list of location objects (incl. matching spots and routes...)
     * Exemple: User enters "Auvergne" ---> this method finds all locations of the DB set in Auvergne
     * with all matching spots, routes, guidebooks and commnents
     *
     * @param locationInput the location input entered by the user
     * @return a list of location objects
     */
    public List<Location> detailledInfoBasedOnLocation(String locationInput) {

        return locationDao.findLocationsByTableColomn(locationInput);
    }


    /**
     * This method takes a list of Locations as parameter and for each spot of each location remove the locations that don't
     * fulfil the research criteria (level too low or route not bolted).
     *
     * @param locations        the list of locations to be filtered
     * @param onlyBoltedRoutes is true if unbolted routes should be removed
     * @param levelMin
     * @param levelMax
     * @return
     */
    public List<Location> filterLocations(List<Location> locations, boolean onlyBoltedRoutes, int levelMin, int levelMax) {

        for (Iterator<Location> i = locations.iterator(); i.hasNext(); ) {
            Location location = i.next();
            List<Spot> spots = location.getSpots();
            serviceSpot.filterSpots(spots, onlyBoltedRoutes, levelMin, levelMax);
            if (spots.isEmpty()) {
                i.remove();
            }
        }
        return locations;
    }


    /**
     * This method takes as input a list of Location objects and remove from the list the Guidebook objects that do not
     * fulfil the "loanRequired" - requirement
     *
     * @param locations    the input list of locations
     * @param loanRequired is true if user want to display only guidebook available for loan
     * @return the filtered list of locations
     */
    public List<Location> filterLocationsForTopos(List<Location> locations, boolean loanRequired) {
        for (Iterator<Location> i = locations.iterator(); i.hasNext(); ) {
            Location location = i.next();
            List<Spot> spots = location.getSpots();

            for (Iterator<Spot> j = spots.iterator(); j.hasNext(); ) {
                Spot spot = j.next();
                List<Guidebook> guidebooks = spot.getGuidebooks();
                serviceGuidebook.filterGuidebooksByLoanAvailable(guidebooks, loanRequired);
            }
        }
        return locations;
    }

    /**
     * This method takes a list of Location objects and returns a list of matching Guidebook objects without any duplicates.
     *
     * @param locations the list of locations (cities)
     * @return a list of all guidebooks, without any duplicates, linked to the different spots set in the locations.
     */
    public List<Guidebook> editGuidebookListWithoutDuplicate(List<Location> locations) {

        List<Guidebook> guidebooksWithoutRepetition = new ArrayList<Guidebook>();

        for (Iterator<Location> k = locations.iterator(); k.hasNext(); ) {
            Location location = k.next();
            List<Spot> spots = location.getSpots();

            for (Iterator<Spot> i = spots.iterator(); i.hasNext(); ) {
                Spot spot = i.next();
                List<Guidebook> listGuidebookToBeAdded = spot.getGuidebooks();

                for (Iterator<Guidebook> j = listGuidebookToBeAdded.iterator(); j.hasNext(); ) {
                    Guidebook guidebook = j.next();

                    if (!guidebooksWithoutRepetition.contains(guidebook)) {
                        guidebooksWithoutRepetition.add(guidebook);
                    }
                }
            }
        }
        return guidebooksWithoutRepetition;
    }

    /**
     * This method retrieves from the DB a list of Locations whose attributes (region, departement_name,
     * city_name or zip_code) contain the query.
     * Is used for autocomplete fields.
     *
     * @param query the letter-combinaison entered by the user in the autocomplete field
     * @return a list of Locations contained in the DB and matching with the query.
     */
    public List<String> getLocationProposals(String query) {
        return locationDao.getLocationProposals(query);
    }

    /**
     * This method retrieves from the DB a list of Locations whose city_name or zip_code contains the query.
     * Is used for autocomplete fields.
     *
     * @param query the letter-combinaison entered by the user in the autocomplete field
     * @return a list of Locations contained in the DB and matching with the query.
     */
    public List<String> getCityProposalsForUpdateSpots(String query) {
        return locationDao.getCityProposalsForUpdateSpots(query);
    }

    /**
     * This method find a location based on its name and the name of its departement
     *
     * @param cityName        the name of the location
     * @param departementName the name of the departement
     * @return the location or null if the search has no result
     */
    public Location findLocationByNameAndDepartement(String cityName, String departementName) {
        try {
            return locationDao.findLocationByNameAndDepartement(cityName, departementName);
        } catch (Exception e) {
            logger.info("La recherche ne donne aucun résultat ", e);
            return null;
        }
    }


    /**
     * This method insert in the DB one location and its spot.
     *
     * @param location //
     * @param spot     //
     * @return
     */
    @Transactional
    public Spot insertLocationAndItsSpot(Location location, Spot spot) {

        Location newLocationWithKey = locationDao.insertLocation(location);
        spot.setLocation(newLocationWithKey);
        Spot newSpotwithKey = spotDao.insertSpot(spot);
        newSpotwithKey.setLocation(newLocationWithKey);
        return newSpotwithKey;

    }


    /**
     * This method takes as input a list of Locations objects and removes all spots
     * Each location may have many spots, each spot may have many guidebooks. The methods removes from the spot objects included in the location objects all the spots already included in the spots matching with the Guidebook object.
     *
     * @param listLocations     a list of locations to be cleaned
     * @param selectedGuidebook the spots that are already in this guidebook object will be removed from the list of locations
     * @return the list of location after removal of the already linked spots.
     */
    public List<Location> removeSpotsAlreadyLinked(List<Location> listLocations, Guidebook selectedGuidebook) {

        // Check if some spots are already stored in the selectedGuidebook
        if (selectedGuidebook.getSpots() == null) {
            return listLocations;
        } else {

            // if, yes get a list of the Ids of the spots already linked to the selectedGuidebook
            List<Integer> spotIdsMatchingWithSelectedGuidebook = new ArrayList<>();
            for (Spot s : selectedGuidebook.getSpots()) {
                spotIdsMatchingWithSelectedGuidebook.add(s.getId());
            }

            // the spot objects matching with these IDs are then removed from the original list of locations
            for (Iterator<Location> i = listLocations.iterator(); i.hasNext(); ) {
                Location location = i.next();
                List<Spot> spots = location.getSpots();

                for (Iterator<Spot> j = spots.iterator(); j.hasNext(); ) {
                    Spot spot = j.next();
                    if ((spotIdsMatchingWithSelectedGuidebook.contains(spot.getId()))) {
                        j.remove();
                    }
                }
            }
            return listLocations;
        }
    }


    /**
     * This method takes as input a list of Locations and check if a spot is containeed in one of the location object.
     *
     * @param listLocations the input list of locations
     * @return true if none of the item of the list contains a spot, false otherwise.
     */
    public boolean testIfNoSpot(List<Location> listLocations) {

        for (Iterator<Location> i = listLocations.iterator(); i.hasNext(); ) {
            Location location = i.next();
            List<Spot> spots = location.getSpots();
            if (!spots.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    /**
     * This method sorts the locations contained in a list by ascending order of the zip codes.
     * It further calls the sorting methods, that sort the object-attributes contained in a Location:
     * - spots are sorted by alphabetical order of the nameSpot
     * - comments are sorted by date, from the latest till the oldest
     * - routes are sorted by ascending order of their name
     *
     * @param locations the list of locations to be sorted
     * @return the sorted list of location
     */
    public List<Location> sortLocations(List<Location> locations) {

        if (locations != null) {
            Collections.sort(locations);

            for (Iterator<Location> i = locations.iterator(); i.hasNext(); ) {
                Location location = i.next();
                List<Spot> spots = location.getSpots();

                serviceSpot.sortSpots(spots);

                for (Iterator<Spot> j = spots.iterator(); j.hasNext(); ) {
                    Spot spot = j.next();
                    List<SpotComment> spotComments = spot.getComments();

                    serviceSpotComment.sortSpotComments(spotComments);

                }
            }
        }
        return locations;
    }


}







