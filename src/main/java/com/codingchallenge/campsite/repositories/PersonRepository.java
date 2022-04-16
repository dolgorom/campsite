package com.codingchallenge.campsite.repositories;


import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.codingchallenge.campsite.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
	
	Collection<Person> findByFullname(String fullname);
	
	Collection<Person> findByFullnameAndEmail(String fullname, String email);
	
	
}