package com.finchuk.controller;

import com.finchuk.security.HTTPMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by olexandr on 31.03.17.
 */
public abstract class Controller{
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    public void execute(RequestService reqService) {
        HTTPMethod method = reqService.getMethod();

        switch (method) {
            case GET:
                get(reqService);
                break;
            case POST:
                post(reqService);
                break;
            case PUT:
                put(reqService);
                break;
            case DELETE:
                delete(reqService);
                break;
            default:
                LOGGER.error("Switch doesn't cover all the enum variants");
        }
    }

    public void get(RequestService reqService) {
    }

    public void post(RequestService reqService) {
    }

    public void delete(RequestService reqService) {
    }

    public void put(RequestService reqService) {
    }

}
