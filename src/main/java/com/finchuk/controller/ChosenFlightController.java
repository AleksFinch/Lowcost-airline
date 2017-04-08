package com.finchuk.controller;

import com.finchuk.entities.Flight;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.FlightService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 08.04.17.
 */
public class ChosenFlightController extends Controller {
    private static final Logger LOGGER = LogManager.getLogger(ChosenFlightController.class);
    FlightService flightService = ServiceFactory.getFlightService();

    @Override
    public void get(RequestService reqService) {
        String id = reqService.getString("id");
        Long ticketId = Long.valueOf(id);
        Flight flight = flightService.find(ticketId);
        if(flight==null){
            try {
                reqService.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                LOGGER.error("",e);
                throw new IllegalArgumentException();
            }
        }
        reqService.setPageAttribute("flight",flight);

    }
}
