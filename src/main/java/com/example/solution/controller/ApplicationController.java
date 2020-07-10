package com.example.solution.controller;

import com.example.solution.service.IFindConnectionBetweenCities;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Controller of the application.
 * @author Durga Nihar Muddhuchetty
 * @author nihar.1882@gmail.com
 */
@RestController
public class ApplicationController {

    public static final String BASE_URL="connected";
    public static final String ORIGIN_REQUEST_PARAM="origin";
    public static final String DESTINATION_REQUEST_PARAM="destination";

    @Autowired
    IFindConnectionBetweenCities connectionsService;

    /** Controller method to handle get request
     * @param sourceCity: Source city from the user input
     * @param destinationCity: Destination city from the user input
     */
    @GetMapping(BASE_URL)
    public ResponseEntity<String>  getConnectionInformation(@RequestParam(ORIGIN_REQUEST_PARAM) String sourceCity, @RequestParam(DESTINATION_REQUEST_PARAM) String destinationCity){
        System.out.println(1);
        return ResponseEntity.ok(connectionsService.findIfCitiesAreConnected(sourceCity,destinationCity));
    }

}
