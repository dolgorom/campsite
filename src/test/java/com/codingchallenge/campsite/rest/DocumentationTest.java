package com.codingchallenge.campsite.rest;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.codingchallenge.campsite.requests.ModificationRequest;
import com.codingchallenge.campsite.requests.ReservatonRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.time.LocalDate;
import java.util.UUID;

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
	
	@Test
	public void testCancelReservation() throws Exception {
		
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
				.andExpect(content().string(containsString("2022"))).andDo(document("{methodName}"));

		//
		this.mockMvc.perform(post("/make-reservation").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

	}
	
	@Test
	public void testModifyReservation() throws Exception {
		
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
				.andExpect(content().string(containsString(newDeparture))).andDo(document("{methodName}"));



	}
	




}
