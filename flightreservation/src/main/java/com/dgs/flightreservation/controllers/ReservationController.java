package com.dgs.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dgs.flightreservation.dto.ReservationRequest;
import com.dgs.flightreservation.entities.Flight;
import com.dgs.flightreservation.entities.Reservation;
import com.dgs.flightreservation.repos.FlightRepository;
import com.dgs.flightreservation.services.ReservationService;

@Controller
public class ReservationController {
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private ReservationService reservationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);  

	@RequestMapping("/showCompleteReservation")
	public String showCompleteReservation(@RequestParam Long flightId, ModelMap modelMap) {
		LOGGER.info("showCompleteReservation() invoked with the Flight Id: " + flightId);
		Flight flight = flightRepository.findById(flightId).get(); 
		modelMap.addAttribute("flight", flight); 
		LOGGER.info("Flight found are: " + flight);
		return "completeReservation";
	}
	
	@PostMapping("/completeReservation")
	public String completeReservation(ReservationRequest request, ModelMap modelMap) {
		LOGGER.info("completeReservation() " + request);
		Reservation reservation = reservationService.bookFlight(request);
		modelMap.addAttribute("msg", "Reservation created successfully and the id is " + reservation.getId());
		return "reservationConfirmation";
	}
}
