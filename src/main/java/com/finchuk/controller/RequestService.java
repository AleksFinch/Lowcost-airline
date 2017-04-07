package com.finchuk.controller;

import com.finchuk.dispatcher.MainServletDispatcher;
import com.finchuk.entities.User;
import com.finchuk.security.HTTPMethod;
import com.finchuk.security.LoginServletWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Created by olexandr on 31.03.17.
 */
public class RequestService {
    private static final Logger LOGGER = LogManager.getLogger(RequestService.class);

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;


    private String redirectPath;
    private String renderPage;

    public RequestService(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    public void setPageAttribute(String key, Object val) {
        httpServletRequest.setAttribute(key, val);
    }
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable((User) httpServletRequest.getSession(true).getAttribute("user"));
    }

    public HTTPMethod getMethod() {
        String formMethod = httpServletRequest.getMethod();
        return HTTPMethod.valueOf(formMethod.toUpperCase());
    }

    public HttpServletRequest getRequest() {
        return httpServletRequest;
    }

    public HttpServletResponse getResponse() {
        return httpServletResponse;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void redirect(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public String getRenderPage() {
        return renderPage;
    }

    public void render(String renderPage) {
        this.renderPage = renderPage;
    }

    public boolean isRedirect() {
        Boolean b = (Boolean) httpServletRequest.getSession().getAttribute(MainServletDispatcher.REDIRECT_KEY);

        return b != null && b;
    }

    public void clearRedirectFlag() {
        httpServletRequest.getSession().setAttribute(MainServletDispatcher.REDIRECT_KEY, null);
    }

    public Optional<String> getParameter(String parameter) {
        String s = httpServletRequest.getParameter(parameter);
        return Optional.ofNullable(s).filter(v -> !v.isEmpty());
    }

    public String getString(String param) {
        return getParameter(param).orElse("");
    }
}
