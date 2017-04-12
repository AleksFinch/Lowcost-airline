package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.RouteDao;
import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.daoimpl.template.JdbcHelper;
import com.finchuk.dao.jdbc.mappers.RouteMapper;
import com.finchuk.dto.Route;

import java.sql.Time;
import java.util.List;

/**
 * Created by olexandr on 26.03.17.
 */
public class RouteJdbcDao implements RouteDao {
    ConnectionManager cm;
    JdbcHelper helper;

    public RouteJdbcDao() {
        this.cm = ConnectionManager.getInstance();
        helper = new JdbcHelper(cm);
    }

    @Override
    public Long add(Route route) {
        return helper.insert("INSERT INTO route " +
                        "(flight_duration," +
                        "plane," +
                        "airport_from," +
                        "airport_to," +
                        "company)" +
                        "VALUES(?,?,?,?,?)",
                Time.valueOf(route.getFlightDuration()),
                route.getPlane(),
                route.getAirportFrom().getAirportId(),
                route.getAirportTo().getAirportId(),
                route.getCompany().getCompanyId());
    }

    @Override
    public Route find(Long id) {
        return helper.findObject("SELECT * FROM route " +
                "WHERE id=?", RouteMapper::map, id);
    }

    @Override
    public void update(Route route) {
        helper.update("UPDATE route " +
                        "SET " +
                        "flight_duration = ?, " +
                        "plane = ?, " +
                        "airport_from = ?, " +
                        "airport_to = ?, " +
                        "company = ? " +
                        "WHERE id = ? ",
                Time.valueOf(route.getFlightDuration()),
                route.getPlane(),
                route.getAirportFrom().getAirportId(),
                route.getAirportTo().getAirportId(),
                route.getCompany().getCompanyId(),
                route.getRouteId());

    }

    @Override
    public void delete(Long id) {
        helper.remove("DELETE FROM route " +
                "WHERE id=?", id);
    }

    @Override
    public List<Route> findAll() {
        return helper.findObjects("SELECT * FROM route "
                , RouteMapper::map);

    }

    @Override
    public Route findWhole(Route route) {
        return helper.findObject("SELECT * FROM route " +
                "WHERE flight_duration=? AND " +
                "plane=? AND " +
                "airport_from = ? AND " +
                "airport_to=? AND " +
                "company=?", RouteMapper::map,
                route.getFlightDuration(),
                route.getPlane(),
                route.getAirportFrom().getAirportId(),
                route.getAirportTo().getAirportId(),
                route.getCompany().getCompanyId());
    }
}
