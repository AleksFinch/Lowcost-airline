package com.finchuk.dto;


import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by olexandr on 25.03.17.
 */
public class Route {
    private Long routeId;
    private LocalTime flightDuration;
    private Airport airportFrom;
    private Airport airportTo;
    private Airline company;
    private String plane;

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public LocalTime getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(LocalTime flightDuration) {
        this.flightDuration = flightDuration;
    }

    @Override
    public String toString() {
        return airportFrom + ", " + airportTo + " company: " + company.getCompanyName();
    }

    public Airport getAirportFrom() {
        return airportFrom;
    }

    public void setAirportFrom(Airport airportFrom) {
        this.airportFrom = airportFrom;
    }

    public Airport getAirportTo() {
        return airportTo;
    }

    public void setAirportTo(Airport airportTo) {
        this.airportTo = airportTo;
    }

    public Airline getCompany() {
        return company;
    }

    public void setCompany(Airline company) {
        this.company = company;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(routeId, route.routeId) &&
                Objects.equals(flightDuration, route.flightDuration) &&
                (airportFrom != null && route.airportFrom != null ?
                        Objects.equals(airportFrom.getAirportId(), route.airportFrom.getAirportId()) :
                        airportFrom == route.airportFrom) &&
                (airportTo != null && route.airportTo != null ?
                        Objects.equals(airportTo.getAirportId(), route.airportTo.getAirportId()) :
                        airportTo == route.airportTo) &&
                (company != null && route.company != null ?
                        Objects.equals(company.getCompanyId(), route.company.getCompanyId()) :
                        company == route.company) &&
                Objects.equals(plane, route.plane);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, flightDuration, airportFrom, airportTo, company, plane);
    }
}