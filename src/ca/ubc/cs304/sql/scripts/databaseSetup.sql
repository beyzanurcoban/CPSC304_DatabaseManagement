CREATE TABLE branch(
                       branch_id integer not null PRIMARY KEY,
                       branch_name varchar2(20) not null,
                       branch_addr varchar2(50),
                       branch_city varchar2(20) not null,
                       branch_phone integer
);

INSERT INTO branch VALUES (1,'ABC', '123 Charming Ave', 'Vancouver', '6041234567');
INSERT INTO branch VALUES (2, 'DEF', '123 Coco Ave', 'Vancouver', '6044567890');

CREATE TABLE driver(
                       DriverID CHAR(30),
                       Name CHAR(30) NOT NULL,
                       LicenseNumber CHAR(30) NOT NULL,
                       SIN CHAR(30) NOT NULL,
                       Email CHAR(50),
                       PhoneNumber CHAR(30),
                       PRIMARY KEY (DriverID)
);

INSERT INTO driver VALUES ('00009301', 'Angus Tian', '2138602', '729386023', 'atian32@gmail.com', '7789235892');
INSERT INTO driver VALUES ('00005511', 'Nord Forstrong', '4275823', '682658306', 'nforstrong@gmail.com', '6042386923');
INSERT INTO driver VALUES ('00001235', 'Liam Martin', '3649260', '639547027', 'liammartin3@gmail.com', '2369993504');
INSERT INTO driver VALUES ('00002293', 'Karen Wilson', '2645732', '148759365', 'kwilson14@gmail.com', '6047393649');
INSERT INTO driver VALUES ('00002323', 'Sandra Cote', '7296471', '247839237', 'sandraco@gmail.com', '2064638658');

CREATE TABLE task_perform(
                             TaskID CHAR(30),
                             Status CHAR(30) NOT NULL,
                             Destination CHAR(50) NOT NULL,
                             CompleteAfter TIMESTAMP,
                             CompleteBefore TIMESTAMP,
                             TaskNote CHAR(200),
                             DistanceTravelled FLOAT(20),
                             CompleteTime TIMESTAMP,
                             ArriveTime TIMESTAMP,
                             DepartTime TIMESTAMP,

                             StartTime TIMESTAMP,
                             DriverID CHAR(30),
                             PRIMARY KEY (TaskID),
                             CONSTRAINT DriverID_task_perform
                                 FOREIGN KEY (DriverID) REFERENCES driver (DriverID)
                                     ON DELETE CASCADE
);

INSERT INTO task_perform VALUES ('00000001', 'COMPLETE', '2349 Wool Street', TIMESTAMP '2022-01-20 14:00:00', TIMESTAMP '2022-01-25 19:00:00',
                                 'Package for Ms. Kim', 23.3, TIMESTAMP '2022-01-23 15:23:00', TIMESTAMP '2022-01-23 15:20:00', TIMESTAMP '2022-01-23 08:30:00',
                                 TIMESTAMP '2022-01-20 14:00:00', '00002323');

INSERT INTO task_perform VALUES ('00000002', 'COMPLETE', '2323 6th Avenue', TIMESTAMP '2022-01-20 09:00:00', TIMESTAMP '2022-01-25 19:00:00',
                                 'Call 7782392385 once arrived', NULL, NULL, NULL, TIMESTAMP '2022-01-23 09:32:00',
                                 TIMESTAMP '2022-01-20 09:00:00', '00001235');

INSERT INTO task_perform VALUES ('00000003', 'CANCELED', '556 Robson Street', TIMESTAMP '2022-02-21 05:00:00', TIMESTAMP '2022-02-25 17:00:00',
                                 NULL, 14.5, NULL, NULL, TIMESTAMP '2022-02-23 13:00:00',
                                 TIMESTAMP '2022-02-21 09:00:00', '00002293');

INSERT INTO task_perform VALUES ('00000004', 'IN-PROGRESS', '4966 Cordova Street', TIMESTAMP '2022-03-03 10:00:00', TIMESTAMP '2022-03-06 21:00:00',
                                 NULL, 7.3, TIMESTAMP '2022-03-03 23:06:10', TIMESTAMP '2022-03-03 23:00:45', TIMESTAMP '2022-03-03 21:00:00',
                                 TIMESTAMP '2022-03-03 11:00:00', '00009301');

INSERT INTO task_perform VALUES ('00000005', 'IN-PROGRESS', '4074 Robson Street', TIMESTAMP '2022-03-01 09:00:00', TIMESTAMP '2022-03-04 16:00:00',
                                 'Leave the order at the door', NULL, TIMESTAMP '2022-03-03 20:10:00', TIMESTAMP '2022-03-03 20:00:34', TIMESTAMP '2022-03-03 16:00:00',
                                 TIMESTAMP '2022-03-01 15:00:00', '00005511');

CREATE TABLE transshiptask_performcontact(
                                             ContactName CHAR(30),
                                             ContactPhone CHAR(30) NOT NULL,
                                             PRIMARY KEY (ContactName)
);

INSERT INTO transshiptask_performcontact VALUES ('Jeff Miller', '2363952749');
INSERT INTO transshiptask_performcontact VALUES ('Julie Wong', '6042740265');
INSERT INTO transshiptask_performcontact VALUES ('Doug King', '6048265026');
INSERT INTO transshiptask_performcontact VALUES ('Sarah Li', '2063584755');
INSERT INTO transshiptask_performcontact VALUES ('Pat Murray', '7784671233');

CREATE TABLE transshiptask_perform(
                                      TaskID CHAR(30),
                                      ContactName CHAR(30),
                                      DistanceTraveled FLOAT(20),
                                      StartTime TIMESTAMP,
                                      DepartTime TIMESTAMP,
                                      ArriveTime TIMESTAMP,
                                      CompleteTime TIMESTAMP,
                                      DriverID CHAR(30),
                                      PRIMARY KEY (TaskID),
                                      CONSTRAINT DriverID_transshiptask_perform
                                          FOREIGN KEY (DriverID) REFERENCES driver (DriverID)
                                              ON DELETE CASCADE,
                                      CONSTRAINT ContactName_transshiptask_perform
                                          FOREIGN KEY (ContactName) REFERENCES transshiptask_performcontact (ContactName)
                                              ON DELETE CASCADE
);

INSERT INTO transshiptask_perform VALUES ('00000006', 'Jeff Miller', 12.4, NULL, NULL, NULL, NULL, '00009301');
INSERT INTO transshiptask_perform VALUES ('00000007', 'Julie Wong', 34.0, TIMESTAMP '2022-01-20 09:00:00', TIMESTAMP '2022-01-20 11:00:00', TIMESTAMP '2022-01-20 11:57:00', TIMESTAMP '2022-01-20 12:00:00', '00005511');
INSERT INTO transshiptask_perform VALUES ('00000008', 'Doug King', 13.5, TIMESTAMP '2022-02-28 07:00:00', NULL, NULL, TIMESTAMP '2022-02-28 23:37:00', '00001235');
INSERT INTO transshiptask_perform VALUES ('00000009', 'Sarah Li', 22.2, TIMESTAMP '2022-03-04 08:00:00', TIMESTAMP '2022-03-05 09:00:00', TIMESTAMP '2022-03-05 12:00:00', TIMESTAMP '2022-03-20 12:05:00', '00002293');
INSERT INTO transshiptask_perform VALUES ('00000010', 'Pat Murray', 17.0, TIMESTAMP '2022-03-01 12:00:00', TIMESTAMP '2022-03-02 15:00:00', TIMESTAMP '2022-03-02 17:20:00', TIMESTAMP '2022-03-02 17:32:00', '00002323');

CREATE TABLE pickuptask_performmerchant(
                                           MerchantName CHAR(30),
                                           MerchantPhone CHAR(30) NOT NULL,
                                           PRIMARY KEY (MerchantName)
);

INSERT INTO pickuptask_performmerchant VALUES ('Larry Richard', '7783640271');
INSERT INTO pickuptask_performmerchant VALUES ('Bob Clarke', '7783621110');
INSERT INTO pickuptask_performmerchant VALUES ('Debbie Wright', '6045553829');
INSERT INTO pickuptask_performmerchant VALUES ('Joseph Davis', '6041113758');
INSERT INTO pickuptask_performmerchant VALUES ('Stephen Kelly', '6047281616');

CREATE TABLE pickuptask_perform(
                                   TaskID CHAR(30),
                                   MerchantName CHAR(30),
                                   DistanceTravelled FLOAT(20),
                                   StartTime TIMESTAMP,
                                   DepartTime TIMESTAMP,
                                   ArriveTime TIMESTAMP,
                                   CompleteTime TIMESTAMP,
                                   DriverID CHAR(30),
                                   PRIMARY KEY (TaskID),
                                   CONSTRAINT DriverID_pickuptask_perform
                                       FOREIGN KEY (DriverID) REFERENCES driver (DriverID)
                                           ON DELETE CASCADE,
                                   CONSTRAINT MerchantName_pickuptask_perform
                                       FOREIGN KEY (MerchantName) REFERENCES pickuptask_performmerchant (MerchantName)
                                           ON DELETE CASCADE
);

INSERT INTO pickuptask_perform VALUES ('00004628', 'Larry Richard', 12.4, TIMESTAMP '2022-02-28 07:00:00', TIMESTAMP '2022-03-01 09:00:00', TIMESTAMP '2022-03-02 06:00:00', TIMESTAMP '2022-03-02 09:00:00', '00009301');
INSERT INTO pickuptask_perform VALUES ('00005298', 'Debbie Wright', 43.2, TIMESTAMP '2022-03-01 09:00:00', NULL, NULL, TIMESTAMP '2022-03-01 22:00:00', '00005511');
INSERT INTO pickuptask_perform VALUES ('00002371', 'Bob Clarke', NULL, TIMESTAMP '2022-01-01 22:00:00', TIMESTAMP '2022-03-02 09:00:00', TIMESTAMP '2022-03-02 13:00:00', TIMESTAMP '2022-03-02 13:11:00', '00001235');
INSERT INTO pickuptask_perform VALUES ('00001111', 'Joseph Davis', 32.1, TIMESTAMP '2022-01-20 10:00:00', TIMESTAMP '2022-01-20 14:00:00', TIMESTAMP '2022-01-20 18:00:00', TIMESTAMP '2022-01-20 19:00:00', '00002293');
INSERT INTO pickuptask_perform VALUES ('00004728', 'Stephen Kelly', 22.0, NULL, NULL, NULL, NULL, '00002323');

CREATE TABLE deliverytask_performrecipient(
                                              RecipientName CHAR(30),
                                              RecipientPhone CHAR(30) NOT NULL,
                                              PRIMARY KEY (RecipientName)
);

INSERT INTO deliverytask_performrecipient VALUES ('Alex Caron', '2362338288');
INSERT INTO deliverytask_performrecipient VALUES ('Ron Lewis', '2363437264');
INSERT INTO deliverytask_performrecipient VALUES ('Michael Scott', '2364839471');
INSERT INTO deliverytask_performrecipient VALUES ('Ryan Hall', '7781030472');
INSERT INTO deliverytask_performrecipient VALUES ('Ian Wood', '7782647244');

CREATE TABLE deliverytask_perform(
                                     TaskID CHAR(30),
                                     RecipientName CHAR(30),
                                     DistanceTravelled FLOAT(20),
                                     StartTime TIMESTAMP,
                                     DepartTime TIMESTAMP,
                                     ArriveTime TIMESTAMP,
                                     CompleteTime TIMESTAMP,
                                     DriverID CHAR(30),
                                     PRIMARY KEY (TaskID),
                                     CONSTRAINT DriverID_deliverytask_perform
                                         FOREIGN KEY (DriverID) REFERENCES driver (DriverID)
                                             ON DELETE CASCADE,
                                     CONSTRAINT RecipientName_deliverytask_perform
                                         FOREIGN KEY (RecipientName) REFERENCES deliverytask_performrecipient (RecipientName)
                                             ON DELETE CASCADE
);

INSERT INTO deliverytask_perform VALUES ('00003748', 'Michael Scott', 16.6, TIMESTAMP '2022-02-28 07:00:00', NULL, NULL, TIMESTAMP '2022-03-02 17:00:00', '00009301');
INSERT INTO deliverytask_perform VALUES ('00002838', 'Ryan Hall', 10.0, TIMESTAMP '2022-02-08 09:00:00', TIMESTAMP '2022-02-08 11:00:00', TIMESTAMP '2022-02-08 14:00:00', TIMESTAMP '2022-02-08 14:12:00', '00005511');
INSERT INTO deliverytask_perform VALUES ('00007854', 'Ian Wood', 20.0, TIMESTAMP '2022-03-11 08:00:00', TIMESTAMP '2022-03-12 08:00:00', TIMESTAMP '2022-03-12 18:00:00', TIMESTAMP '2022-03-12 18:15:00', '00002293');
INSERT INTO deliverytask_perform VALUES ('00001149', 'Alex Caron', NULL, NULL, NULL, NULL, NULL, '00001235');
INSERT INTO deliverytask_perform VALUES ('00009998', 'Ron Lewis', NULL, TIMESTAMP '2022-03-03 18:15:00', TIMESTAMP '2022-03-04 18:15:00', TIMESTAMP '2022-03-04 21:15:00', TIMESTAMP '2022-03-03 21:30:00', '00002323');

CREATE TABLE vehicle(
                        VehicleID CHAR(30),
                        Color CHAR(30),
                        Type CHAR(30),
                        LicensePlate CHAR(30) NOT NULL,
                        Description CHAR(50),
                        CargoCapacity CHAR(30),
                        PRIMARY KEY (VehicleID)
);

INSERT INTO vehicle VALUES ('00203612', 'Red', 'Sedan', 'Y023C2', NULL, '5');
INSERT INTO vehicle VALUES ('02335342', 'Blue', 'Sedan', 'KW33C2', 'Honda Civic', '5');
INSERT INTO vehicle VALUES ('00002302', 'Black', 'Hatchback', '9CKA23', NULL, '10');
INSERT INTO vehicle VALUES ('01203000', 'Black', 'Sedan', 'PY0C82', NULL, '8');
INSERT INTO vehicle VALUES ('00062352', 'White', 'Convertible', 'H90212', NULL, '10');

CREATE TABLE merchanthours(
                              OpeningHour CHAR(30),
                              ClosingHour CHAR(30),
                              OpeningDuration CHAR(30) NOT NULL,
                              PRIMARY KEY (OpeningHour, ClosingHour)
);

INSERT INTO merchanthours VALUES ('09:00', '18:00', '09:00');
INSERT INTO merchanthours VALUES ('08:30', '18:00', '09:30');
INSERT INTO merchanthours VALUES ('07:00', '15:00', '08:00');
INSERT INTO merchanthours VALUES ('08:00', '16:30', '08:30');
INSERT INTO merchanthours VALUES ('10:00', '17:00', '07:00');

CREATE TABLE merchantinfo(
                             CompanyName CHAR(50),
                             MerchantType CHAR(50) NOT NULL,
                             OpeningHour CHAR(30) NOT NULL,
                             ClosingHour CHAR(30) NOT NULL,
                             EmailAddress CHAR(50),
                             PhoneNumber CHAR(30),
                             PRIMARY KEY (CompanyName),
                             CONSTRAINT OpeningClosingHour_merchantinfo
                                 FOREIGN KEY (OpeningHour, ClosingHour) REFERENCES merchanthours (OpeningHour, ClosingHour)
                                     ON DELETE CASCADE
);

INSERT INTO merchantinfo VALUES ('Save On Food', 'Super Market', '09:00', '18:00', 'saveonfood@gmail.com', '2364748282');
INSERT INTO merchantinfo VALUES ('Sport Check', 'Sports Goods', '08:30', '18:00', NULL, '2364839244');
INSERT INTO merchantinfo VALUES ('Blue Chip', 'Restaurant', '07:00', '15:00', 'bluechip@gmail.com', '6043334422');
INSERT INTO merchantinfo VALUES ('Uniqlo', 'Clothing Goods', '08:00', '16:30', 'uniqlo@gmail.com', '6042828282');
INSERT INTO merchantinfo VALUES ('Glitch', 'Clothing Goods', '10:00', '17:00', NULL, '7783646281');

CREATE TABLE merchant(
                         MerchantID CHAR(30),
                         CompanyName CHAR(50) NOT NULL,
                         SignUpDate DATE,
                         PRIMARY KEY (MerchantID),
                         CONSTRAINT CompanyName_merchant
                             FOREIGN KEY (CompanyName) REFERENCES merchantinfo (CompanyName)
                                 ON DELETE CASCADE
);

INSERT INTO merchant VALUES ('00047294', 'Uniqlo', TO_DATE ('2019-04-12', 'YYYY-MM-DD'));
INSERT INTO merchant VALUES ('00018481', 'Glitch', TO_DATE ('2022-01-01', 'YYYY-MM-DD'));
INSERT INTO merchant VALUES ('00048281', 'Sport Check', TO_DATE ('2017-08-15', 'YYYY-MM-DD'));
INSERT INTO merchant VALUES ('00093839', 'Save On Food', TO_DATE ('2020-06-29', 'YYYY-MM-DD'));
INSERT INTO merchant VALUES ('00071717', 'Blue Chip', TO_DATE ('2020-01-11', 'YYYY-MM-DD'));

CREATE TABLE customerlocation(
                                 LocationID CHAR(30),
                                 BuzzCode CHAR(30),
                                 PropertyType CHAR(30),
                                 PRIMARY KEY (LocationID)
);

INSERT INTO customerlocation VALUES ('00042312', '5923', 'APARTMENT');
INSERT INTO customerlocation VALUES ('00125342', NULL, 'TOWN HOUSE');
INSERT INTO customerlocation VALUES ('00012352', NULL, 'HOUSE');
INSERT INTO customerlocation VALUES ('00043523', NULL, 'HOUSE');
INSERT INTO customerlocation VALUES ('00021351', '2193', 'APARTMENT');

CREATE TABLE customer(
                         CustomerID CHAR(30),
                         Name CHAR(30) NOT NULL,
                         PhoneNumber CHAR(30) NOT NULL,
                         LocationID CHAR(30) NOT NULL,
                         SignUpDate DATE,
                         Note CHAR(200),
                         PRIMARY KEY (CustomerID),
                         CONSTRAINT LocationID_customer
                             FOREIGN KEY (LocationID) REFERENCES customerlocation (LocationID)
                                 ON DELETE CASCADE
);

INSERT INTO customer VALUES ('00004252', 'Pam Beesly', '6047372952', '00042312', TO_DATE ('2019-01-10', 'YYYY-MM-DD'), NULL);
INSERT INTO customer VALUES ('00004111', 'Phil Dunphy', '6042848204', '00125342', TO_DATE ('2021-03-11', 'YYYY-MM-DD'), NULL);
INSERT INTO customer VALUES ('00005255', 'Jake Peralta', '2367718492', '00012352', TO_DATE ('2020-08-15', 'YYYY-MM-DD'), 'Buzz 1055');
INSERT INTO customer VALUES ('00004525', 'Jim Halpert', '2369990011', '00043523', TO_DATE ('2019-01-10', 'YYYY-MM-DD'), NULL);
INSERT INTO customer VALUES ('00004224', 'Doug Judy', '7782828281', '00021351', TO_DATE ('2022-06-29', 'YYYY-MM-DD'), NULL);

CREATE TABLE orders
(
    OrderID CHAR(30),
    Status CHAR(30),
    OrderPrice DECIMAL(18,2) NOT NULL,
    DeliveryDate TIMESTAMP,
    PickUpDate TIMESTAMP,
    CreationTime TIMESTAMP,
    CompletionTime TIMESTAMP,
    PaymentMethod CHAR(30),
    PickUpAddress CHAR(50),
    DeliveryAddress CHAR(50) NOT NULL,
    CustomerID CHAR(30),
    MerchantID CHAR(30),
    PRIMARY KEY (OrderID),
    CONSTRAINT MerchantID_orders
        FOREIGN KEY (MerchantID) REFERENCES merchant (MerchantID)
            ON DELETE CASCADE,
    CONSTRAINT CustomerID_orders
        FOREIGN KEY (CustomerID) REFERENCES customer (CustomerID)
            ON DELETE CASCADE
);

INSERT INTO orders VALUES ('0000009', 'COMPLETED', 9.99, TIMESTAMP '2022-03-12 07:00:00',
                           TIMESTAMP '2022-03-12 07:00:00', TIMESTAMP '2022-03-12 10:00:00',
                           NULL, 'Debit', '1234 Main Mall', '5440 Kingsway', '00004252', '00048281');
INSERT INTO orders VALUES ('0000010', 'IN-PROGRESS', 9.99, TIMESTAMP '2022-03-12 08:00:00',
                           TIMESTAMP '2022-03-12 09:00:00', TIMESTAMP '2022-03-12 08:00:00',
                           NULL, 'Cash', '4566 No.3 Rd', '5440 Kingsway', '00004252', '00093839');
INSERT INTO orders VALUES ('0000011', 'COMPLETED', 9.99, TIMESTAMP '2022-03-12 10:00:00',
                           TIMESTAMP '2022-03-12 17:00:00', TIMESTAMP '2022-03-12 10:00:00',
                           NULL, 'Credit', '1234 Main Mall', '4566 No.3 Rd', '00004224', '00093839');
INSERT INTO orders VALUES ('0000012', 'PENDING', 6.99, TIMESTAMP '2022-03-12 08:00:00',
                           TIMESTAMP '2022-03-12 19:00:00', TIMESTAMP '2022-03-12 07:00:00',
                           NULL, 'Debit', '1234 Main Mall', '4566 No.3 Rd', '00005255', '00047294');
INSERT INTO orders VALUES ('0000013', 'COMPLETED', 6.99, TIMESTAMP '2022-03-12 09:50:00',
                           TIMESTAMP '2022-03-12 17:00:00', TIMESTAMP '2022-03-12 09:50:00',
                           NULL, 'Debit', '5440 Kingsway', '1234 Main Mall', '00005255', '00047294');

CREATE TABLE item(
                     ItemID CHAR(30),
                     Name CHAR(50),
                     Description CHAR(50),
                     Price DECIMAL(18,2) NOT NULL,
                     Type CHAR(30),
                     Weight DECIMAL(18,2),
                     ItemSize CHAR(20),
                     OrderID CHAR(30) NOT NULL,
                     MerchantID CHAR(30),
                     PRIMARY KEY (ItemID),
                     CONSTRAINT MerchantID_item
                         FOREIGN KEY (MerchantID) REFERENCES merchant (MerchantID)
                             ON DELETE CASCADE,
                     CONSTRAINT OrderID_item
                         FOREIGN KEY (OrderID) REFERENCES orders (OrderID)
                             ON DELETE CASCADE
);

INSERT INTO item VALUES ('0000009', 'Apple', 'Fruit', 1.26, 'Fruit', 1.5, 'Small', '0000009', '00048281');
INSERT INTO item VALUES ('0000013', 'Pineapple', 'Fruit', 7.27, 'Fruit', NULL, 'Small', '0000010', '00093839');
INSERT INTO item VALUES ('0000012', 'Computer', 'Electronic', 1026, 'Electronic', NULL, 'Medium', '0000012', '00047294');
INSERT INTO item VALUES ('0000015', 'Orange', 'Fruit', 1.50, 'Fruit', 1.5, 'Small', '0000013', '00047294');
INSERT INTO item VALUES ('0000018', 'Apple', 'Electronic', 1229, 'Electronic', 2.24, 'Large', '0000011', '00093839');


CREATE TABLE transfers(
                          TaskID CHAR(30),
                          ItemID CHAR(30),
                          PRIMARY KEY (TaskID, ItemID),
                          CONSTRAINT TaskID_transfers
                              FOREIGN KEY (TaskID) REFERENCES task_perform (TaskID)
                                  ON DELETE CASCADE,
                          CONSTRAINT ItemID_transfers
                              FOREIGN KEY (ItemID) REFERENCES item (ItemID)
                                  ON DELETE CASCADE
);

INSERT INTO transfers VALUES ('00000001', '0000009');
INSERT INTO transfers VALUES ('00000002', '0000013');
INSERT INTO transfers VALUES ('00000003', '0000012');
INSERT INTO transfers VALUES ('00000004', '0000015');
INSERT INTO transfers VALUES ('00000005', '0000018');

CREATE TABLE city(
                     City CHAR(30),
                     Province CHAR(30),
                     Longitude CHAR(30),
                     Latitude CHAR(30),
                     Country CHAR(30) NOT NULL,
                     PRIMARY KEY (City, Province)
);

INSERT INTO city VALUES ('Vancouver', 'British Columbia', '49.123', '123.238', 'Canada');
INSERT INTO city VALUES ('Burnaby', 'British Columbia', '49.248', '122.981', 'Canada');
INSERT INTO city VALUES ('Surrey', 'British Columbia', '49.129', '123.213', 'Canada');
INSERT INTO city VALUES ('Victoria', 'British Columbia', '48.428', '123.366', 'Canada');
INSERT INTO city VALUES ('Toronto', 'Ontario', '38.238', '192.235', 'Canada');

CREATE TABLE location(
                         LocationID CHAR(30),
                         City CHAR(30) NOT NULL,
                         Province CHAR(30) NOT NULL,
                         StreetName CHAR(30),
                         StreetNumber CHAR(30),
                         UnitNumber CHAR(30),
                         PostalCode CHAR(30) NOT NULL,
                         Note CHAR(200),
                         PRIMARY KEY (LocationID),
                         CONSTRAINT CityProvince_location
                             FOREIGN KEY (City, Province) REFERENCES city (City, Province)
                                 ON DELETE CASCADE
);

INSERT INTO location VALUES ('00000001', 'Vancouver', 'British Columbia', '6th Avenue', '2133', NULL, 'V8Z 0C8', NULL);
INSERT INTO location VALUES ('00000002', 'Vancouver', 'British Columbia', 'Ash Street', '2032', NULL, 'V2K 8G2', 'Leave at front door');
INSERT INTO location VALUES ('00000003', 'Burnaby', 'British Columbia', 'Stolberg Street', '8276', '2', 'V1O 9U8', NULL);
INSERT INTO location VALUES ('00000004', 'Toronto', 'Ontario', 'Freedom Street', '123', '28', 'T82 9T2', NULL);
INSERT INTO location VALUES ('00000005', 'Surrey', 'British Columbia', 'No 3. Road', '4732', '7', 'V2R 9U0', NULL);

CREATE TABLE storagelocation(
                                LocationID CHAR(30),
                                Capacity CHAR(30),
                                PRIMARY KEY (LocationID)
);

INSERT INTO storagelocation VALUES ('00037239', '1000');
INSERT INTO storagelocation VALUES ('00132434', '2000');
INSERT INTO storagelocation VALUES ('00019452', '2500');
INSERT INTO storagelocation VALUES ('00053830', '1000');
INSERT INTO storagelocation VALUES ('00992921', '500');

CREATE TABLE merchantlocation_owns(
                                      LocationID CHAR(30),
                                      Type CHAR(30) NOT NULL,
                                      LandLineNumber CHAR(30),
                                      MerchantID CHAR(30) NOT NULL,
                                      PRIMARY KEY (LocationID),
                                      CONSTRAINT MerchantID_merchantlocation_owns
                                          FOREIGN KEY (MerchantID) REFERENCES merchant (MerchantID)
                                              ON DELETE CASCADE
);

INSERT INTO merchantlocation_owns VALUES ('00023838', 'RESTAURANT', '6043334422', '00047294');
INSERT INTO merchantlocation_owns VALUES ('00111122', 'BOOK STORE', '6043352422', '00018481');
INSERT INTO merchantlocation_owns VALUES ('00055443', 'RESTAURANT', '7784345222', '00048281');
INSERT INTO merchantlocation_owns VALUES ('00093483', 'BIKE STORE', '1800238523', '00093839');
INSERT INTO merchantlocation_owns VALUES ('00776655', 'RESTAURANT', '5125823752', '00071717');

-- For division query
INSERT INTO merchantlocation_owns VALUES ('00000001', 'Super Market', '6048888888', '00093839');
INSERT INTO merchantlocation_owns VALUES ('00000002', 'Super Market', '6048888888', '00093839');
INSERT INTO merchantlocation_owns VALUES ('00000003', 'Super Market', '6048888888', '00093839');
INSERT INTO merchantlocation_owns VALUES ('00000004', 'Super Market', '6048888888', '00093839');
INSERT INTO merchantlocation_owns VALUES ('00000005', 'Super Market', '6048888888', '00093839');

CREATE TABLE liveat(
                       CustomerID CHAR(30),
                       LocationID CHAR(30),
                       PRIMARY KEY(CustomerID, LocationID),
                       CONSTRAINT CustomerID_liveat
                           FOREIGN KEY (CustomerID) REFERENCES customer (CustomerID)
                               ON DELETE CASCADE,
                       CONSTRAINT LocationID_liveat
                           FOREIGN KEY (LocationID) REFERENCES location (LocationID)
                               ON DELETE CASCADE
);

INSERT INTO liveat VALUES ('00004252', '00000003');
INSERT INTO liveat VALUES ('00004111', '00000002');
INSERT INTO liveat VALUES ('00005255', '00000001');
INSERT INTO liveat VALUES ('00004525', '00000004');
INSERT INTO liveat VALUES ('00004224', '00000005');