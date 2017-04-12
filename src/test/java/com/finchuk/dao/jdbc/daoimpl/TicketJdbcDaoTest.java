package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dto.Ticket;
import creator.EntityCreator;

import java.math.BigDecimal;

/**
 * Created by olexandr on 28.03.17.
 */
public class TicketJdbcDaoTest extends AbstractJdbcDaoTest<Ticket, Long> {

    public TicketJdbcDaoTest() {
        dao = JdbcDaoFactory.getInstance().getTicketDao();

    }

    @Override
    protected Long getId(Ticket obj) {
        return obj.getTicketId();
    }

    @Override
    protected void setId(Ticket obj, Long id) {
        obj.setTicketId(id);
    }

    @Override
    protected Ticket getTestObj() {
        return EntityCreator.createTicket();
    }

    @Override
    protected void updateObj(Ticket obj) {
        obj.setPrice(BigDecimal.valueOf(2562));
    }


}