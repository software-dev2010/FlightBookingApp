package com.dgs.flightreservation.services;

import com.dgs.flightreservation.dto.ReservationRequest;
import com.dgs.flightreservation.entities.Reservation;

public interface ReservationService {

	public Reservation bookFlight(ReservationRequest request);
}
