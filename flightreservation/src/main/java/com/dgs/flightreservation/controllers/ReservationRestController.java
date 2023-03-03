package com.dgs.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dgs.flightreservation.dto.ReservationUpdateRequest;
import com.dgs.flightreservation.entities.Reservation;
import com.dgs.flightreservation.repos.ReservationRepository;

// We create the Integration Layer

@RestController
public class ReservationRestController {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRestController.class);  

	@CrossOrigin
	@GetMapping("/reservations/{id}")
	public Reservation findReservation(@PathVariable Long id) {
		LOGGER.info("Inside findReservation() for id: " + id);
		return reservationRepository.findById(id).get();
	}
	
	/*
	  @RequestBody tells Spring that at runtime, this object should be constructed using the content 
	  that comes in the request, which is the JSON in our case. This request when it comes in, Spring
	  will deserialize that JSON into this 
	*/
	
	@CrossOrigin
	@PostMapping("/reservations")
	public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
		LOGGER.info("Inside updateReservation() for " + request);
		/*
		  We are creating this 'request' because we don't want to pass in the entire reservation to do this update.
		  The client need not pass in the entire reservation. Simply he'll use this ReservationUpdateRequest wrapper 
		  and he'll pass in these 3 fields id, checkIn, numberOfBags and he will do the update on the reservation.
		*/
		
		Reservation reservation = reservationRepository.findById(request.getId()).get();  
		reservation.setCheckedIn(request.getCheckedIn()); 
		reservation.setNumberOfBags(request.getNumberOfBags()); 
		LOGGER.info("Saving Reservation");
		return reservationRepository.save(reservation); 
	}

}
