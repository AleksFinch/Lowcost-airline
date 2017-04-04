package com.finchuk.dao;

import com.finchuk.entities.Flight;
import com.finchuk.entities.Ticket;
import com.finchuk.entities.User;

import java.util.List;

/**
 * Created by olexandr on 25.03.17.
 */
public interface TicketDao extends Dao<Ticket, Long> {
    List<Ticket> getUserTickets(User user);

    List<Ticket> getFlightTickets(Flight flight);
}
