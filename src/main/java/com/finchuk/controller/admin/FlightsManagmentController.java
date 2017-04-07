package com.finchuk.controller.admin;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.Airline;
import com.finchuk.services.impl.AirlineService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by root on 06.04.17.
 */
public class FlightsManagmentController extends Controller {
    AirlineService service = AirlineService.getInstance();
    @Override
    public void get(RequestService reqService) {
        List<Airline> airlines = service.findAll();
        reqService.setPageAttribute("airlines", airlines);
    }

    @Override
    public void post(RequestService reqService) {
        String company = reqService.getString("company");
        String imgPath = reqService.getString("img_path");

        //TODO: validate
        //TODO: Map in another class


    }
}
