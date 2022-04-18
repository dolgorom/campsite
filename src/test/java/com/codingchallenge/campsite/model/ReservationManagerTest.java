package com.codingchallenge.campsite.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.codingchallenge.campsite.repositories.ReservationRepository;
import com.codingchallenge.campsite.rest.exceptions.IllegalRequestException;




@SpringBootTest()
@ContextConfiguration
public class ReservationManagerTest {
	
	@Autowired
	private ReservationManager reservationManager;
	
	@Autowired
	private ReservationRepository reservationsRepo;
	
	@Test
	public void testSuccessfullReservationAndFailed() {
		
		assertTrue(null != reservationManager);
		Reservation reservation1 = new Reservation(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
		Reservation reservation2 = new Reservation(LocalDate.now().plusDays(2), LocalDate.now().plusDays(3));
		
		reservationManager.createOrUpdateReservation(reservation1);
		
		//should fail since the dates are taken already
		assertThrows(IllegalRequestException.class, () -> {reservationManager.createOrUpdateReservation(reservation2);});
		
		
	}
	
	@Test
	public void testModification() {
		
		assertTrue(null != reservationManager);
		Reservation reservation1 = new Reservation(LocalDate.now().plusDays(3), LocalDate.now().plusDays(4));
		
		reservationManager.createOrUpdateReservation(reservation1);
		
		reservation1.setArrival(LocalDate.now().plusDays(5));
		reservation1.setDeparture(LocalDate.now().plusDays(6));
		
		Reservation updatedReservation = reservationManager.createOrUpdateReservation(reservation1);
		
		assertEquals(reservation1.getId(), updatedReservation.getId());
		assertEquals(reservation1.getArrival(), updatedReservation.getArrival());
		assertEquals(reservation1.getDeparture(), updatedReservation.getDeparture());
				
		
	}
	
	@Test
	public void testFailedModification() {
		
		assertTrue(null != reservationManager);
		Reservation reservation1 = new Reservation(LocalDate.now().plusDays(6), LocalDate.now().plusDays(7));
		Reservation reservation2 = new Reservation(LocalDate.now().plusDays(7), LocalDate.now().plusDays(8));
		
		reservationManager.createOrUpdateReservation(reservation1);
		reservationManager.createOrUpdateReservation(reservation2);
		
		reservation1.setArrival(LocalDate.now().plusDays(7));
		reservation1.setDeparture(LocalDate.now().plusDays(8));
		
		assertThrows(IllegalRequestException.class, () ->reservationManager.createOrUpdateReservation(reservation1));
		
				
		
	}
	

	
	



}
