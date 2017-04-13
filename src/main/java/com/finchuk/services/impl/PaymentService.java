package com.finchuk.services.impl;

import com.finchuk.dto.Flight;
import com.finchuk.dto.Ticket;
import com.finchuk.dto.TicketStatus;
import com.finchuk.dto.User;
import com.finchuk.services.factory.ServiceFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by root on 09.04.17.
 */
public class PaymentService {

    private TicketService ticketService ;
    private FlightService flightService ;
    Clock clock;

    public void init(){
        flightService = ServiceFactory.getFlightService();
        ticketService = ServiceFactory.getTicketService();
        clock = Clock.systemUTC();
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void setFlightService(FlightService flightService) {
        this.flightService = flightService;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void payFor(Ticket t){
        if(t.getStatus()==TicketStatus.PAID){
            throw new IllegalStateException("already payed");
        }
        if(t.getOwner()==null){
            throw new IllegalStateException("without owner");
        }
        t.setStatus(TicketStatus.PAID);
        ticketService.update(t);
    }

    public void failPaymentFor(Ticket t){
        if(t.getStatus()==TicketStatus.PAID){
            throw new IllegalStateException("already payed");
        }
        t.setStatus(TicketStatus.FREE);
        t.setOwner(null);
        t.setPrice(t.getFlight().getStartPrice());
        t.setWithBaggage(false);
        t.setPlaceNumber(0);
        ticketService.update(t);
    }

    public synchronized Ticket reserveTicket(Long flightId, boolean withBagg, boolean privilege, User user){
        Flight flight = flightService.find(flightId);

        LocalDateTime currTime = LocalDateTime.now(clock);
        LocalDateTime depTime = flight.getDepartureTime();
        if(currTime.isAfter(depTime)){
            return null;
        }
        Long free = flightService.freeTickets(flight);
        if (free==0l){
            return null;
        }
        for (Ticket t:
             flight.getTickets()) {
            if(t.getStatus()== TicketStatus.FREE){
                t.setStatus(TicketStatus.CHECK_PAYMENT);
                t.setWithBaggage(withBagg);
                t.setPlaceNumber(privilege?1:0);
                recalculatePrice(t,withBagg,privilege,currTime,free);
                t.setOwner(user);
                ticketService.update(t);
                return t;
            }
        }
        return null;
    }

    private void recalculatePrice(Ticket t, boolean withBagg, boolean privilege, LocalDateTime currTime, Long free){
        LocalDateTime depTime = t.getFlight().getDepartureTime();
        BigDecimal price = privilege?
                t.getFlight().getStartPriceForBusiness() :
                t.getFlight().getStartPrice();

        Long until = currTime.until(depTime, ChronoUnit.MINUTES);
        Long maxUntil = depTime.minusMonths(3).until(depTime, ChronoUnit.MINUTES);
        until = Math.min(until, maxUntil);
        BigDecimal timeInPercent = BigDecimal.valueOf(2.0)
                .subtract(BigDecimal.valueOf(until)
                        .divide(BigDecimal.valueOf(maxUntil), 10, RoundingMode.HALF_UP));
        price = price.multiply(timeInPercent);

        price = withBagg?price.multiply(BigDecimal.valueOf(2.5)):price;
        BigDecimal freeInPercents = BigDecimal.valueOf(2.0)
                .subtract(BigDecimal.valueOf(free)
                        .divide(BigDecimal.valueOf(t.getFlight().getTickets().size())));

        price = price.multiply(freeInPercents);
        t.setPrice(price);
    }
}
