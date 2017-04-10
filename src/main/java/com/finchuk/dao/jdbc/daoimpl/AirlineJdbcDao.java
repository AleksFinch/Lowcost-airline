package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.AirlineDao;
import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.daoimpl.template.JdbcHelper;
import com.finchuk.dao.jdbc.mappers.AirlineMapper;
import com.finchuk.entities.Airline;

import java.util.List;

/**
 * Created by olexandr on 26.03.17.
 */
public class AirlineJdbcDao implements AirlineDao {
    ConnectionManager cm;
    JdbcHelper helper;

    public AirlineJdbcDao() {
        this.cm = ConnectionManager.getInstance();
        helper = new JdbcHelper(cm);
    }

    @Override
    public Long add(Airline airline) {
        return helper.insert("INSERT INTO airline " +
                        "(company_name,img_path)" +
                        "VALUES(?,?)",
                airline.getCompanyName(),
                airline.getImgPath().toString());
    }

    @Override
    public Airline find(Long id) {
        return helper.findObject("SELECT * FROM airline " +
                "WHERE id=?", AirlineMapper::map, id);
    }

    @Override
    public void update(Airline airline) {
        helper.update("UPDATE airline " +
                        "SET company_name = ?," +
                        "img_path = ? " +
                        "WHERE id = ?",
                airline.getCompanyName(),
                airline.getImgPath(),
                airline.getCompanyId());

    }

    @Override
    public void delete(Long id) {
        helper.remove("DELETE FROM airline " +
                "WHERE id=?", id);
    }

    @Override
    public List<Airline> findAll() {
        return helper.findObjects("SELECT * FROM airline "
                , AirlineMapper::map);

    }
}
