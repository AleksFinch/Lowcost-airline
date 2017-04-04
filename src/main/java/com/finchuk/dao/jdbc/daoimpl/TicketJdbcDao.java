package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.TicketDao;
import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.daoimpl.template.JdbcHelper;
import com.finchuk.dao.jdbc.mappers.TicketMapper;
import com.finchuk.entities.Flight;
import com.finchuk.entities.Ticket;
import com.finchuk.entities.User;

import java.util.List;

/**
 * Created by olexandr on 26.03.17.
 */
public class TicketJdbcDao implements TicketDao {
    ConnectionManager cm;
    JdbcHelper helper;

    public TicketJdbcDao() {
        this.cm = ConnectionManager.getInstance();
        helper = new JdbcHelper(cm);
    }

    @Override
    public Long add(Ticket ticket) {
        //TODO: use optional
        return helper.insert("INSERT INTO ticket " +
                        "(place_number," +
                        "price," +
                        "flight," +
                        "owner," +
                        "status," +
                        "with_baggage)" +
                        "VALUES(?,?,?,?,?,?)",
                ticket.getPlaceNumber(),
                ticket.getPrice(),
                ticket.getFlight().getFlightId(),
                ticket.getOwner().getUserId(),
                ticket.getStatus().getValue(),
                ticket.isWithBaggage());
    }

    @Override
    public Ticket find(Long id) {
        return helper.findObject("SELECT * FROM ticket " +
                "WHERE id=?", TicketMapper::map, id);
    }

    @Override
    public void update(Ticket ticket) {
        helper.update("UPDATE ticket " +
                        "SET " +
                        "place_number = ?," +
                        "price = ?," +
                        "flight = ?," +
                        "owner = ?," +
                        "status = ?, " +
                        "with_baggage = ? " +
                        "WHERE id=?",
                ticket.getPlaceNumber(),
                ticket.getPrice(),
                ticket.getFlight().getFlightId(),
                ticket.getOwner().getUserId(),
                ticket.getStatus().getValue(),
                ticket.isWithBaggage(),
                ticket.getTicketId());

    }

    @Override
    public void delete(Long id) {
        helper.remove("DELETE FROM ticket " +
                "WHERE id=?", id);
    }

    @Override
    public List<Ticket> findAll() {
        return helper.findObjects("SELECT * FROM ticket "
                , TicketMapper::map);

    }

    @Override
    public List<Ticket> getUserTickets(User user) {
        return helper.findObjects("SELECT * FROM ticket WHERE owner = ? "
                , TicketMapper::map, user.getUserId());
    }

    @Override
    public List<Ticket> getFlightTickets(Flight flight) {
        return helper.findObjects("SELECT * FROM ticket WHERE flight = ? "
                , TicketMapper::map, flight.getFlightId());
    }
}
