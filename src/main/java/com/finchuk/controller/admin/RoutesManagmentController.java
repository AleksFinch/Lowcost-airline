package com.finchuk.controller.admin;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.Airline;
import com.finchuk.entities.Airport;
import com.finchuk.entities.Route;
import com.finchuk.services.impl.AirlineService;
import com.finchuk.services.impl.AirportService;
import com.finchuk.services.impl.RouteService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by root on 06.04.17.
 */
public class RoutesManagmentController extends Controller {
    AirlineService airlineService = AirlineService.getInstance();
    AirportService airportService = AirportService.getInstance();
    RouteService routeService = RouteService.getInstance();
    @Override
    public void get(RequestService reqService) {
        List<Airline> airlines = airlineService.findAll();
        reqService.setPageAttribute("sel_airlines", airlines);
        List<Airport> airports = airportService.findAll();
        reqService.setPageAttribute("sel_airports", airports);
        List<Route> routes = routeService.findAll();
        reqService.setPageAttribute("routes", routes);

    }

    @Override
    public void post(RequestService reqService) {
        String company = reqService.getString("company");
        String imgPath = reqService.getString("img_path");



    }
}
