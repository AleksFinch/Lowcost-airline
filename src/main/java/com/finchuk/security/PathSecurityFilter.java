package com.finchuk.security;

import com.finchuk.entities.Role;
import com.finchuk.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Created by olexandr on 30.03.17.
 */
@WebFilter(urlPatterns = "/*")
public class PathSecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = new LoginServletWrapper((HttpServletRequest) servletRequest);
        String requestURI = getNormalURI(httpServletRequest,servletResponse);
        if(requestURI==null){
            return;
        }
        if(!checkPermission(servletRequest,servletResponse,requestURI)){
            return;
        }
        filterChain.doFilter(httpServletRequest,servletResponse);
    }

    private boolean checkPermission(ServletRequest servletRequest, ServletResponse servletResponse,String requestURI) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        User user = (User) httpServletRequest.getSession(true).getAttribute("user");
        HTTPMethod method = HTTPMethod.valueOf(httpServletRequest.getMethod().toUpperCase());
        Role role = user!=null?
                user.getRole():
                null;
        SecurityContainer container = SecurityContainer.getInstance();
        if(!container.isAllowed(requestURI, method, role)){
            if(role==null){
                httpServletRequest
                        .getRequestDispatcher(SecurityContainer.LOGIN_PAGE)
                        .forward(servletRequest,servletResponse);
            }else{
                ((HttpServletResponse) servletResponse).
                        sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            return false;
        }
        return true;
    }

    private String getNormalURI(HttpServletRequest httpServletRequest, ServletResponse servletResponse) throws IOException {
        String requestURI = httpServletRequest.getRequestURI();
        try {
            requestURI = new URI(requestURI).normalize().toString();
            requestURI = requestURI.replaceAll("[/]+","/");
        } catch (URISyntaxException e) {
            ((HttpServletResponse) servletResponse).
                    sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return requestURI;
    }

    @Override
    public void destroy() {

    }
}
