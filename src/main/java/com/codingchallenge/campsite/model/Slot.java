package com.codingchallenge.campsite.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Slot {

	
	@Id
	private long id;
	
	private LocalDate date;
	
	@ManyToOne
	private Reservation reservation;
	
	protected Slot() {
		
	}
	
	public Slot(long id, LocalDate date,  Reservation reservation) {
		this.id = id;
		this.date = date;
		this.reservation = reservation;
		
	}

	public long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	public String toString() {
		return "Slot [id=" + id + ", date=" + date + ", reservation=" + reservation + "]";
	}
	
	
	
	

}
