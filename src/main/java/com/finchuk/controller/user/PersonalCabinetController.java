package com.finchuk.controller.user;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.Ticket;
import com.finchuk.entities.User;
import com.finchuk.services.impl.TicketService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 06.04.17.
 */
public class PersonalCabinetController extends Controller {
    @Override
    public void get(RequestService reqService) {
        User user = reqService.getCurrentUser().orElse(new User());

        reqService.setPageAttribute("current_user", user);}
}
