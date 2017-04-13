package com.finchuk.services.impl;

import com.finchuk.dao.TicketDao;
import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dto.Flight;
import com.finchuk.dto.Ticket;
import com.finchuk.dto.User;
import com.finchuk.services.AbstractEntityService;
import com.finchuk.services.factory.ServiceFactory;

import java.util.List;

/**
 * Created by olexandr on 29.03.17.
 */
public class TicketService extends AbstractEntityService<Ticket, Long> {
     private FlightService flightService;
    private UserService userService;

    public TicketService() {
        dao = JdbcDaoFactory.getInstance().getTicketDao();
    }

    public void init(){
        flightService = ServiceFactory.getFlightService();
        userService = ServiceFactory.getUserService();
    }
    @Override
    protected void setId(Ticket obj, Long id) {
        obj.setTicketId(id);
    }

    @Override
    protected void loadTails(Ticket obj) {
        obj.setFlight(flightService.find(obj.getFlight().getFlightId()));
        obj.setOwner(userService.find(obj.getOwner().getUserId()));
    }

    public List<Ticket> getUserTickets(User user) {
        List<Ticket> tickets = ((TicketDao) dao).getUserTickets(user);
        tickets.forEach(e -> e.setFlight(flightService.find(e.getFlight().getFlightId())));
        tickets.forEach(e -> e.setOwner(user));
        return tickets;
    }

    public List<Ticket> getFlightTickets(Flight flight) {
        List<Ticket> tickets = ((TicketDao) dao).getFlightTickets(flight);
        tickets.forEach(e -> e.setFlight(flight));
        tickets.forEach(e -> e.setOwner(userService.find(e.getOwner().getUserId())));
        return tickets;
    }



    }
