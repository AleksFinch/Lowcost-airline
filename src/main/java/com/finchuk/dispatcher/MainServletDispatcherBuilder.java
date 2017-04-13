package com.finchuk.dispatcher;

import com.finchuk.controller.Controller;
import com.finchuk.filter.StaticResourceFilter;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * A more user friendly way of creating and registering a {@link MainServletDispatcher}
 */
public class MainServletDispatcherBuilder {

    private ServletContext servletContext;
    MainServletDispatcher dispatcher = new MainServletDispatcher();

    public MainServletDispatcherBuilder(ServletContext sc) {
        this.servletContext = sc;
    }

    public MainServletDispatcherBuilder addPathMap(String path, Controller controller){
        dispatcher.addMapping(path,controller);
        return this;
    }

    public MainServletDispatcher build(){
        return dispatcher;
    }

    public MainServletDispatcher buildAndRegister(String name,String mapping){
        MainServletDispatcher servlet = build();

        ServletRegistration.Dynamic dynamic = servletContext.addServlet(name, servlet);
        dynamic.setMultipartConfig(new MultipartConfigElement(""));
        dynamic.addMapping(mapping);

        StaticResourceFilter filter =
                new StaticResourceFilter(mapping.replace("/*", ""));
        filter.ignore("/image");

        FilterRegistration.Dynamic filterDynamic =
                servletContext.addFilter("Static Resource Filter", filter);
        filterDynamic.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");


        return servlet;
    }
}
