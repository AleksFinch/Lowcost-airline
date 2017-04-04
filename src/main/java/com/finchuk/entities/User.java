package com.finchuk.entities;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by olexandr on 25.03.17.
 */
public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String eMail;
    private String password;
    private Role role;
    private Collection<Ticket> tickets;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(eMail, user.eMail) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                (tickets != null ? (user.tickets != null
                        && user.tickets.size() == tickets.size()
                        && tickets.containsAll(user.tickets))
                        : user.tickets == null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, eMail, password, role);
    }
}
