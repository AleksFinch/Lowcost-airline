package com.finchuk.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Prevent handling static resources by
 * {@link com.finchuk.dispatcher.MainServletDispatcher }
 */
public class StaticResourceFilter implements Filter {
    private String resourcePath = "/resources/";

    private String pagePath = "/pages/";

    private String forwardPath = "/app";

    private List<String> ignoreList = new ArrayList<>();

    public StaticResourceFilter() {
    }

    public StaticResourceFilter(String resourcePath, String pagePath, String forwardPath) {
        this.resourcePath = resourcePath;
        this.pagePath = pagePath;
        this.forwardPath = forwardPath;
    }

    public StaticResourceFilter(String forwardPath) {
        this.forwardPath = forwardPath;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void ignore(String startPath) {
        ignoreList.add(startPath);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith(resourcePath) || path.startsWith(pagePath)
                || path.startsWith(forwardPath)
                || ignoreList.stream().anyMatch(path::startsWith)) {
            chain.doFilter(request, response); // Goes to default servlet.
        } else {
            req.getRequestDispatcher(forwardPath + path).forward(request, response); // Goes to controller.
        }
    }

    @Override
    public void destroy() {
    }
}
