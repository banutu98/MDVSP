CREATE TABLE IF NOT EXISTS trips(
  trip_id INT AUTO_INCREMENT PRIMARY KEY ,
  start_time VARCHAR(5) NOT NULL,
  duration INT NOT NULL
  );

CREATE TABLE IF NOT EXISTS depots(
  depot_id INT AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR(255) NOT NULL,
  capacity INT NOT NULL
  );

CREATE TABLE IF NOT EXISTS locations(
  location_id INT AUTO_INCREMENT PRIMARY KEY ,
  x DOUBLE NOT NULL,
  y DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS associations(
  trip_id INT NOT NULL,
  start_location_id INT NOT NULL,
  end_location_id INT NOT NULL,
  UNIQUE KEY(trip_id, start_location_id, end_location_id),
  CONSTRAINT trip_ref FOREIGN KEY (trip_id) REFERENCES trips(trip_id),
  CONSTRAINT start_location_ref FOREIGN KEY (start_location_id) REFERENCES locations(location_id),
  CONSTRAINT end_location_ref FOREIGN KEY (end_location_id) REFERENCES locations(location_id)

);


CREATE TABLE IF NOT EXISTS drivers(
  driver_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  car_model VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS customers(
  customer_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  trip_id INT NOT NULL,
  CONSTRAINT customer_trip_ref FOREIGN KEY (trip_id) REFERENCES trips(trip_id)
);

CREATE TABLE IF NOT EXISTS drivers_requests(
  driver_id INT NOT NULL,
  customer_id INT NOT NULL,
  CONSTRAINT driver_ref FOREIGN KEY (driver_id) REFERENCES drivers(driver_id),
  CONSTRAINT customer_ref FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE IF NOT EXISTS assignments(
  driver_id INT NOT NULL,
  trip_id INT NOT NULL,
  UNIQUE KEY(driver_id, trip_id)
);

-- @DELIMITER $$
CREATE TRIGGER before_assignment_insert BEFORE INSERT ON assignments FOR EACH ROW
BEGIN
  DECLARE num INT;
  DECLARE trip_num INT;
  DECLARE driver_num INT;
  SELECT COUNT(*) FROM assignments WHERE trip_id = new.trip_id INTO num;
  SELECT COUNT(*) FROM trips WHERE trip_id = new.trip_id INTO trip_num;
  SELECT COUNT(*) FROM drivers WHERE drivers.driver_id = new.driver_id INTO driver_num;
  IF trip_num != 1 THEN
    set @message_text =  CONCAT('Invalid trip id ', new.trip_id);
    SIGNAL SQLSTATE '45000' set message_text = @message_text;
  end if;
  IF driver_num != 1 THEN
    set @message_text =  CONCAT('Invalid driver id ', new.driver_id);
    SIGNAL SQLSTATE '45000' set message_text = @message_text;
  end if;
  IF num > 0 THEN
    SIGNAL SQLSTATE '45000' set message_text = 'Trip assigned to multiple drivers';
  end if;
END;
$$
-- @DELIMITER ;

INSERT INTO a.trips(duration, start_time) values (30, '10:30');
INSERT INTO a.trips(duration, start_time) values (60, '11:00');
INSERT INTO a.drivers(name, car_model) values ('Gigel', 'Audi');
INSERT INTO a.drivers(name, car_model) values ('Alin', 'Audi');

SELECT * FROM a.trips;
SELECT * FROM a.drivers;
SELECT * FROM a.assignments;