package creator;


import com.finchuk.dao.*;
import com.finchuk.dao.factory.DaoFactory;
import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dto.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by olexandr on 28.03.17.
 */

public class EntityCreator {
    private static AirlineDao airlineDao;
    private static AirportDao airportDao;
    private static RouteDao routeDao;
    private static FlightDao flightDao;
    private static UserDao userDao;
    private static TicketDao ticketDao;

    static {
        DaoFactory factory = JdbcDaoFactory.getInstance();
        airlineDao = factory.getAirlineDao();
        airportDao = factory.getAirportDao();
        routeDao = factory.getRouteDao();
        flightDao = factory.getFlightDao();
        userDao = factory.getUserDao();
        ticketDao = factory.getTicketDao();
    }

    public static Airline createAirline(){
        Airline airline = new Airline();
        airline.setCompanyName("FinAir");
        airline.setImgPath(URI.create("/res/log.png"));
        return airline;
    }
    public static Airport createAirport(){

        Airport airport = new Airport();
        do {
            String id = UUID.randomUUID().toString().substring(0,15);
            airport.setAirportId(id);
        } while (airportDao.find(airport.getAirportId()) != null);
        airport.setTown("Zmerynka");
        airport.setCountry("Ukr");
        return airport;
    }
    public static Route createRoute(){
        Route route = new Route();
        route.setPlane("Boeing 747");
        route.setFlightDuration(LocalTime.of(10,12));
        List<Airline> airlines = airlineDao.findAll();
        Airline airline;
        if(airlines.size()==0){
            airline = createAirline();
            Long id = airlineDao.add(airline);
            airline.setCompanyId(id);
        }else{
            airline=airlines.get(0);
        }
        route.setCompany(airline);

        List<Airport> airports = airportDao.findAll();
        Airport from;
        if(airports.size()==0){
            from = createAirport();
            airportDao.add(from);
        }else{
            from=airports.get(0);
        }
        route.setAirportFrom(from);
        route.setAirportTo(from);
        return route;
    }
    public static Flight createFlight(){
        Flight flight = new Flight();
        flight.setDepartureTime(LocalDateTime.of(2017,9,17,11,25));
        flight.setStartPrice(new BigDecimal(100.0));
        flight.setStartPriceForBusiness(new BigDecimal(200.0));
        List<Route> routes = routeDao.findAll();
        Route route;
        if(routes.size()==0){
            route = createRoute();
            Long id = routeDao.add(route);
            route.setRouteId(id);
        }else{
            route=routes.get(0);
        }
        flight.setRoute(route);
        return flight;
    }
    public static User createUser(){
        User user = new User();
        user.seteMail(UUID.randomUUID().toString().substring(0,10)+"@gmail.com");
        user.setRole(Role.getRole("user"));
        user.setFirstName("Vasia");
        user.setLastName("Pupkin");
        user.setPassword("djfdjfndjfn");
        return user;
    }
    public static Ticket createTicket(){
        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatus.FREE);
        ticket.setWithBaggage(true);
        ticket.setPrice(BigDecimal.valueOf(250));
        ticket.setPlaceNumber(20);
        List<User> users = userDao.findAll();
        User user;
        if(users.size()==0){
            user = createUser();
            Long id = userDao.add(user);
            user.setUserId(id);
        }else{
            user=users.get(0);
        }
        ticket.setOwner(user);
        List<Flight> flights = flightDao.findAll();
        Flight flight;
        if(users.size()==0){
            flight = createFlight();
            Long id = flightDao.add(flight);
            flight.setFlightId(id);
        }else{
            flight=flights.get(0);
        }
        ticket.setFlight(flight);
        return ticket;
    }
}
