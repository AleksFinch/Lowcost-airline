package com.finchuk.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by olexandr on 25.03.17.
 */
public class Ticket {
    private Long ticketId;
    private int placeNumber;
    private BigDecimal price;
    private Flight flight;
    private User owner;
    private TicketStatus status;
    private boolean withBaggage;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }


    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isWithBaggage() {
        return withBaggage;
    }

    public void setWithBaggage(boolean withBaggage) {
        this.withBaggage = withBaggage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return placeNumber == ticket.placeNumber &&
                withBaggage == ticket.withBaggage &&
                Objects.equals(ticketId, ticket.ticketId) &&
                (price != null ?
                        price.compareTo(ticket.price) == 0
                        : ticket.price == null) &&
                (flight != null && ticket.flight != null ?
                        Objects.equals(flight.getFlightId(), ticket.flight.getFlightId()) :
                        flight == ticket.flight) &&
                (owner != null && ticket.owner != null ?
                        Objects.equals(owner.getUserId(), ticket.owner.getUserId()) :
                        owner == ticket.owner) &&
                status == ticket.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, placeNumber, price, flight, owner, status, withBaggage);
    }
}
