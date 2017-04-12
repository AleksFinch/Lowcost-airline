package com.finchuk.services;

import com.finchuk.dao.Dao;
import com.finchuk.dao.jdbc.transaction.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract CrudService implementation that delegates invocations to the dao
 * @param <T> Entity type parameter
 * @param <PK> Primary key type
 */
public abstract class AbstractEntityService<T, PK extends Serializable> {
    protected Dao<T, PK> dao;


    public boolean add(T t) {
        PK key = dao.add(t);
        setId(t, key);
        return true;
    }

    public T find(PK id) {
        Object[] obj = new Object[1];
        Transaction.doTransaction(() -> {
            T finded = dao.find(id);
            if (finded != null) {
                loadTails(finded);
            }
            obj[0] = finded;
        });
        return (T) obj[0];
    }

    public void update(T t) {
        dao.update(t);
    }

    public void delete(PK id) {
        dao.delete(id);
    }

    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        Transaction.doTransaction(() -> {
            list.addAll(dao.findAll());
            list.forEach(e -> loadTails(e));
        });
        return list;
    }

    protected void loadTails(T obj) {

    }

    protected abstract void setId(T obj, PK id);
}
