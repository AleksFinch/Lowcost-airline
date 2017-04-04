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
        SecurityContainer container = SecurityContainer.getInstance();
        HttpServletRequest httpServletRequest = new LoginServletWrapper((HttpServletRequest) servletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        try {
            requestURI = new URI(requestURI).normalize().toString();
            requestURI = requestURI.replaceAll("[/]+","/");
        } catch (URISyntaxException e) {
            ((HttpServletResponse) servletResponse).
                    sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        User user = (User) httpServletRequest.getSession(true).getAttribute("user");
        HTTPMethod method = HTTPMethod.valueOf(httpServletRequest.getMethod().toUpperCase());
        Role role = user!=null?
                user.getRole():
                null;
        if(!container.isAllowed(requestURI, method, role)){
            if(role==null){
                httpServletRequest
                        .getRequestDispatcher(SecurityContainer.LOGIN_PAGE)
                        .forward(servletRequest,servletResponse);
                return;
            }else{
                ((HttpServletResponse) servletResponse).
                        sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
