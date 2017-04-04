ALTER TABLE ticket
  DROP FOREIGN KEY ticket_flight;
ALTER TABLE ticket
  DROP FOREIGN KEY ticket_owner;
ALTER TABLE route
  DROP FOREIGN KEY route_from_airport;
ALTER TABLE route
  DROP FOREIGN KEY route_to_airport;
ALTER TABLE route
  DROP FOREIGN KEY route_airline;
ALTER TABLE user
  DROP FOREIGN KEY user_role;
ALTER TABLE flight
  DROP FOREIGN KEY flight_route;
DROP TABLE ticket;
DROP TABLE route;
DROP TABLE user;
DROP TABLE flight;
DROP TABLE role;
DROP TABLE airport;
DROP TABLE airline;