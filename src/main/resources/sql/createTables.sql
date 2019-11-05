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