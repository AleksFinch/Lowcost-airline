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
        reqService.setAtributesFromFlash("from_town", "to_town", "dep_time", "error");

        String fromTown = reqService.getString("from_town");
        String toTown = reqService.getString("to_town");
        String depTime = reqService.getString("dep_time");
        String timezone = reqService.getString("timezone");

        if(fromTown.isEmpty()||toTown.isEmpty()||
                depTime.isEmpty()){
            return;
        }
        try {
            reqService.setPageAttribute("from_town", fromTown);
            reqService.setPageAttribute("to_town", toTown);
            reqService.setPageAttribute("dep_time", depTime);
            reqService.setPageAttribute("found_flights", service.getFlightWithSearching(fromTown, toTown, depTime, timezone));
        } catch (DateTimeParseException | IllegalArgumentException e) {
            reqService.putFlashParameter("from_town", fromTown);
            reqService.putFlashParameter("to_town", toTown);
            reqService.putFlashParameter("dep_time", depTime);
            if (e.getMessage().equals("past")) {
                reqService.putFlashParameter("error", "invalid.date.past");
            } else {
                reqService.putFlashParameter("error", "invalid.date");
            }

            reqService.redirect("/find_tickets.html");

        }
    }
}
