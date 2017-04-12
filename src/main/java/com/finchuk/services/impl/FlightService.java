package com.finchuk.services.impl;

import com.finchuk.dao.FlightDao;
import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dao.jdbc.transaction.Transaction;
import com.finchuk.entities.Flight;
import com.finchuk.entities.Route;
import com.finchuk.entities.Ticket;
import com.finchuk.entities.TicketStatus;
import com.finchuk.services.AbstractEntityService;
import com.finchuk.services.factory.ServiceFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by olexandr on 29.03.17.
 */
public class FlightService extends AbstractEntityService<Flight, Long> {
   private RouteService routeService ;
    private TicketService ticketService ;

    public FlightService() {
        dao = JdbcDaoFactory.getInstance().getFlightDao();
    }

    public void init(){
        routeService = ServiceFactory.getRouteService();
        ticketService = ServiceFactory.getTicketService();
    }
    @Override
    protected void setId(Flight obj, Long id) {
        obj.setFlightId(id);
    }

    @Override
    protected void loadTails(Flight obj) {
        obj.setRoute(routeService.find(obj.getRoute().getRouteId()));
        obj.setTickets(ticketService.getFlightTickets(obj));

    }


    public List<Flight> getFlightWithSearching(String townFrom, String townTo, String depDate, String timezone) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int timez = Integer.valueOf(timezone);

        LocalDateTime date = LocalDate.parse(depDate, format).atStartOfDay();
        date = date.plusMinutes(timez);
        LocalDateTime dateNow = LocalDateTime.now(ZoneOffset.UTC);
        if (dateNow.isAfter(date)) {
            throw new IllegalArgumentException("past");
        }
        List<Flight> tickets = ((FlightDao) dao).getFlightsByParams(townFrom.toLowerCase(),townTo.toLowerCase(),date);
        tickets.forEach(e->loadTails(e));
        return tickets;
    }

    public void addFlightWithTickets(Flight flight, int ticketsCount){
        Transaction.doTransaction(()->{
            Route route = routeService.loadOrCreate(flight.getRoute());
            flight.setRoute(route);
            add(flight);
            Ticket ticket =new Ticket();
            ticket.setFlight(flight);
            ticket.setStatus(TicketStatus.FREE);
            ticket.setPrice(flight.getStartPrice());
            for (int i = 0; i <ticketsCount; i++) {
                ticketService.add(ticket);
            }
        });
    }

    public Long freeTickets(Flight f){
        return f.getTickets()
                .stream()
                .filter(e->e.getStatus()== TicketStatus.FREE)
                .count();

    }

    public Long totalCount() {
        return ((FlightDao) dao).totalCount();
    }

    public List<Flight> findWithOffset(Long count, Long from) {
        List<Flight> flights = ((FlightDao) dao).findWithOffset(count, from);
        flights.forEach(e -> loadTails(e));
        return flights;
    }
}
