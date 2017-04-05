package com.finchuk.controller;

import com.finchuk.services.AuthService;
import com.finchuk.services.impl.AuthServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class LogoutController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger(LogoutController.class);

    private AuthService authService = AuthServiceImpl.getInstance();

    @Override
    public void post(RequestService reqService) {
        authService.logout(reqService.getRequest());
        reqService.redirect("/login.html");
    }
}
