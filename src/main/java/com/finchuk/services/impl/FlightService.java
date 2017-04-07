package com.finchuk.services.impl;

import com.finchuk.dao.FlightDao;
import com.finchuk.dao.TicketDao;
import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.entities.Flight;
import com.finchuk.entities.Ticket;
import com.finchuk.services.AbstractEntityService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by olexandr on 29.03.17.
 */
public class FlightService extends AbstractEntityService<Flight, Long> {
    private static FlightService flightService = new FlightService();
    private RouteService routeService = RouteService.getInstance();
    private TicketService ticketService = TicketService.getInstance();

    private FlightService() {
        dao = JdbcDaoFactory.getInstance().getFlightDao();
    }

    @Override
    protected void setId(Flight obj, Long id) {
        obj.setFlightId(id);
    }

    @Override
    protected void loadTails(Flight obj) {
        obj.setRoute(routeService.find(obj.getRoute().getRouteId()));
    }

    public static FlightService getInstance() {
        return flightService;
    }

    public List<Flight> getFlightWithSearching(String townFrom, String townTo, String depDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(depDate, format);
        List<Flight> tickets = ((FlightDao) dao).getFlightsByParams(townFrom.toLowerCase(),townTo.toLowerCase(),date);
        tickets.forEach(e->loadTails(e));
        return tickets;
    }
}
