package com.cargo.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cargo.datasource.PersonRepository;
import com.cargo.model.Person;
import com.cargo.service.responce.ConsignmentInfoResponse;
import com.cargo.service.responce.PersonResponse;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ConsignmentService consignmentService;

	public Person getPerson(long personId) {

		return personRepository.findOne(personId);
	}

	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public ResponseEntity<Double> getTotalDue(Map<String, String> param) {

		List<ConsignmentInfoResponse> response = getConsignmentByPersonId(param).getBody();
		Double totalDue = response.stream().mapToDouble(consignmentInfoResponse -> consignmentInfoResponse.getTotal())
				.sum();
		return new ResponseEntity<Double>(totalDue, HttpStatus.OK);

	}

	public ResponseEntity<List<ConsignmentInfoResponse>> getConsignmentByPersonId(Map<String, String> param) {

		try {
			List<ConsignmentInfoResponse> response = consignmentService.getConsignmentByPersonId(param).getBody();
			return new ResponseEntity<List<ConsignmentInfoResponse>>(response, HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<List<ConsignmentInfoResponse>>(HttpStatus.BAD_REQUEST);
		}
	}

}
