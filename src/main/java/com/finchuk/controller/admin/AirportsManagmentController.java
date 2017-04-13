package com.finchuk.controller.admin;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.dto.Airport;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.AirportService;
import com.finchuk.util.RequestMapper;
import com.finchuk.util.Validator;

import java.util.List;

/**
 * Created by root on 06.04.17.
 */
public class AirportsManagmentController extends Controller {
    AirportService service = ServiceFactory.getAirportService();

    @Override
    public void get(RequestService reqService) {
        reqService.setAtributesFromFlash("airport_id", "country", "town", "error");
        List<Airport> airports = service.findAll();
        reqService.setPageAttribute("airports", airports);
    }

    @Override
    public void post(RequestService reqService) {
        saveParamsInFlash(reqService);
        String result = Validator.validateAirport(reqService);
        if (!result.isEmpty()) {
            reqService.redirect("/admin/airport_managing.html");
            reqService.putFlashParameter("error", result);
            return;
        }
        Airport airport = RequestMapper.mapAirport(reqService);

        if (service.add(airport)) {
            reqService.setFlashParamsEmpty("airport_id", "country", "town");
            reqService.redirect("/admin/airport_managing");
        } else {
            reqService.putFlashParameter("error", "airport.already.exist");
            reqService.redirect("/admin/airport_managing");
        }


    }

    private void saveParamsInFlash(RequestService reqService) {

        String airportId = reqService.getString("airport_id");
        String country = reqService.getString("country");
        String town = reqService.getString("town");
        reqService.putFlashParameter("airport_id", airportId);
        reqService.putFlashParameter("country", country);
        reqService.putFlashParameter("town", town);
    }
}
