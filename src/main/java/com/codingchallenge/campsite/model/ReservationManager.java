package com.codingchallenge.campsite.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codingchallenge.campsite.repositories.PersonRepository;
import com.codingchallenge.campsite.repositories.ReservationRepository;
import com.codingchallenge.campsite.rest.exceptions.IllegalRequestException;
import com.codingchallenge.campsite.tools.DateTools;

@Component
public class ReservationManager {
	
	private static final Logger log = LoggerFactory.getLogger(ReservationManager.class);
	
	@Autowired
	private ReservationRepository reservationsRepo;
	
	@Autowired
	private PersonRepository personRepo;
	
	
	public Set<LocalDate> getListOfAvailableDates(LocalDate startDate, LocalDate endDate){
		
		Set<LocalDate> allDates = DateTools.createDatePeriod(startDate, endDate.plusDays(1));
				
		Collection<Reservation> reservations = reservationsRepo.findReservationsBetween(startDate, endDate);	
		
		Iterator<Reservation> iterator = reservations.iterator();
		
		while (iterator.hasNext()) {
			Reservation reservation = iterator.next();
			
			allDates.removeAll(DateTools.createDatePeriod(reservation.getArrival(), reservation.getDeparture()));
		}
										
		return allDates;
	}
	
	private boolean validateReservationDates(LocalDate startDate, LocalDate endDate){
		
		Set<LocalDate> requiredDates = DateTools.createDatePeriod(startDate, endDate);
				
		Set<LocalDate> allDates = getListOfAvailableDates(startDate, endDate);
		
		return allDates.containsAll(requiredDates);
		
	}
	
	public synchronized Reservation  createOrUpdateReservation(Reservation reservation) throws IllegalRequestException{
		
		if (validateReservationDates(reservation.getArrival(), reservation.getDeparture())) {
			
			

			return reservationsRepo.save(reservation);
			
		} else {
			log.warn("Requested dates are not available");
			throw new IllegalRequestException("Reservation for specific dates is not available");
		}
				
		
	}
	
	public synchronized Person createPerson(Person person) throws IllegalRequestException {

		Collection<Person> existingPerson = personRepo.findByFullnameAndEmail(person.getFullname(), person.getEmail());
		if (existingPerson.size() > 0) {
			return existingPerson.iterator().next();
		} else {
			return personRepo.save(person);
		}

	}
	
	

}
