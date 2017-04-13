package com.finchuk.dao;

import com.finchuk.dto.Flight;
import com.finchuk.dto.Ticket;
import com.finchuk.dto.User;

import java.util.List;

/**
 * Created by olexandr on 25.03.17.
 */
public interface TicketDao extends Dao<Ticket, Long> {
    List<Ticket> getUserTickets(User user);

    List<Ticket> getFlightTickets(Flight flight);


}
