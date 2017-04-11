INSERT INTO airline
VALUES (101,
        'Turkish Airlines',
        '/resources/images/airlines_pic/logo3.jpg')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO airline
VALUES (102,
        'FinAir',
        '/resources/images/airlines_pic/logo2.jpg')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO airline
VALUES (103,
        'Pedash Sky',
        '/resources/images/airlines_pic/logo4.png')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO airline
VALUES (104,
        'Leleka Fly',
        '/resources/images/airlines_pic/logo1.jpg')
ON DUPLICATE KEY UPDATE id = id;



INSERT INTO airport
VALUES ('uaBr1',
        'Ukraine',
        'Borispil')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO airport
VALUES ('uaZm1',
        'Ukraine',
        'Zmerynka')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO airport
VALUES ('usNY1',
        'USA',
        'New York')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO airport
VALUES ('usCh1',
        'USA',
        'Chicago')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO airport
VALUES ('frPr1',
        'France',
        'Paris')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO airport
VALUES ('geBr1',
        'Germany',
        'Berlin')
ON DUPLICATE KEY UPDATE id = id;



INSERT INTO route
VALUES (101,
        '2:20',
        'Boeing 747',
        'uaBr1',
        'geBr1',
        101)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO route
VALUES (102,
        '3:15',
        'Dream',
        'uaBr1',
        'frPr1',
        103)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO route
VALUES (103,
        '2:45',
        'Boeing 747',
        'uaBr1',
        'frPr1',
        102)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO route
VALUES (104,
        '5:45',
        'Boeing 747',
        'uaBr1',
        'usCh1',
        102)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO route
VALUES (105,
        '3:55',
        'Boeing 743',
        'uaBr1',
        'usCh1',
        101)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO route
VALUES (106,
        '4:25',
        'Kukuruznik',
        'uaZm1',
        'usNY1',
        104)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO route
VALUES (107,
        '4:27',
        'Boeing 575',
        'uaBr1',
        'usNY1',
        103)
ON DUPLICATE KEY UPDATE id = id;




INSERT INTO user 
VALUES (101,
        'User',
        'Puser',
        'user1@gmail.com',
        '5gbjiw2MGbJM8O44CBgxYup81j/3kS27IrXoAyhrREY=',
        1)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO user 
VALUES (102,
        'User',
        'Tuser',
        'user2@gmail.com',
        '5gbjiw2MGbJM8O44CBgxYup81j/3kS27IrXoAyhrREY=',
        1)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO user 
VALUES (103,
        'User',
        'Fuzer',
        'user3@gmail.com',
        '5gbjiw2MGbJM8O44CBgxYup81j/3kS27IrXoAyhrREY=',
        1)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO user 
VALUES (104,
        'Admin',
        'Kamin',
        'admin@gmail.com',
        'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=',
        2)
ON DUPLICATE KEY UPDATE id = id;


INSERT INTO flight
VALUES (101,
        '2017-05-05T4:27',
        101,
        102.5,
        204.6)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO flight
VALUES (102,
        '2017-05-05T5:27',
        102,
        102.5,
        204.6)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO flight
VALUES (103,
        '2017-05-05T17:27',
        102,
        105.5,
        207.6)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO flight
VALUES (104,
        '2017-05-05T17:27',
        103,
        102.5,
        204.6)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO flight
VALUES (105,
        '2017-05-05T17:27',
        104,
        102.5,
        204.6)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO flight
VALUES (106,
        '2017-05-05T17:27',
        105,
        102.5,
        204.6)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO flight
VALUES (107,
        '2017-05-05T17:27',
        106,
        102.5,
        204.6)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO flight
VALUES (108,
        '2017-05-05T17:27',
        107,
        102.5,
        204.6)
ON DUPLICATE KEY UPDATE id = id;
