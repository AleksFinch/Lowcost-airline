package com.finchuk.services.impl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.entities.Flight;
import com.finchuk.services.AbstractEntityService;

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
}
