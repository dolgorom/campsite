package com.codingchallenge.campsite.rest;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.repositories.ReservationRepository;
import com.codingchallenge.campsite.requests.ModificationRequest;
import com.codingchallenge.campsite.requests.ReservatonRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ReservationRepository reservations;

	@Test
	public void testAvailableSpots() throws Exception {
		LocalDate ld = LocalDate.now().plusMonths(1);
		String lastAvailable = ld.toString();
		this.mockMvc.perform(get("/available-slots")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(lastAvailable)));

	}

	@Test
	public void testMakeReservation() throws Exception {

		ReservatonRequest reservatonRequest = new ReservatonRequest();
		reservatonRequest.setArrival(LocalDate.now().plusDays(2).toString());
		reservatonRequest.setDeparture(LocalDate.now().plusDays(3).toString());
		reservatonRequest.setFullname("fullname");
		reservatonRequest.setEmail("email4@mail.com");
		
		com.fasterxml.jackson.databind.ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String reservRequest = ow.writeValueAsString(reservatonRequest);

		this.mockMvc.perform(post("/make-reservation").content(reservRequest).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
				//.andDo(document("{methodName}"));

	}

	@Test
	public void testMakeTwoReservation() throws Exception {

		ReservatonRequest reservatonRequest = new ReservatonRequest();
		reservatonRequest.setArrival(LocalDate.now().plusDays(5).toString());
		reservatonRequest.setDeparture(LocalDate.now().plusDays(7).toString());
		reservatonRequest.setFullname("fullname");
		reservatonRequest.setEmail("email4@mail.com");
		
		com.fasterxml.jackson.databind.ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String reservRequest = ow.writeValueAsString(reservatonRequest);

		this.mockMvc.perform(post("/make-reservation").content(reservRequest).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		this.mockMvc.perform(post("/make-reservation").content(reservRequest).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());

	}

	@Test
	public void testMakeReservationCancelReservation() throws Exception {
		
		ReservatonRequest reservatonRequest = new ReservatonRequest();
		reservatonRequest.setArrival(LocalDate.now().plusDays(1).toString());
		reservatonRequest.setDeparture(LocalDate.now().plusDays(2).toString());
		reservatonRequest.setFullname("fullname");
		reservatonRequest.setEmail("email4@mail.com");
		
		com.fasterxml.jackson.databind.ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonRequest = ow.writeValueAsString(reservatonRequest);
		
		String response = this.mockMvc
				.perform(post("/make-reservation").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		JSONObject json = (JSONObject) JSONParser.parseJSON(response);
		UUID id = UUID.fromString((String)json.get("id"));

		// Cancel previous booking to allow booking for the same date slot
		this.mockMvc.perform(get("/cancel-reservation/" + id)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("2022")));

		//
		this.mockMvc.perform(post("/make-reservation").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

	}
	
	@Test
	public void testMakeReservationModifyReservation() throws Exception {
		
		ReservatonRequest reservatonRequest = new ReservatonRequest();
		reservatonRequest.setArrival(LocalDate.now().plusDays(8).toString());
		reservatonRequest.setDeparture(LocalDate.now().plusDays(9).toString());
		reservatonRequest.setFullname("fullname");
		reservatonRequest.setEmail("email4@mail.com");
		
		com.fasterxml.jackson.databind.ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonRequest = ow.writeValueAsString(reservatonRequest);
		
		String response = this.mockMvc
				.perform(post("/make-reservation").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		JSONObject json = (JSONObject) JSONParser.parseJSON(response);
		String id = (String)json.get("id");
		
		String newArrival = LocalDate.now().plusDays(9).toString();
		String newDeparture = LocalDate.now().plusDays(10).toString();

		ModificationRequest modificationRequest = new ModificationRequest();
		
		modificationRequest.setReservationId(id);
		modificationRequest.setNewArrival(newArrival);
		modificationRequest.setNewDeparture(newDeparture);
		
		String jsonModRequest = ow.writeValueAsString(modificationRequest);
		
		// Cancel previous booking to allow booking for the same date slot
		this.mockMvc.perform(post("/modify-reservation/").content(jsonModRequest).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(newArrival)))
				.andExpect(content().string(containsString(newDeparture)));



	}
	
	/**
	 * 
	 * Multi-threaded test to make sure that a booking slot can't be booked more than one time
	 * 
	 * @throws Exception
	 */

	@Test
	public void testConcurrencyReservation() throws Exception {
		
		Iterable<Reservation> allreservations = reservations.findAll();
		
		long reservationsBeforeTest = StreamSupport.stream(allreservations.spliterator(), false).count();
		

		ReservatonRequest reservatonRequest = new ReservatonRequest();
		reservatonRequest.setArrival(LocalDate.now().plusDays(7).toString());
		reservatonRequest.setDeparture(LocalDate.now().plusDays(8).toString());
		reservatonRequest.setFullname("fullname");
		reservatonRequest.setEmail("email4@mail.com");
		
		com.fasterxml.jackson.databind.ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String reservRequest = ow.writeValueAsString(reservatonRequest);

		ExecutorService executor = Executors.newFixedThreadPool(50);
		for (int i = 0; i < 50; i++) {
			Runnable worker = () -> {
				try {
					this.mockMvc
							.perform(post("/make-reservation").content(reservRequest)
									.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			executor.execute(worker);
		}

		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		
		Iterable<Reservation> allreservationsAfterTest = reservations.findAll();
		long reservationsAfterTest = StreamSupport.stream(allreservationsAfterTest.spliterator(), false).count();
		
		//make sure that just one reservation was created
		assertEquals(reservationsBeforeTest + 1, reservationsAfterTest);
		System.out.println("Finished all threads");

	}

}
