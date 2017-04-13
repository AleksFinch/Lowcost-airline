package com.finchuk.services.impl;

import com.finchuk.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

/**
 * Created by root on 13.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {


    @Mock
    private TicketService ticketService;
    @Mock
    private FlightService flightService;
    @Mock
    private Clock clock;

    PaymentService service;

    private static final Long TICKET_FLIGHT_COUNT = 10l;

    private static final long instant = 1492071167;//2017-04-13T08:12:47.988Z
    private Flight flight = getFlight();
    private BigDecimal startPrice = new BigDecimal("102.50");
    private BigDecimal startPriceForBusiness = new BigDecimal("302.00");
    private LocalDateTime departureTime = LocalDateTime.of(2017, 04, 16,
            7, 17);
    //4264 min
    //129600 max


    @Before
    public void setUp() throws Exception {
        flight = getFlight();

        given(flightService.find(1l)).willReturn(flight);
        given(flightService.freeTickets(flight)).willReturn(TICKET_FLIGHT_COUNT - TICKET_FLIGHT_COUNT / 2);
        given(clock.instant()).willReturn(Instant.ofEpochSecond(instant));

        given(clock.getZone()).willReturn(ZoneOffset.UTC);
        service = new PaymentService();

        service.setClock(clock);
        service.setFlightService(flightService);
        service.setTicketService(ticketService);


    }

    @Test
    public void testCalculatePrice() throws Exception {
        Ticket ticket = service.reserveTicket(1l, false, true, new User());
        assertTrue(ticket.getPrice()
                .subtract(new BigDecimal("2227.7393"))
                .compareTo(BigDecimal.valueOf(0.001)) != 1);

    }

    @Test
    public void testCalculatePriceZero() throws Exception {
        flight.setStartPrice(BigDecimal.ZERO);
        flight.setStartPriceForBusiness(BigDecimal.ZERO);
        Ticket ticket = service.reserveTicket(1l, false, true, new User());

        assertTrue(ticket.getPrice()
                .subtract(new BigDecimal("0"))
                .compareTo(BigDecimal.valueOf(0.001)) != 1);

    }

    @Test
    public void testCalculatePriceWithoutPriority() throws Exception {
        Ticket ticket = service.reserveTicket(1l, true, false, new User());
        assertTrue(ticket.getPrice()
                .subtract(new BigDecimal("756.103"))
                .compareTo(BigDecimal.valueOf(0.01)) != 1);

    }

    @Test
    public void testCalculatePriceWithoutBaggage() throws Exception {
        Ticket ticket = service.reserveTicket(1l, false, false, new User());
        assertTrue(ticket.getPrice()
                .subtract(new BigDecimal("302.441"))
                .compareTo(BigDecimal.valueOf(0.01)) != 1);

    }


    public Flight getFlight() {

        Route route = new Route();
        route.setRouteId(10l);


        Flight flight = new Flight();
        flight.setRoute(route);
        flight.setDepartureTime(departureTime);
        flight.setStartPrice(startPrice);
        flight.setStartPriceForBusiness(startPriceForBusiness);
        flight.setFlightId(10l);
        List<Ticket> list = new ArrayList<>();
        for (int i = 0; i < TICKET_FLIGHT_COUNT / 2; i++) {
            Ticket ticket = new Ticket();
            ticket.setFlight(flight);
            ticket.setStatus(TicketStatus.FREE);
            ticket.setPrice(flight.getStartPrice());
            list.add(ticket);
        }

        for (long i = TICKET_FLIGHT_COUNT / 2; i < TICKET_FLIGHT_COUNT; i++) {
            Ticket ticket = new Ticket();
            ticket.setFlight(flight);
            ticket.setStatus(TicketStatus.PAID);
            ticket.setPrice(flight.getStartPrice());
            list.add(ticket);
        }
        flight.setTickets(list);
        return flight;
    }


}