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
public class PersonalTicketsController extends Controller {
    TicketService service =TicketService.getInstance();
    @Override
    public void get(RequestService reqService) {
        List<Ticket> tickets = new ArrayList<>();
        reqService.getCurrentUser()
                .ifPresent(e->tickets.addAll(service.getUserTickets(e)));
        reqService.setPageAttribute("personal_tickets", tickets);
    }
}
