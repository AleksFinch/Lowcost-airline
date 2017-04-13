package com.finchuk.services;

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

    }

}