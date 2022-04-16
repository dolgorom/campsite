package com.codingchallenge.campsite.requests;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.codingchallenge.campsite.model.Person;
import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.rest.exceptions.IllegalRequestException;

public class ReservatonRequest {
	
	private String arrival;
	
	private String departure;
	
	private String fullname;
	
	private String email;

	public String getArrival() {
		return arrival;
	}

	public String getDeparture() {
		return departure;
	}

	public String getFullname() {
		return fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public boolean isComplete() {
		
		return (arrival != null && departure != null && fullname != null && email != null);
				
	}
	

	public Reservation parseFromReservationRequest() throws DateTimeParseException, IllegalRequestException {
		LocalDate arrival = LocalDate.parse(this.getArrival());
		LocalDate departure = LocalDate.parse(this.getDeparture());
		
		if (arrival.compareTo(departure) >= 0 ) {
			throw new IllegalRequestException("Departure can't be on the same day as arrival or before arrival date");
		}
		
		if (arrival.datesUntil(departure).collect(Collectors.toList()).size() >3 ) {
			throw new IllegalRequestException("Total duration exceed max allowed of 3 days");
		}
		
		if (arrival.compareTo(LocalDate.now().plusMonths(1)) > 0) {
			throw new IllegalRequestException("Reservation can't be booked over one month in advance");
		}
		
		if (arrival.compareTo(LocalDate.now()) < 1) {
			throw new IllegalRequestException("Reservation have to be booked at least one day in advance");
		}
		
		return new Reservation(arrival, departure);
	}
	
	public Person parsePersonFromReservationRequest() throws IllegalRequestException {
		
		String regexPattern = "^(.+)@(\\S+)$";
		if(!Pattern.compile(regexPattern).matcher(this.getEmail()).matches()) {
			throw new IllegalRequestException("email has to be valid");
		}
		
		return new Person(this.getFullname(), this.getEmail());
	}
	
	

	
}
