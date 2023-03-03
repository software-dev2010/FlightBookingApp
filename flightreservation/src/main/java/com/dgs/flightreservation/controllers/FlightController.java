package com.dgs.flightreservation.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dgs.flightreservation.entities.Flight;
import com.dgs.flightreservation.repos.FlightRepository;

@Controller
public class FlightController {
	
	@Autowired
	private FlightRepository flightRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);  

	@PostMapping("/findFlights")
	public String findFlights(@RequestParam String from, @RequestParam String to, 
			@RequestParam @DateTimeFormat(pattern="MM-dd-yyyy") Date departureDate, ModelMap modelMap) {
		LOGGER.info("Inside findFlights() From: " + from + " To: " + to + " Departure Date: " + departureDate);
		List<Flight> flights = flightRepository.findFlights(from, to, departureDate); 
		modelMap.addAttribute("flights", flights); 
		LOGGER.info("Flights found are: " + flights);
		return "displayFlights";
	}
	
	@RequestMapping("admin/showAddFlight")
	public String showAddFlight() {
		return "addFlight";
	}
}
