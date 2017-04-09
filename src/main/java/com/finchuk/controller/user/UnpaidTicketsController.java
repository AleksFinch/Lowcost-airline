package com.finchuk.controller.user;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.Ticket;
import com.finchuk.entities.TicketStatus;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.TicketService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by root on 06.04.17.
 */
public class UnpaidTicketsController extends Controller {
    TicketService service = ServiceFactory.getTicketService();
    @Override
    public void get(RequestService reqService) {
        List<Ticket> tickets = new ArrayList<>();
        reqService.getCurrentUser()
                .ifPresent(user->
                        tickets.addAll(service
                                .getUserTickets(user)
                                .stream()
                                .filter(e->e.getStatus()== TicketStatus.CHECK_PAYMENT)
                                .collect(Collectors.toList())));
        reqService.setPageAttribute("personal_tickets", tickets);
    }
}
