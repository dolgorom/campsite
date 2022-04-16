package com.codingchallenge.campsite.model;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.codingchallenge.campsite.rest.exceptions.IllegalRequestException;




@SpringBootTest()
@ContextConfiguration
public class ReservationManagerTest {
	
	@Autowired
	private ReservationManager reservationManager;
	
	@Test
	public void testSuccessfullReservationAndFailed() {
		
		assertTrue(null != reservationManager);
		Reservation reservation1 = new Reservation(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
		Reservation reservation2 = new Reservation(LocalDate.now().plusDays(2), LocalDate.now().plusDays(3));
		
		reservationManager.createReservation(reservation1);
		
		//should fail since the dates are taken already
		assertThrows(IllegalRequestException.class, () -> {reservationManager.createReservation(reservation2);});
		
		
	}
	

	
	



}
