# **Flight Booking Microservices Application**

This project is a Flight Booking System built using Spring Boot Microservices, Spring WebFlux, R2DBC, MySQL, Kafka, Eureka, API Gateway, and Config Server. The system allows users to search flights, book tickets, cancel bookings, and receive real-time booking notifications through Kafka. 
This project follows a fully reactive, non-blocking architecture using WebFlux + R2DBC.

## 1. Project Overview

This application consists of multiple microservices that communicate through REST APIs, Reactive Feign Clients, Kafka, and Eureka Discovery Server.

### User Features

- Search flights (origin, destination, date, trip type)

- View flight details (airline, timings, seats, price)

- Book tickets (multiple passengers allowed)

- Automatic PNR generation

- View ticket using PNR

- View full booking history using email ID

- Cancel ticket (if before journey time)


### Admin Features

- Add airline inventory

- Update seat availability

- Manage flight data

## 2. Microservices in the System

### 1. API Gateway (Port 8765)

- Central entry point for all clients

- Performs routing based on service name

- Integrated with Eureka for dynamic service lookup

### 2. Eureka Service Registry (Port 8761)

- All services register here

- Enables client-side load balancing

- Required for service discovery

### 3. Config Server (Port 8888)

- Fetches configuration from GitHub Repo

- All services load config from here on startup

- Centralized configuration management

### 4. Flight-Service (Port 9001)

Tech Stack: WebFlux + Reactive R2DBC MySQL + Kafka Producer

Handles:

Airline management

Inventory creation

Flight search

Seat availability updates

### 5. Booking-Service (Port 9002)

Tech Stack: WebFlux + Reactive R2DBC MySQL + Reactive Feign + Circuit Breaker + Kafka Producer

Handles:

Ticket booking

Generating PNR

Passenger details

Payment calculation

Cancelling tickets

Fetching flight data using Reactive Feign + Circuit Breaker (Resilience4j)

Publishing booking events to Kafka



## Architecture Diagram
<img width="1732" height="1171" alt="image" src="https://github.com/user-attachments/assets/040e6023-ed48-4d93-87df-b76eea97d640" />
