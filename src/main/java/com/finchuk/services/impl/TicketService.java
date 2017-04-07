package com.finchuk.services.impl;

import com.finchuk.dao.TicketDao;
import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.entities.Flight;
import com.finchuk.entities.Ticket;
import com.finchuk.entities.User;
import com.finchuk.services.AbstractEntityService;

import java.util.List;

/**
 * Created by olexandr on 29.03.17.
 */
public class TicketService extends AbstractEntityService<Ticket, Long> {
    private static TicketService ticketService = new TicketService();

    private FlightService flightService = FlightService.getInstance();
    private UserService userService = UserService.getInstance();

    private TicketService() {
        dao = JdbcDaoFactory.getInstance().getTicketDao();
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



    public static TicketService getInstance() {
        return ticketService;
    }
}
