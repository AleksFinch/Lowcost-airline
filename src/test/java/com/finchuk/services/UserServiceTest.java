package com.finchuk.services;

import com.finchuk.entities.Ticket;
import com.finchuk.services.impl.TicketService;
import org.junit.BeforeClass;
import org.junit.Test;
import testdb.DBInitializer;

/**
 * Created by olexandr on 29.03.17.
 */
public class UserServiceTest {
    @BeforeClass
    public static void init() {
        DBInitializer.initMySql();
    }

    @Test
    public void find() throws Exception {
        TicketService userService = TicketService.getInstance();
        Ticket user = userService.find(2l);

    }

}