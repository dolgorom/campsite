package com.codingchallenge.campsite.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

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
		
		@Test
		public void testFindByDates() {
			
			Person person = new Person("test", "email");
			
			LocalDate startDate = LocalDate.now().plusDays(2);
			LocalDate endDate = LocalDate.now().plusDays(3);
			
			Reservation reservation = new Reservation(startDate, endDate);
			reservation.setPerson(person);
			
			entityManager.persist(person);
			entityManager.persist(reservation);
			
			Slot slot1 = new Slot(startDate.toEpochDay(), startDate, reservation);
			Slot slot2 = new Slot(endDate.toEpochDay(), endDate, reservation);
			
			entityManager.persist(slot1);
			entityManager.persist(slot2);
			
			Collection<Slot> slot = slots.findReservationsBetween(LocalDate.now().plusDays(2),  LocalDate.now().plusDays(3));
						
			assertEquals(2, slot.size());
		}
		
		@Test
		public void testWrittingToDb() {
			
			Person person = new Person("test", "email");
			
			
			LocalDate localDate1 = LocalDate.now().plusDays(1);
			LocalDate localDate2 = LocalDate.now().plusDays(2);
			LocalDate localDate3 = LocalDate.now().plusDays(3);
			
			LocalDate localDate4 = LocalDate.now().plusDays(4);
			LocalDate localDate5 = LocalDate.now().plusDays(5);
			
			Reservation reservation = new Reservation(localDate1, localDate3.plusDays(1));
			reservation.setPerson(person);
			
			entityManager.persist(person);
			entityManager.persist(reservation);
			
			Slot slot1 = new Slot(localDate1.toEpochDay(), localDate1, reservation);
			Slot slot2 = new Slot(localDate2.toEpochDay(), localDate2, reservation);
			Slot slot3 = new Slot(localDate3.toEpochDay(), localDate3, reservation);
									
			entityManager.persist(slot1);
			entityManager.persist(slot2);
			entityManager.persist(slot3);
						
			
			Collection<Slot> slot = slots.findReservationsBetween(LocalDate.now().plusDays(1),  LocalDate.now().plusDays(3));
						
			assertEquals(3, slot.size());
			
			Reservation reservation2 = new Reservation(localDate1, localDate3.plusDays(1));
			reservation2.setPerson(person);
			
			entityManager.persist(reservation2);
			
			Slot newSlot1 = new Slot(localDate3.toEpochDay(), localDate3, reservation2);
			Slot newSlot2 = new Slot(localDate4.toEpochDay(), localDate4, reservation2);
			Slot newSlot3 = new Slot(localDate5.toEpochDay(), localDate5, reservation2);
			
			Set<Slot> newSlots = new HashSet<>();
			newSlots.add(newSlot1);
			newSlots.add(newSlot2);
			newSlots.add(newSlot3);
			
			slots.saveAll(newSlots);
			
			
			
			Iterator<Slot> iter = slots.findAll().iterator();
			
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}
			
			slot = slots.findReservationsBetween(LocalDate.now().plusDays(1),  LocalDate.now().plusDays(5));
			
			assertEquals(3, slot.size());
		}
	}
