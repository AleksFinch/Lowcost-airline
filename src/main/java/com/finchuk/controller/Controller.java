package com.finchuk.controller;

import com.finchuk.security.HTTPMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class used for convenient request handling
 *
 * It looks like a HttpServlet but has the advantage of centralized
 * dispatching and path parameter handling
 *
 * Every method receives a RequestService instance that contains
 * some convenient wrapper methods
 *
 * @see com.finchuk.dispatcher.MainServletDispatcher
 * @see RequestService
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
