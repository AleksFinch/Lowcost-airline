package com.finchuk.security;

import com.finchuk.dto.Role;
import com.finchuk.dto.User;
import com.finchuk.services.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

/**
 * A wrapper around HttpServletRequest that override methods related to
 * security, authorization and authentication
 *
 * @see SecurityContainer
 * @see PathSecurityFilter
 */
public class LoginServletWrapper extends HttpServletRequestWrapper {
    private static final Logger LOGGER = LogManager.getLogger(LoginServletWrapper.class);
    AuthService service;

    LoginServletWrapper(HttpServletRequest request) {
        super(request);
    }

    public AuthService getService() {
        return service;
    }

    public void setService(AuthService service) {
        this.service = service;
    }

    protected HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) getRequest();
    }

    @Override
    public Principal getUserPrincipal() {
        User user = (User) getHttpRequest().getSession(false).getAttribute("user");
        if (user == null) {
            return null;
        }

        return () -> user.getUserId().toString();
    }

    @Override
    public boolean isUserInRole(String role) {
        User user = getCurrentUser();
        if (user == null) {
            return false;
        }

        if (Role.getRole(role) == user.getRole()) {
            return true;
        }

        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException {
        User user = service.checkLogin(username, password);
        if (user!=null) {
            getHttpRequest().getSession(true).setAttribute("user", user);
            getHttpRequest().getSession(false).setAttribute("loggedIn", true);
        } else {
            getHttpRequest().getSession().invalidate();
            throw new ServletException("Bad login credentials");
        }
    }

    @Override
    public void logout() throws ServletException {
        getSession().invalidate();
    }

    public User getCurrentUser() {
        return (User) getSession(true).getAttribute("user");
    }

}
