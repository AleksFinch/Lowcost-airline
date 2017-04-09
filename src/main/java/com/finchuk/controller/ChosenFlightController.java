package com.finchuk.controller;

import com.finchuk.entities.Flight;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.FlightService;
import com.finchuk.util.Validator;
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
        Long ticketId = Validator.tryParseLong(id);
        if(ticketId==null){
            reqService.setNotFound();
            return;
        }
        Flight flight = flightService.find(ticketId);
        if(flight==null){
            reqService.setNotFound();
            return;
        }
        reqService.setPageAttribute("flight",flight);

    }
}
