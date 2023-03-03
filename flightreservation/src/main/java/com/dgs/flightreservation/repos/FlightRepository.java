package com.dgs.flightreservation.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dgs.flightreservation.entities.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

	/*
	 * At runtime when this method is invoked, Spring Data through Hibernate will execute this query
	 * against the database. @Param will bind from, to and departureDate to query parameters, so from
	 * should be mapped to departureCity, to should be mapped to arrivalCity and departureDate should
	 * be mapped to dateOfDeparture. It'll generate a native SQL internally, it'll execute it, get the 
	 * results, converts it into a list of flights ant it'll return that back to our controller. 
	 */
	
	@Query("from Flight where departureCity=:departureCity and arrivalCity=:arrivalCity and dateOfDeparture=:dateOfDeparture")
	List<Flight> findFlights(@Param("departureCity") String from, @Param("arrivalCity") String to, 
							 @Param("dateOfDeparture") Date departureDate);  

}
