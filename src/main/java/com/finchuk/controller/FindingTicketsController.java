package com.finchuk.controller;

import com.finchuk.entities.Ticket;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.FlightService;
import com.finchuk.services.impl.TicketService;

import java.util.ArrayList;
import java.util.List;

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
        if(fromTown.isEmpty()||toTown.isEmpty()||depTime.isEmpty()){
            return;
        }

        reqService.setPageAttribute("found_flights", service.getFlightWithSearching(fromTown,toTown,depTime));
    }
}
