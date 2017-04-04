package com.finchuk.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by olexandr on 25.03.17.
 */
public interface Dao<T, PK extends Serializable> {
    PK add(T t);

    T find(PK id);

    void update(T t);

    void delete(PK id);

    List<T> findAll();
}
