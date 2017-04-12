package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.AirportDao;
import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.daoimpl.template.JdbcHelper;
import com.finchuk.dao.jdbc.mappers.AirportMapper;
import com.finchuk.dto.Airport;

import java.util.List;

/**
 * Created by olexandr on 26.03.17.
 */
public class AirportJdbcDao implements AirportDao {
    ConnectionManager cm;
    JdbcHelper helper;

    public AirportJdbcDao() {
        this.cm = ConnectionManager.getInstance();
        helper = new JdbcHelper(cm);
    }


    @Override
    public String add(Airport airport) {
        helper.insert("INSERT INTO airport " +
                        "(id,country,town)" +
                        "VALUES(?,?,?)",
                airport.getAirportId(),
                airport.getCountry(),
                airport.getTown());
        return airport.getAirportId();
    }

    @Override
    public Airport find(String id) {
        return helper.findObject("SELECT * FROM airport " +
                "WHERE id=?", AirportMapper::map, id);
    }

    @Override
    public void update(Airport airport) {
        helper.update("UPDATE airport " +
                        "SET country = ?," +
                        "town = ? " +
                        "WHERE id=?",
                airport.getCountry(),
                airport.getTown(),
                airport.getAirportId());

    }

    @Override
    public void delete(String id) {
        helper.remove("DELETE FROM airport " +
                "WHERE id=?", id);
    }

    @Override
    public List<Airport> findAll() {
        return helper.findObjects("SELECT * FROM airport "
                , AirportMapper::map);

    }

}
