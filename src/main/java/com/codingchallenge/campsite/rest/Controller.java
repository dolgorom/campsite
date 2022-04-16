package com.codingchallenge.campsite.rest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codingchallenge.campsite.model.Person;
import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.model.ReservationManager;
import com.codingchallenge.campsite.repositories.ReservationRepository;
import com.codingchallenge.campsite.requests.ModificationRequest;
import com.codingchallenge.campsite.requests.ReservatonRequest;
import com.codingchallenge.campsite.rest.exceptions.DateParsingException;
import com.codingchallenge.campsite.rest.exceptions.IllegalDateException;
import com.codingchallenge.campsite.rest.exceptions.IncompleteRequestException;
import com.codingchallenge.campsite.rest.exceptions.ReservationNotFoundException;

@RestController
public class Controller {
	
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	private ReservationRepository reservationsRepo;
	
	@Autowired
	private ReservationManager reservationManager;
	   
	
	@GetMapping("/get-all-reservations")
    public Iterable<Reservation> findAllReservations() {
		return reservationsRepo.findAll();
    }
	

	
	@GetMapping("/available-slots")
    public Set<LocalDate> findAllAvailableSlots() {
		log.debug("findavailableSlots");
		LocalDate startDate = LocalDate.now().plusDays(1);
		LocalDate endDate = startDate.plusMonths(1);
		
		return reservationManager.getListOfAvailableDates(startDate, endDate);
    }
	
	@GetMapping("/available-slots/start/{arrival}/end/{departure}")
	public ResponseEntity<Set<LocalDate>> findAllAvailableSlotsForDates(@PathVariable String arrival,
			@PathVariable String departure) {
		log.info("findavailableSlots");
		
		LocalDate startDate;
		LocalDate endDate;

		try {

			startDate = LocalDate.parse(arrival);
			endDate = LocalDate.parse(departure);
		} catch (java.time.format.DateTimeParseException exc) {
			throw new DateParsingException(exc.getMessage());
		}
		
		if (startDate.compareTo(endDate) > 0) {
			throw new IllegalDateException("start must be at least one day ahead");
		}

		return new ResponseEntity<Set<LocalDate>>(reservationManager.getListOfAvailableDates(startDate, endDate), HttpStatus.OK);
	}
	
	@GetMapping("/cancel-reservation/{id}")
	public ResponseEntity<Reservation> findAllAvailableSlotsForDates(@PathVariable UUID id) {
		log.info("cancel reservation");

		Reservation reservation = reservationsRepo.findById(id);

		if (reservation != null) {
			reservation.setCancelled(true);
			return new ResponseEntity<Reservation>(reservationsRepo.save(reservation), HttpStatus.OK);
		} else {
			throw new ReservationNotFoundException("Reservation not found");
		}

	}
	
	
	@PostMapping("/make-reservation")
	public ResponseEntity<Reservation> makeReservation(@RequestBody ReservatonRequest reservationRequest) {

		log.info("makeReservation called {} ", reservationRequest);

		if (!reservationRequest.isComplete()) {
			throw new IncompleteRequestException(
					"Request must provide arrival date, departure date, email and full name of the person");
		}

		Reservation reservation;
		try {
			reservation = reservationRequest.parseFromReservationRequest();			

		} catch (java.time.format.DateTimeParseException exc) {
			throw new DateParsingException(exc.getMessage());
		}  
		
		Person person;
		try {
			person = reservationRequest.parsePersonFromReservationRequest();	


		} catch (java.time.format.DateTimeParseException exc) {
			throw new DateParsingException(exc.getMessage());
		}  

		person = reservationManager.createPerson(person);
		reservation.setPerson(person);
		
		return new ResponseEntity<Reservation>( reservationManager.createReservation(reservation), HttpStatus.CREATED);
	}
	
	@PostMapping("/modify-reservation")
	public ResponseEntity<Reservation> modifyReservation(@RequestBody ModificationRequest modificationRequest) {

		log.info("makeReservation called {} ", modificationRequest);

		if (!modificationRequest.isComplete()) {
			throw new IncompleteRequestException(
					"Request must provide reservation id and new  arrival date and departure date. Ex. {\"reservationId\":\"2aaa2c46-89bd-4ce8-8a5c-53dd8a90e678\",\"newArrival\":\"2022-04-17\",\"newDeparture\":\"2022-04-18\"}");
		}
		


		Reservation modifiedReservation;
		try {
			modifiedReservation = modificationRequest.parseFromReservationRequest();			

		} catch (java.time.format.DateTimeParseException exc) {
			throw new DateParsingException(exc.getMessage());
		}  
		
		Reservation reservationToBeUpdated = reservationsRepo.findById(modifiedReservation.getId());
		
		if(reservationToBeUpdated == null) {
			throw new ReservationNotFoundException("Reservation not found");
		}
		
		reservationToBeUpdated.setArrival(modifiedReservation.getArrival());
		reservationToBeUpdated.setDeparture(modifiedReservation.getDeparture());

		
		return new ResponseEntity<Reservation>( reservationManager.createReservation(reservationToBeUpdated), HttpStatus.CREATED);
	}
	
	
	
}

