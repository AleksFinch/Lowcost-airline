package com.finchuk.util;

import com.finchuk.controller.RequestService;
import com.finchuk.entities.Airline;
import com.finchuk.entities.Airport;
import com.finchuk.entities.Flight;
import com.finchuk.entities.Route;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by root on 08.04.17.
 */
public class RequestMapper {
    public static Route mapRoute(RequestService reqService){
        String flight_duration = reqService.getString("flight_time");
        String plane = reqService.getString("plane");
        String airportFrom = reqService.getString("s_airport_from");
        String airportTo = reqService.getString("s_airport_to");
        Long company = Long.valueOf(reqService.getString("s_airline"));

        LocalTime time = LocalTime.parse(flight_duration, DateTimeFormatter.ofPattern("H:mm"));

        Route route = new Route();
        route.setFlightDuration(time);
        route.setPlane(plane);
        Airport airportF = new Airport();
        airportF.setAirportId(airportFrom);
        Airport airportT = new Airport();
        airportT.setAirportId(airportTo);
        Airline airline = new Airline();
        airline.setCompanyId(company);
        route.setAirportFrom(airportF);
        route.setAirportTo(airportT);
        route.setCompany(airline);
        return route;
    }

    public static Flight mapFlight(RequestService reqService){
        Route route = mapRoute(reqService);

        String departureTime = reqService.getString("departure_time");
        BigDecimal startPrice = new BigDecimal(reqService.getString("start_price"));
        BigDecimal startPriceForBusiness = new BigDecimal(reqService.getString("start_price_business"));

        LocalDateTime time = LocalDateTime.parse(departureTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        Flight flight = new Flight();
        flight.setRoute(route);
        flight.setDepartureTime(time);
        flight.setStartPrice(startPrice);
        flight.setStartPriceForBusiness(startPriceForBusiness);
        return flight;
    }


}
