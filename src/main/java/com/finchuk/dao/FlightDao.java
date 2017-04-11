package com.finchuk.dao;

import com.finchuk.entities.Flight;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by olexandr on 25.03.17.
 */
public interface FlightDao extends Dao<Flight, Long> {

    List<Flight> getFlightsByParams(String townFrom, String townTo, LocalDateTime date);
}
