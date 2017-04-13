package com.finchuk.dao.jdbc.mappers;

import com.finchuk.dto.Flight;
import com.finchuk.dto.Ticket;
import com.finchuk.dto.TicketStatus;
import com.finchuk.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by olexandr on 25.03.17.
 */
public class TicketMapper {
    private static final Logger LOGGER = LogManager.getLogger(TicketMapper.class);

    public static Ticket map(ResultSet resultSet) {

        Ticket ticket = new Ticket();
        try {
            ticket.setTicketId(resultSet.getLong("id"));
            ticket.setPlaceNumber(resultSet.getInt("place_number"));
            ticket.setPrice(resultSet.getBigDecimal("price"));
            Flight flight = new Flight();
            flight.setFlightId(resultSet.getLong("flight"));
            ticket.setFlight(flight);
            User user = new User();
            user.setUserId(resultSet.getLong("owner"));
            ticket.setOwner(user);
            TicketStatus sta = TicketStatus.values()[resultSet.getInt("status")];
            ticket.setStatus(sta);
            ticket.setWithBaggage(resultSet.getBoolean("with_baggage"));
        } catch (SQLException e) {
            LOGGER.error("can't map object", e);
        }
        return ticket;
    }
}
