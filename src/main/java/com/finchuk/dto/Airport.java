package com.finchuk.dto;

import java.util.Objects;

/**
 * Created by olexandr on 25.03.17.
 */
public class Airport {
    private String airportId;
    private String country;
    private String town;

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airport that = (Airport) o;

        return Objects.equals(airportId, that.airportId) &&
                Objects.equals(town, that.town) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportId, country, town);
    }

    @Override
    public String toString() {
        return airportId + " - " + country + ", " + town;
    }

}
