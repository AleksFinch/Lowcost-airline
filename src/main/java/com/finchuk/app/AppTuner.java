package com.finchuk.app;

import com.finchuk.controller.LoginController;
import com.finchuk.controller.RandomHotTicketController;
import com.finchuk.controller.RedirectController;
import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dispatcher.MainServletDispatcher;
import com.finchuk.dispatcher.MainServletDispatcherBuilder;
import com.finchuk.entities.Role;
import com.finchuk.security.SecurityContainer;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

/**
 * Created by olexandr on 03.04.17.
 */

public class AppTuner {
    ServletContext servletContext;
    ConnectionManager cm;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    void init(){
        ConnectionManager.initFromJNDI("jdbc/lowcost");
        cm = ConnectionManager.getInstance();
        initSecurity();
        initContrrolerMapping();
    }

    private void initSecurity(){
        SecurityContainer container = SecurityContainer.getInstance();
        container.addConstraint("(/app)?/app/admin/.*", Role.ADMIN);
        container.addConstraint("(/app)?/user/.*", Role.ADMIN,Role.USER);
    }

    private void initContrrolerMapping(){
        MainServletDispatcherBuilder builder = new MainServletDispatcherBuilder(servletContext);
        builder
        .addPathMap("/", new RedirectController("/index"))
                .addPathMap("/index", new RandomHotTicketController())
                .addPathMap("/login", new LoginController())
                .buildAndRegister("Controller Dispatcher Servlet", "/app/*");
    }
}
