package com.dgs.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dgs.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
