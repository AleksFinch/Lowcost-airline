package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.FlightDao;
import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.daoimpl.template.JdbcHelper;
import com.finchuk.dao.jdbc.mappers.FlightMapper;
import com.finchuk.entities.Flight;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by olexandr on 26.03.17.
 */
public class FlightJdbcDao implements FlightDao {

    ConnectionManager cm;
    JdbcHelper helper;

    public FlightJdbcDao() {
        this.cm = ConnectionManager.getInstance();
        helper = new JdbcHelper(cm);
    }

    @Override
    public Long add(Flight flight) {
        return helper.insert("INSERT INTO flight " +
                        "(departure_time, route, start_price, start_price_business)" +
                        "VALUES(?,?,?,?)",
                Timestamp.valueOf(flight.getDepartureTime()),
                flight.getRoute().getRouteId(),
                flight.getStartPrice(),
                flight.getStartPriceForBusiness());
    }

    @Override
    public Flight find(Long id) {
        return helper.findObject("SELECT * FROM flight " +
                "WHERE id=?", FlightMapper::map, id);
    }

    @Override
    public void update(Flight flight) {
        helper.update("UPDATE flight " +
                        "SET departure_time = ?," +
                        "route = ?," +
                        "start_price = ?," +
                        "start_price_business = ? " +
                        "WHERE id=?",
                Timestamp.valueOf(flight.getDepartureTime()),
                flight.getRoute().getRouteId(),
                flight.getStartPrice(),
                flight.getStartPriceForBusiness(),
                flight.getFlightId());

    }

    @Override
    public void delete(Long id) {
        helper.remove("DELETE FROM flight " +
                "WHERE id=?", id);
    }

    @Override
    public List<Flight> findAll() {
        return helper.findObjects("SELECT * FROM flight "
                , FlightMapper::map);

    }

}
