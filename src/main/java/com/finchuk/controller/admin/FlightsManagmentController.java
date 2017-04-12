package com.finchuk.controller.admin;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.Airline;
import com.finchuk.entities.Airport;
import com.finchuk.entities.Flight;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.AirlineService;
import com.finchuk.services.impl.AirportService;
import com.finchuk.services.impl.FlightService;
import com.finchuk.util.RequestMapper;
import com.finchuk.util.Validator;

import java.time.format.DateTimeParseException;
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
        reqService.setAtributesFromFlash("plane",
                "s_airline",
                "s_airport_from",
                "s_airport_to",
                "start_price",
                "start_price_business",
                "tickets_count",
                "flight_time",
                "departure_time",
                "error");
        List<Airline> airlines = airlineService.findAll();
        reqService.setPageAttribute("sel_airlines", airlines);
        List<Airport> airports = airportService.findAll();
        reqService.setPageAttribute("sel_airports", airports);
        List<Flight> flights = flightService.findAll();
        reqService.setPageAttribute("flights", flights);
        Map<Flight, Long> map = new HashMap<>();
        for (Flight f :
                flights) {
            Long free = flightService.freeTickets(f);
            map.put(f, free);
        }
        reqService.setPageAttribute("availableTickets", map);
    }

    @Override
    public void post(RequestService reqService) {
        saveParamsInFlash(reqService);
        String result = Validator.validateFlight(reqService);
        if (!result.isEmpty()) {
            reqService.redirect("/admin/flight_managing.html");
            reqService.putFlashParameter("error", result);
            return;
        }

        try {
            Flight flight = RequestMapper.mapFlight(reqService);
            String ticCount = reqService.getString("tickets_count");
            Integer ticketsCount = Integer.valueOf(ticCount);
            reqService.setFlashParamsEmpty(
                    "plane",
                    "s_airline",
                    "s_airport_from",
                    "s_airport_to",
                    "start_price",
                    "start_price_business",
                    "tickets_count",
                    "flight_time",
                    "departure_time");

            flightService.addFlightWithTickets(flight, ticketsCount);
            reqService.redirect("/admin/flight_managing");

        } catch (NumberFormatException | DateTimeParseException e) {
            reqService.redirect("/admin/flight_managing.html");
            reqService.putFlashParameter("error", e.getMessage());

        }

    }

    private void saveParamsInFlash(RequestService reqService) {
        String flightDuration = reqService.getString("flight_time");
        String plane = reqService.getString("plane");
        String airportFrom = reqService.getString("s_airport_from");
        String airportTo = reqService.getString("s_airport_to");
        String airlineComp = reqService.getString("s_airline");
        String ticCount = reqService.getString("tickets_count");
        String departureTime = reqService.getString("departure_time");
        String startPrice = reqService.getString("start_price");
        String startPriceForBusiness = reqService.getString("start_price_business");

        reqService.putFlashParameter("plane", plane);
        reqService.putFlashParameter("s_airport_from", airportFrom);
        reqService.putFlashParameter("s_airport_to", airportTo);
        reqService.putFlashParameter("start_price", startPrice);
        reqService.putFlashParameter("start_price_business", startPriceForBusiness);
        reqService.putFlashParameter("tickets_count", ticCount);
        reqService.putFlashParameter("departure_time", departureTime);
        reqService.putFlashParameter("flight_time", flightDuration);
        reqService.putFlashParameter("s_airline", airlineComp);


    }
}
