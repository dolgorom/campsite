package com.codingchallenge.campsite.repositories;


import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.codingchallenge.campsite.model.Person;
import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.model.Slot;

public interface SlotRepository extends CrudRepository<Slot, Long> {
	
	
	Collection<Slot> findByReservation(Reservation reservation);
	
	
}