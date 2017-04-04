package com.finchuk.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by olexandr on 25.03.17.
 */
public class Flight {
    private Long flightId;
    private LocalDateTime departureTime;
    private Route route;
    private Collection<Ticket> tickets;
    private BigDecimal startPrice;
    private BigDecimal startPriceForBusiness;


    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getStartPriceForBusiness() {
        return startPriceForBusiness;
    }

    public void setStartPriceForBusiness(BigDecimal startPriceForBusiness) {
        this.startPriceForBusiness = startPriceForBusiness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightId, flight.flightId) &&
                Objects.equals(departureTime, flight.departureTime) &&
                (route != null && flight.route != null ?
                        Objects.equals(route.getRouteId(), flight.route.getRouteId()) :
                        route == flight.route) &&
                (startPrice != null ?
                        startPrice.compareTo(flight.startPrice) == 0
                        : flight.startPrice == null) &&
                (startPriceForBusiness != null ?
                        startPriceForBusiness.compareTo(flight.startPriceForBusiness) == 0
                        : flight.startPriceForBusiness == null) &&
                (tickets != null ? (flight.tickets != null
                        && flight.tickets.size() == tickets.size()
                        && tickets.containsAll(flight.tickets))
                        : flight.tickets == null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, departureTime, route, startPrice, startPriceForBusiness);
    }
}
