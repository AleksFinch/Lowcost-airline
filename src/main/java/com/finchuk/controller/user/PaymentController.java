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

/**
 * Created by root on 09.04.17.
 */
public class PaymentController extends Controller {
    PaymentService service = ServiceFactory.getPaymentService();
    TicketService ticketService = ServiceFactory.getTicketService();

    @Override
    public void post(RequestService reqService) {
        String ticketId = reqService.getString("ticket_id");
        String status = reqService.getString("result");

        Long id = Validator.tryParseLong(ticketId);
        if(id==null){
            reqService.setNotFound();
            return;
        }
        Ticket ticket = ticketService.find(id);
        User user = reqService.getCurrentUser().get();
        if (user == null
                || !user.equals( ticket.getOwner())
                ||ticket.getStatus()!= TicketStatus.CHECK_PAYMENT) {
            reqService.setNotFound();
        }
        if(status.equals("success")){
            service.payFor(ticket);
            reqService.redirect("/user/personal_tickets");
        }else if(status.equals("fail")){
            service.failPaymentFor(ticket);
            reqService.redirect("/find_tickets");
        }else{
            reqService.setNotFound();
        }


    }
}
