package com.finchuk.app;

import com.finchuk.controller.*;
import com.finchuk.controller.admin.AirlinesManagmentController;
import com.finchuk.controller.admin.AirportsManagmentController;
import com.finchuk.controller.admin.FlightsManagmentController;
import com.finchuk.controller.user.*;
import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dispatcher.MainServletDispatcherBuilder;
import com.finchuk.entities.Role;
import com.finchuk.security.SecurityContainer;

import javax.servlet.ServletContext;

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
        container.addConstraint("(/app)?/admin/.*", Role.ADMIN);
        container.addConstraint("(/app)?/user/.*", Role.ADMIN,Role.USER);
    }

    private void initContrrolerMapping(){
        MainServletDispatcherBuilder builder = new MainServletDispatcherBuilder(servletContext);
        builder
        .addPathMap("/", new RedirectController("/index"))
                .addPathMap("/index", new RandomHotTicketController())
                .addPathMap("/login", new LoginController())
                .addPathMap("/logout",new LogoutController())
                .addPathMap("/register", new RegisterController())
                .addPathMap("/find_tickets", new FindingTicketsController())
                .addPathMap("/chosen_flight", new ChosenFlightController())
                .addPathMap("/lang", new LocaleController())
                .addPathMap("/user/buy_ticket", new BuyingTicketController())
                .addPathMap("/user/payment", new PaymentController())
                .addPathMap("/user/personal_tickets", new PersonalTicketsController())
                .addPathMap("/user/unpaid_tickets", new UnpaidTicketsController())
                .addPathMap("/user/personal_cabinet", new PersonalCabinetController())
                .addPathMap("/admin/airport_managing", new AirportsManagmentController())
                .addPathMap("/admin/airline_managing", new AirlinesManagmentController())
                .addPathMap("/admin/flight_managing", new FlightsManagmentController())
                .buildAndRegister("Controller Dispatcher Servlet", "/app/*");
    }
}
