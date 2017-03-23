package com.cargo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cargo.config.PersonRestURIConstants;
import com.cargo.datasource.PersonDAO;
import com.cargo.model.Person;

@RestController
public class PersonController {

	@Autowired
	private PersonDAO personDAO;
	
	
	@RequestMapping(value = PersonRestURIConstants.ADD_PERSON, method = RequestMethod.POST)
	public void addPerson(@RequestBody Person person){
		personDAO.save(person);
	}
	
	@RequestMapping(value = PersonRestURIConstants.GET_ALL_PERSON, method = RequestMethod.GET)
	@ResponseBody public List<Person> getPerson(@PathVariable("type") String type) {
		
		return personDAO.findByType(type); 

	}

}
