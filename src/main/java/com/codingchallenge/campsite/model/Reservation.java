package com.codingchallenge.campsite.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

import com.codingchallenge.campsite.rest.exceptions.IllegalRequestException;

@Entity
public class Reservation {
	
	@Id
	private UUID id;
	
	private boolean cancelled = false;
	
	private LocalDate arrival;

	private LocalDate departure;
	
	@ManyToOne
	private Person person;
	
	
	protected Reservation() {}
	
	
	public Reservation(LocalDate arrival, LocalDate departure) {
		this.arrival = arrival;
		this.departure = departure;
		this.id = UUID.randomUUID();
	}
	
	public Reservation(UUID id, LocalDate arrival, LocalDate departure) {
		this.id = id;
		this.arrival = arrival;
		this.departure = departure;
	}


	public UUID getId() {
		return id;
	}
		
	
	public LocalDate getArrival() {
		return arrival;
	}


	public LocalDate getDeparture() {
		return departure;
	}


	public void setArrival(LocalDate arrival) {
		this.arrival = arrival;
	}


	public void setDeparture(LocalDate departure) {
		this.departure = departure;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	@Override
	public String toString() {
		return "Reservation [id=" + id + ", cancelled=" + cancelled + ", arrival=" + arrival + ", departure="
				+ departure + ", person=" + person + "]";
	}
	
	
}
