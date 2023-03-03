package com.dgs.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dgs.flightreservation.dto.ReservationRequest;
import com.dgs.flightreservation.entities.Flight;
import com.dgs.flightreservation.entities.Passenger;
import com.dgs.flightreservation.entities.Reservation;
import com.dgs.flightreservation.repos.FlightRepository;
import com.dgs.flightreservation.repos.PassengerRepository;
import com.dgs.flightreservation.repos.ReservationRepository;
import com.dgs.flightreservation.util.EmailUtil;
import com.dgs.flightreservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Value("${com.dgs.flightreservation.itinerary.dirpath}")
	private String ITINERARY_DIR;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private PDFGenerator pdfGenerator;
	
	@Autowired
	private EmailUtil emailUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);  

	/*
	 * The reason for we've created a service layer class is because we're making
	 * multiple database calls using multiple repositories and also we'll be
	 * generating the itinerary later on and we'll be emailing that. All that will
	 * happen in the bookFlight(). A lot of business logic.
	 */

	@Override
	public Reservation bookFlight(ReservationRequest request) {

		LOGGER.info("Inside bookFlight()");
		/*
		 * The first step is would be tipically to make a payment. We are not going to
		 * do that. You can retrieve a card number and all that and we can invoke a
		 * payment gateway webservice. Tipically we might land on a payment gateway
		 * which is a third party software and that is where the payment will be
		 * processed if you pass in all these information. But we are skipping that
		 * step. Make payment: request.getCardNumber() And if the payment fails we would
		 * throw an exception from here. If the payment goes on successfully, the next
		 * is to retrieve the flight information first.
		 */

		// Retrieve the flight
		Long flightId = request.getFlightId();
		LOGGER.info("Fetching flight for flight id: " + flightId);
		Flight flight = flightRepository.findById(flightId).get();

		// Save the passenger information
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());
		LOGGER.info("Saving the passenger: " + passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);

		// Create the reservation
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);      // The passenger will be checked in later on, using the checkin app that
		
		LOGGER.info("Saving the reservation: " + reservation);
		Reservation savedReservation = reservationRepository.save(reservation);
		
		String filePath = ITINERARY_DIR + savedReservation.getId() + ".pdf";
		LOGGER.info("Generating the itinerary");
		pdfGenerator.generateItinerary(savedReservation, filePath); 
		LOGGER.info("Emailing the itinerary");
		emailUtil.sendItinerary(passenger.getEmail(), filePath); 

		return savedReservation;
	}

}
