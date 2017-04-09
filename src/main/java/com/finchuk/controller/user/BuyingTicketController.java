package com.finchuk.controller.user;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.entities.Ticket;
import com.finchuk.entities.TicketStatus;
import com.finchuk.entities.User;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.PaymentService;
import com.finchuk.services.impl.TicketService;
import com.finchuk.util.Validator;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 09.04.17.
 */
public class BuyingTicketController extends Controller {
    PaymentService service = ServiceFactory.getPaymentService();
    TicketService ticketService = ServiceFactory.getTicketService();

    @Override
    public void get(RequestService reqService) {
        String ticketId = reqService.getString("id");
        Long id = Validator.tryParseLong(ticketId);
        if(id==null){
            reqService.setNotFound();
            return;
        }
        Ticket ticket = ticketService.find(id);
        User user = reqService.getCurrentUser().get();
        if (user != null
                && user.equals(ticket.getOwner())
                &&ticket.getStatus()== TicketStatus.CHECK_PAYMENT) {
            reqService.setPageAttribute("ticket", ticket);
            reqService.setPageAttribute("price", ticket.getPrice());
        } else {
            reqService.setNotFound();

        }
    }

    @Override
    public void post(RequestService reqService) {
        String flightId = reqService.getString("flight_id");
        String withBaggage = reqService.getString("with_baggage");
        String privilege = reqService.getString("privilege");

        Long id = Validator.tryParseLong(flightId);
        if(id==null){
            reqService.setNotFound();
            return;
        }

        boolean withBagg = withBaggage.equals("on");
        boolean priv = privilege.equals("on");
        User user = reqService.getCurrentUser().get();
        Ticket ticket = service.reserveTicket(id, withBagg, priv, user);


        if (ticket == null) {
            reqService.redirect("/chosen_flight?id=" + id + "&ran_out=true");
        } else {

            reqService.redirect("/user/buy_ticket?id=" + ticket.getTicketId());
        }


    }
}
