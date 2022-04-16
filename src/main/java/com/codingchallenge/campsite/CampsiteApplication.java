package com.codingchallenge.campsite;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.codingchallenge.campsite.model.Person;
import com.codingchallenge.campsite.model.Reservation;
import com.codingchallenge.campsite.repositories.PersonRepository;
import com.codingchallenge.campsite.repositories.ReservationRepository;


	@SpringBootApplication
	@EnableConfigurationProperties
	public class CampsiteApplication {
		
		private static final Logger log = LoggerFactory.getLogger(CampsiteApplication.class);

	    public static void main(String[] args) {
	    	SpringApplication.run(CampsiteApplication.class, args);
	    }
	    
	    
	    @Bean
		public CommandLineRunner demo(ReservationRepository repository, PersonRepository person) {
			return (args) -> {
				// save a few customers

				Person p = new Person("John", "email@test.com");
				p.setId(1);
				person.save(p);
				
				repository.save(new Reservation(LocalDate.parse("2022-09-10"), LocalDate.parse("2022-09-11")));
				repository.save(new Reservation(LocalDate.parse("2022-09-10"), LocalDate.parse("2022-09-11")));


				// fetch all customers
				log.info("Customers found with findAll():");
				log.info("-------------------------------");
				for (Reservation reservation : repository.findAll()) {
					log.info(reservation.toString());
				}
				log.info("");


				log.info("");
			};
		}
	}

