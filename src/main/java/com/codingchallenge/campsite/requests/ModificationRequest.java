package com.codingchallenge.campsite.requests;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.codingchallenge.campsite.model.Person;
import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.rest.exceptions.IllegalRequestException;

public class ModificationRequest {

	private String reservationId;

	private String newArrival;

	private String newDeparture;

	public String getReservationId() {
		return reservationId;
	}

	public String getNewArrival() {
		return newArrival;
	}

	public String getNewDeparture() {
		return newDeparture;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public void setNewArrival(String newArrival) {
		this.newArrival = newArrival;
	}

	public void setNewDeparture(String newDeparture) {
		this.newDeparture = newDeparture;
	}

	public boolean isComplete() {

		return (reservationId != null && newArrival != null && newDeparture != null);

	}

	public Reservation parseFromReservationRequest() throws DateTimeParseException, IllegalRequestException {
		LocalDate arrival = LocalDate.parse(this.newArrival);
		LocalDate departure = LocalDate.parse(this.newDeparture);
		UUID id;

		if (arrival.compareTo(departure) >= 0) {
			throw new IllegalRequestException("Departure can't be on the same day as arrival or before arrival date");
		}

		if (arrival.datesUntil(departure).collect(Collectors.toList()).size() > 3) {
			throw new IllegalRequestException("Total duration exceed max allowed of 3 days");
		}

		if (arrival.compareTo(LocalDate.now().plusMonths(1)) > 0) {
			throw new IllegalRequestException("Reservation can't be booked over one month in advance");
		}

		if (arrival.compareTo(LocalDate.now()) < 1) {
			throw new IllegalRequestException("Reservation have to be booked at least one day in advance");
		}

		try {
			id = UUID.fromString(reservationId);
		} catch (Exception ex) {
			throw new IllegalRequestException(ex.getMessage());
		}

		return new Reservation(id, arrival, departure);
	}

}
