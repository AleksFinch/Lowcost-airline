CREATE TABLE IF NOT EXISTS airline (
  id           BIGINT(11)  NOT NULL AUTO_INCREMENT,
  company_name VARCHAR(64) NOT NULL,
  img_path     VARCHAR(512),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS airport (
  id      VARCHAR(20) NOT NULL,
  country VARCHAR(64) NOT NULL,
  town    VARCHAR(64) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS route (
  id              BIGINT(11)  NOT NULL AUTO_INCREMENT,
  flight_duration TIME        NOT NULL,
  plane           VARCHAR(32) NOT NULL,
  airport_from    VARCHAR(20) NOT NULL,
  airport_to      VARCHAR(20) NOT NULL,
  company         BIGINT(11)  NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT route_from_airport
  FOREIGN KEY (airport_from)
  REFERENCES airport (id),
  CONSTRAINT route_to_airport
  FOREIGN KEY (airport_to)
  REFERENCES airport (id),
  CONSTRAINT route_airline
  FOREIGN KEY (company)
  REFERENCES airline (id)

);

CREATE TABLE IF NOT EXISTS flight (
  id                   BIGINT(11)     NOT NULL AUTO_INCREMENT,
  departure_time       DATETIME       NOT NULL,
  route                BIGINT(11)     NOT NULL,
  start_price          DECIMAL(10, 2) NOT NULL,
  start_price_business DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT flight_route
  FOREIGN KEY (route)
  REFERENCES route (id)
);

CREATE TABLE IF NOT EXISTS role (
  id   INT(11)     NOT NULL AUTO_INCREMENT,
  role VARCHAR(32) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user (
  id         BIGINT(11)   NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(64)  NOT NULL,
  last_name  VARCHAR(64)  NOT NULL,
  e_mail     VARCHAR(128) NOT NULL UNIQUE,
  password   VARCHAR(64)  NOT NULL,
  role       INT(11)      NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT user_role
  FOREIGN KEY (role)
  REFERENCES role (id)
);

CREATE TABLE IF NOT EXISTS ticket (
  id           BIGINT(11)     NOT NULL AUTO_INCREMENT,
  place_number INT(11)        NOT NULL,
  price        DECIMAL(10, 2) NOT NULL,
  flight       BIGINT(11)     NOT NULL,
  owner        BIGINT(11),
  status       INT(2)         NOT NULL,
  with_baggage BIT            NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT ticket_flight
  FOREIGN KEY (flight)
  REFERENCES flight (id),
  CONSTRAINT ticket_owner
  FOREIGN KEY (owner)
  REFERENCES user (id)
);

INSERT INTO role (role)
VALUES ('user')
ON DUPLICATE KEY UPDATE role = role;
INSERT INTO role (role)
VALUES ('admin')
ON DUPLICATE KEY UPDATE role = role;