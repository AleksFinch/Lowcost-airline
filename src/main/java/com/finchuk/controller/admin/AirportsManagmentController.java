package com.finchuk.controller.admin;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.Airport;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.AirportService;

import java.util.List;

/**
 * Created by root on 06.04.17.
 */
public class AirportsManagmentController extends Controller {
    AirportService service = ServiceFactory.getAirportService();
    @Override
    public void get(RequestService reqService) {
        List<Airport> airports = service.findAll();
        reqService.setPageAttribute("airports", airports);
    }

    @Override
    public void post(RequestService reqService) {
        String airportId = reqService.getString("airport_id");
        String country = reqService.getString("country");
        String town = reqService.getString("town");
        //TODO: validate
        Airport airport = new Airport();
        airport.setAirportId(airportId);
        airport.setCountry(country);
        airport.setTown(town);
        if(service.add(airport)){
            reqService.redirect("/admin/airport_managing");
        }else{
            reqService.redirect("/admin/airport_managing?already_exist=true");
        }

    }
}
