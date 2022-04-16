package com.codingchallenge.campsite.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.codingchallenge.campsite.model.Person;
import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.model.Slot;


	
	@DataJpaTest
	public class SlotRepositoryTest {
		
		@Autowired
		private TestEntityManager entityManager;
		
		@Autowired
		private ReservationRepository reservations;

		@Autowired
		private SlotRepository slots;
		
		@Autowired
		private PersonRepository persons;

		@Test
		public void testFindByID() {
			Person person = new Person("test", "email");
			
			LocalDate startDate = LocalDate.now().plusDays(2);
			LocalDate endDate = LocalDate.now().plusDays(3);
			
			Reservation reservation = new Reservation(startDate, endDate);
			
			entityManager.persist(person);
			entityManager.persist(reservation);
			
			Slot slot1 = new Slot(startDate.toEpochDay(), startDate, reservation);
			
			entityManager.persist(slot1);
			
			Optional<Slot> slot = slots.findById(startDate.toEpochDay());
			
			assertTrue(slot.isPresent());
			
			assertEquals(reservation, slot.get().getReservation());
			
						
		}
		
		
		@Test
		public void testFindByReservation() {
			
			Person person = new Person("test", "email");
			
			LocalDate startDate = LocalDate.now().plusDays(2);
			LocalDate endDate = LocalDate.now().plusDays(3);
			
			Reservation reservation = new Reservation(startDate, endDate);
			
			entityManager.persist(person);
			entityManager.persist(reservation);
			
			Slot slot1 = new Slot(startDate.toEpochDay(), startDate, reservation);
			Slot slot2 = new Slot(endDate.toEpochDay(), endDate, reservation);
			
			entityManager.persist(slot1);
			entityManager.persist(slot2);
			
			Collection<Slot> slot = slots.findByReservation(reservation);
						
			assertEquals(2, slot.size());
		}
	}
