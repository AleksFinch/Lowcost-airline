package com.finchuk.controller.admin;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.*;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.AirlineService;
import com.finchuk.services.impl.AirportService;
import com.finchuk.services.impl.FlightService;
import com.finchuk.services.impl.RouteService;
import com.finchuk.util.RequestMapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 06.04.17.
 */
public class FlightsManagmentController extends Controller {
    AirlineService airlineService = ServiceFactory.getAirlineService();
    AirportService airportService = ServiceFactory.getAirportService();
    FlightService flightService = ServiceFactory.getFlightService();
    @Override
    public void get(RequestService reqService) {
        List<Airline> airlines = airlineService.findAll();
        reqService.setPageAttribute("sel_airlines", airlines);
        List<Airport> airports = airportService.findAll();
        reqService.setPageAttribute("sel_airports", airports);
        List<Flight> flights = flightService.findAll();
        reqService.setPageAttribute("flights", flights);
        Map<Flight, Long> map=new HashMap<>();
        for (Flight f:
             flights) {
            Long free = flightService.freeTickets(f);
            map.put(f,free);
        }
        reqService.setPageAttribute("availableTickets",map);
    }

    @Override
    public void post(RequestService reqService) {
        Flight flight = RequestMapper.mapFlight(reqService);
        Integer ticketsCount = Integer.valueOf(reqService.getString("tickets_count"));
        flightService.addFlightWithTickets(flight,ticketsCount);
        reqService.redirect("/admin/flight_managing");
    }
}
