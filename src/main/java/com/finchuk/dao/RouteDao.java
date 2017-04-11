package com.finchuk.dao;

import com.finchuk.entities.Route;

/**
 * Created by olexandr on 25.03.17.
 */
public interface RouteDao extends Dao<Route, Long> {

    Route findWhole(Route route);
}
