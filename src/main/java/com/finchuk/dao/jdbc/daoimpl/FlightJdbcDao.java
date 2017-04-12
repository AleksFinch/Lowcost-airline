package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.FlightDao;
import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.RuntimeSqlException;
import com.finchuk.dao.jdbc.daoimpl.template.JdbcHelper;
import com.finchuk.dao.jdbc.mappers.FlightMapper;
import com.finchuk.entities.Flight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by olexandr on 26.03.17.
 */
public class FlightJdbcDao implements FlightDao {
    private static final Logger LOGGER = LogManager.getLogger(FlightJdbcDao.class);

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
    @Override
    public List<Flight> getFlightsByParams(String townFrom, String townTo, LocalDateTime date) {

        Timestamp timestampBefore = Timestamp.valueOf(date);
        date = date.plusDays(1);
        Timestamp timestampAfter = Timestamp.valueOf(date);
        return helper.findObjects("SELECT DISTINCT f.* from flight AS f" +
                "  JOIN route AS r ON r.id=f.route " +
                "  JOIN airport AS a1 ON a1.id=r.airport_from" +
                "  JOIN airport AS a2 ON a2.id=r.airport_to" +
                "  JOIN ticket AS t ON t.flight=f.id" +
                " WHERE (?<f.departure_time)AND" +
                " (f.departure_time<?) AND" +
                "(a1.town = ?) AND " +
                "(a2.town = ?) AND " +
                "t.status = 0"
                , FlightMapper::map,
                timestampBefore,
                timestampAfter,
                townFrom,
                townTo);
    }

    @Override
    public Long totalCount() {
        return helper.findObject("SELECT COUNT(*) FROM flight"
                , (resultSet) -> {
                    Long l;
                    try {
                        l = resultSet.getLong(1);
                    } catch (SQLException e) {
                        LOGGER.error("", e);
                        throw new RuntimeSqlException(e);
                    }
                    return l;
                });
    }

    @Override
    public List<Flight> findWithOffset(Long count, Long from) {
        return helper.findObjects("SELECT * FROM flight LIMIT ? OFFSET ?"
                , FlightMapper::map, count, from);
    }

}
