package com.finchuk.controller.admin;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.Airline;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.AirlineService;
import com.finchuk.util.Validator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by root on 06.04.17.
 */
public class AirlinesManagmentController extends Controller {
    AirlineService service = ServiceFactory.getAirlineService();
    @Override
    public void get(RequestService reqService) {
        List<Airline> airlines = service.findAll();
        reqService.setPageAttribute("airlines", airlines);
    }

    @Override
    public void post(RequestService reqService) {
        String result = Validator.validateAirline(reqService);
        if (!result.isEmpty()) {
            reqService.redirect("/admin/flight_managing.html?error=" + result);
            return;
        }
        String company = reqService.getString("company");
        String imgPath = reqService.getString("img_path");

        URI url;
        try {
            url = new URI(imgPath);
        } catch (URISyntaxException e) {
            reqService.redirect("/admin/airline_managing?error=invalid.imgpath");
            return;
        }
        Airline airline = new Airline();
        airline.setCompanyName(company);
        airline.setImgPath(url);
        if(service.add(airline)){
            reqService.redirect("/admin/airline_managing");
        }else{
            reqService.redirect("/admin/airline_managing?error=true");
        }

    }
}
