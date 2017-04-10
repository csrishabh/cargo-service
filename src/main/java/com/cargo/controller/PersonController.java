package com.cargo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cargo.config.PersonRestURIConstants;
import com.cargo.datasource.PersonRepository;
import com.cargo.model.Person;
import com.cargo.service.PersonService;
import com.cargo.service.responce.ConsignmentInfoResponse;
import com.cargo.service.responce.PersonResponse;

@RestController
public class PersonController {

	@Autowired
	private PersonRepository personDAO;
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = PersonRestURIConstants.ADD_PERSON, method = RequestMethod.POST)
	public void addPerson(@RequestBody Person person){
		personService.savePerson(person);
	}
	
	@RequestMapping(value = PersonRestURIConstants.GET_ALL_PERSON, method = RequestMethod.GET)
	@ResponseBody public List<PersonResponse> getPerson(@PathVariable("type") String type) {
		List<PersonResponse> response = new ArrayList<>();
		
		personDAO.findByType(type.toUpperCase()).stream().forEach(person-> {
			PersonResponse personResponse = new PersonResponse();
			BeanUtils.copyProperties(person, personResponse);
			response.add(personResponse);
		});; 
		return response;

	}
	
	@RequestMapping(value = PersonRestURIConstants.GET_ALL_PERSON_BY_NAME, method = RequestMethod.GET)
	@ResponseBody public List<PersonResponse> getPersonByNameAndType(@PathVariable("type") String type, @PathVariable String name) {
		List<PersonResponse> response = new ArrayList<>();
		
		personDAO.findByTypeAndName(type.toUpperCase(),"%"+name+"%","PERMANENT").stream().forEach(person-> {
			PersonResponse personResponse = new PersonResponse();
			BeanUtils.copyProperties(person, personResponse);
			response.add(personResponse);
		});; 
		return response;

	}
	
	@RequestMapping(value = PersonRestURIConstants.GET_TOTAL_DUE, method = RequestMethod.GET)
	@ResponseBody public ResponseEntity<Double> getTotalDueOnPerson(@RequestParam Map<String, String> param) {
		
		return personService.getTotalDue(param);

	}
	
	@RequestMapping(value = PersonRestURIConstants.GET_ALL_CONSIGNMENT_BY_PERSON, method = RequestMethod.GET)
	@ResponseBody public ResponseEntity<List<ConsignmentInfoResponse>> getAllConsignmentByPerson(@RequestParam Map<String, String> param) {
		
		return personService.getConsignmentByPersonId(param);

	}
	
	
	
	

}
