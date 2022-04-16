package com.codingchallenge.campsite.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.codingchallenge.campsite.model.Person;


	
	@DataJpaTest
	public class PersonRepositoryTest {
		
		@Autowired
		private TestEntityManager entityManager;

		@Autowired
		private PersonRepository persons;

		@Test
		public void testFindByID() {
			Person person = new Person("test", "email");
			entityManager.persist(person);

			Collection<Person> findByLastName = persons.findByFullname("test");

			assertEquals(findByLastName.size(), 1);
		}
		
		
		@Test
		public void testFindByNameAndEmail() {
			
			String fullname = "John Ball";
			String email = "John@Ball.com";
			
			Person person = new Person(fullname, email);

			entityManager.persist(person);

			Collection<Person> findByLastName = persons.findByFullnameAndEmail(fullname, email);

			assertEquals(1, findByLastName.size());
			assertEquals(fullname, findByLastName.iterator().next().getFullname());
			assertEquals(email, findByLastName.iterator().next().getEmail());
			assertTrue(0 != findByLastName.iterator().next().getId());
			
		}
	}
