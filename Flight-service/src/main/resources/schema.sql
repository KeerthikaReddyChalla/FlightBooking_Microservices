CREATE TABLE airline (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    logo VARCHAR(255)
);

CREATE TABLE inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    airline_id BIGINT NOT NULL,
    from_place VARCHAR(100),
    to_place VARCHAR(100),
    flight_date TIMESTAMP,
    trip_type VARCHAR(20),
    total_seats INT,
    available_seats INT,
    price DOUBLE,
    FOREIGN KEY (airline_id) REFERENCES airline(id)
);
