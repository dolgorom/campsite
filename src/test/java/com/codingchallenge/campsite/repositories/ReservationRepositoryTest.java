package com.codingchallenge.campsite.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.repositories.ReservationRepository;


	
	@DataJpaTest
	public class ReservationRepositoryTest {
		
		@Autowired
		private TestEntityManager entityManager;

		@Autowired
		private ReservationRepository reservations;

		@Test
		public void testFindByID() {
			Reservation reservation = new Reservation(LocalDate.parse("2022-10-10"), LocalDate.parse("2022-10-11"));
			entityManager.persist(reservation);

			Reservation findById = reservations.findById(reservation.getId());

			assertEquals(reservation.getId(), findById.getId());
		}
		
		
		@Test
		public void testFindArrivalAndDeparture() {
			Reservation reservation = new Reservation( LocalDate.parse("2022-10-10"), LocalDate.parse("2022-10-13"));
			entityManager.persist(reservation);

			Collection<Reservation> findByLastName = reservations.findReservationsBetween(LocalDate.parse("2022-10-11"), LocalDate.parse("2022-10-13"));

			assertEquals(1, findByLastName.size());
		}
		
		@Test
		public void testFindArrivalAndDepartureForSpot() {
			
			Reservation reservation = new Reservation(LocalDate.parse("2022-10-10"), LocalDate.parse("2022-10-13"));
			entityManager.persist(reservation);

			Collection<Reservation> findByLastName = reservations.findReservationsBetween(LocalDate.parse("2022-10-11"), LocalDate.parse("2022-10-13"));

			assertEquals(1, findByLastName.size());
		}
	}
