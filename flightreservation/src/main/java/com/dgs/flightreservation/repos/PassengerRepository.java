package com.dgs.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dgs.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
