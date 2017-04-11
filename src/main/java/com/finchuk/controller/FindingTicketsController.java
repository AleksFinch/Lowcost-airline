package com.finchuk.controller;

import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.FlightService;

import java.time.format.DateTimeParseException;

/**
 * Created by root on 06.04.17.
 */
public class FindingTicketsController extends Controller {
    FlightService service = ServiceFactory.getFlightService();
    @Override
    public void get(RequestService reqService) {
        String fromTown = reqService.getString("from_town");
        String toTown = reqService.getString("to_town");
        String depTime = reqService.getString("dep_time");
        String timezone = reqService.getString("timezone");

        if(fromTown.isEmpty()||toTown.isEmpty()||
                depTime.isEmpty()){
            return;
        }


        try {
            reqService.setPageAttribute("found_flights", service.getFlightWithSearching(fromTown, toTown, depTime, timezone));
        } catch (DateTimeParseException | NumberFormatException e) {
            reqService.redirect("/find_tickets.html?error=invalid.date");
        }
    }
}
