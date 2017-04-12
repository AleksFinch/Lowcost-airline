package com.finchuk.controller.admin;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.Airline;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.AirlineService;
import com.finchuk.util.RequestMapper;
import com.finchuk.util.Validator;

import java.util.List;

/**
 * Created by root on 06.04.17.
 */
public class AirlinesManagmentController extends Controller {
    AirlineService service = ServiceFactory.getAirlineService();
    @Override
    public void get(RequestService reqService) {
        reqService.setAtributesFromFlash("company", "img_path", "error");
        List<Airline> airlines = service.findAll();
        reqService.setPageAttribute("airlines", airlines);
    }

    @Override
    public void post(RequestService reqService) {
        saveParamsInFlash(reqService);

        String result = Validator.validateAirline(reqService);
        if (!result.isEmpty()) {
            reqService.redirect("/admin/flight_managing.html");
            reqService.putFlashParameter("error", result);
            return;
        }
        try {
            Airline airline = RequestMapper.mapAirline(reqService);
            service.add(airline);
            reqService.setFlashParamsEmpty("company", "img_path");
            reqService.redirect("/admin/airline_managing");
        } catch (IllegalArgumentException e) {
            reqService.redirect("/admin/airline_managing");

            reqService.putFlashParameter("error", e.getMessage());
        }


    }

    private void saveParamsInFlash(RequestService reqService) {
        String company = reqService.getString("company");
        String imgPath = reqService.getString("img_path");
        reqService.putFlashParameter("company", company);
        reqService.putFlashParameter("img_path", imgPath);
    }
}
