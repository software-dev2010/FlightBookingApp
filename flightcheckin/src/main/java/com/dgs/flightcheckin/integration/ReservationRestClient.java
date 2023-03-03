package com.dgs.flightcheckin.integration;

import com.dgs.flightcheckin.integration.dto.Reservation;
import com.dgs.flightcheckin.integration.dto.ReservationUpdateRequest;

public interface ReservationRestClient {
	
	public Reservation findReservation(Long id);
	
	public Reservation updateReservation(ReservationUpdateRequest request);

}
