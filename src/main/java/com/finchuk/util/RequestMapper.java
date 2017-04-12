package com.finchuk.util;

import com.finchuk.controller.RequestService;
import com.finchuk.entities.Airline;
import com.finchuk.entities.Airport;
import com.finchuk.entities.Flight;
import com.finchuk.entities.Route;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by root on 08.04.17.
 */
public class RequestMapper {
    public static Airline mapAirline(RequestService reqService) {

        String company = reqService.getString("company");
        String imgPath = reqService.getString("img_path");
        URI url;
        try {
            url = new URI(imgPath);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("invalid.imgpath");
        }
        Airline airline = new Airline();
        airline.setCompanyName(company);
        airline.setImgPath(url);
        return airline;
    }

    public static Route mapRoute(RequestService reqService){
        String flightDuration = reqService.getString("flight_time");
        String plane = reqService.getString("plane");
        String airportFrom = reqService.getString("s_airport_from");
        String airportTo = reqService.getString("s_airport_to");
        String airlineComp = reqService.getString("s_airline");
        Long company;
        try {
            company = Long.valueOf(airlineComp);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("invalid.company");
        }
        LocalTime time;
        try {
            time = LocalTime.parse(flightDuration, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException | NullPointerException e) {
            throw new DateTimeParseException("invalid.duration", flightDuration, 0);
        }

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
        BigDecimal startPrice;
        BigDecimal startPriceForBusiness;

        try {
            startPrice = new BigDecimal(reqService.getString("start_price"));
            startPriceForBusiness = new BigDecimal(reqService.getString("start_price_business"));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("invalid.price");
        }

        LocalDateTime time;
        try {

            time = LocalDateTime.parse(departureTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        } catch (DateTimeParseException | NullPointerException e) {
            throw new DateTimeParseException("invalid.departure", departureTime, 0);
        }

        Flight flight = new Flight();
        flight.setRoute(route);
        flight.setDepartureTime(time);
        flight.setStartPrice(startPrice);
        flight.setStartPriceForBusiness(startPriceForBusiness);
        return flight;
    }


    public static Airport mapAirport(RequestService reqService) {
        String airportId = reqService.getString("airport_id");
        String country = reqService.getString("country");
        String town = reqService.getString("town");
        Airport airport = new Airport();
        airport.setAirportId(airportId);
        airport.setCountry(country);
        airport.setTown(town);
        return airport;
    }


}
