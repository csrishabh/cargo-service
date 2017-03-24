package com.cargo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargo.datasource.PersonRepository;
import com.cargo.model.Person;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	
	
	public Person getPerson(long personId){
		
		return personRepository.findOne(personId);
	}
	
}
