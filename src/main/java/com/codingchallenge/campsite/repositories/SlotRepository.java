package com.codingchallenge.campsite.repositories;


import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.codingchallenge.campsite.model.Person;
import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.model.Slot;

public interface SlotRepository extends CrudRepository<Slot, Long> {
	
	
	Collection<Slot> findByReservation(Reservation reservation);
	
	@Query(value = "SELECT s from Slot s WHERE s.date >= :startDate and s.date <= :endDate")
	Collection<Slot> findReservationsBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	
}