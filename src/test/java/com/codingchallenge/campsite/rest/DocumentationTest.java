package com.codingchallenge.campsite.rest;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.repositories.ReservationRepository;
import com.codingchallenge.campsite.requests.ReservatonRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.net.ObjectWriter;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class DocumentationTest {

	@Autowired
	private MockMvc mockMvc;


	@Test
	public void testAvailableSLots() throws Exception {
		LocalDate ld = LocalDate.now().plusMonths(1);
		String lastAvailable = ld.toString();
		this.mockMvc.perform(get("/available-slots")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(lastAvailable))).andDo(document("{methodName}"));

	}
	
	@Test
	public void testAvailableSlotsForDates() throws Exception {
		LocalDate start = LocalDate.now().plusDays(1);
		LocalDate end = LocalDate.now().plusDays(10);

		this.mockMvc.perform(get("/available-slots/start/" + start + "/end/" + end )).andDo(print()).andExpect(status().isOk())
				.andDo(document("{methodName}"));

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
				.andExpect(status().isCreated())
				.andDo(document("{methodName}"));

	}




}
