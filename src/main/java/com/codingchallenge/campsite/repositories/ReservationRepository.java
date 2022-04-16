package com.codingchallenge.campsite.repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.codingchallenge.campsite.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

	Reservation findById(UUID id);

	@Query(value = "SELECT r from Reservation r WHERE r.departure > :startDate and r.arrival < :endDate AND r.cancelled = false")
	Collection<Reservation> findReservationsBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	

}