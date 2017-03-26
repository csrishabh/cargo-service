package com.cargo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cargo.config.ApplicationConfig;
import com.cargo.datasource.ConsignmentRepository;
import com.cargo.model.Consignment;
import com.cargo.model.Person;
import com.cargo.service.responce.ConsignmentInfoResponse;

@Service
public class ConsignmentService {

	@Autowired
	private ConsignmentRepository consignmentRepo;

	@Autowired
	private PersonService personService;

	public long getNextId() {

		return consignmentRepo.getMaxId() + 1;
	}

	public ResponseEntity<List<ConsignmentInfoResponse>> getConsignmentsByDate(Map<String, String> map) {

		return consignmentRepo.getConsigments(map);
	}

	public void saveConsignment(Consignment consignment) {
		consignment.setEntry_Date(new Date());
		List<Person> persons = new ArrayList<>();
		consignment.getPersons().stream().forEach(person -> {
			persons.add(personService.getPerson(person.getId()));
		});
		consignment.setPersons(persons);
		consignmentRepo.save(consignment);
	}

	public void deleteConsignment(Long id) {

		Consignment c = consignmentRepo.findOne(id);
		c.setDeleted(true);
		consignmentRepo.save(c);
	}

	public ResponseEntity<List<ConsignmentInfoResponse>> getConsignmentByDispatchId(long id) {

		List<Consignment> consignments = consignmentRepo.getConsignmentByDispatcher(id);

		List<ConsignmentInfoResponse> response = convertToResponse(consignments);

		return new ResponseEntity<List<ConsignmentInfoResponse>>(response, HttpStatus.OK);
	}

	public ResponseEntity<List<ConsignmentInfoResponse>> getConsignmentByPersonId(Map<String, String> param)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Stream<Consignment> stream = null;
		Person person = personService.getPerson(Long.parseLong(param.get("id")));
		if (person == null) {
			throw new RuntimeException("Invalid Person Id");
		}
		List<Consignment> consignments = person.getConsignments();
		stream = consignments.stream().filter(consignment -> consignment.getPaidBy().equals(person.getType()))
				.filter(consignment -> !consignment.isDeleted());
		if (param.get("date1") != null && param.get("date2") != null) {
			Date date1 = formatter.parse(param.get("date1"));
			Date date2 = formatter.parse(param.get("date2"));
			stream = stream.filter(consignment -> (consignment.getEntry_Date().compareTo(date1) >= 0))
					.filter(consignment -> (consignment.getEntry_Date().compareTo(date2) <= 0));

		}

		if (param.get("type") != null && !param.get("type").equals("")) {
			String type = param.get("type").toUpperCase();
			if (type.equals("ALL")) {
				stream = stream.filter(consignment -> !consignment.getPayment_Type().toUpperCase().equals("CASH"));
			} else {
				stream = stream.filter(consignment -> consignment.getPayment_Type().toUpperCase().equals(type));
			}

		}

		consignments = stream.collect(Collectors.toList());

		List<ConsignmentInfoResponse> response = convertToResponse(consignments);
		return new ResponseEntity<List<ConsignmentInfoResponse>>(response, HttpStatus.OK);
	}

	public void updateConsignmetCBid(Long id, String CBid) {
		Consignment consignment = consignmentRepo.findOne(id);
		consignment.setCbId(CBid);
		consignmentRepo.save(consignment);
	}

	public List<ConsignmentInfoResponse> convertToResponse(List<Consignment> consignments) {

		List<ConsignmentInfoResponse> response = new ArrayList<>();
		consignments.stream().forEach(consignment -> {
			ConsignmentInfoResponse res = new ConsignmentInfoResponse();
			BeanUtils.copyProperties(consignment, res);
			double total = (consignment.getWeight() * consignment.getRate()) + consignment.getS_Tax()
					+ consignment.getOther_charge() + consignment.getCarrige_charge();
			res.setTotal(total);
			if (consignment.getDispatcher() != null) {
				res.setDispatcherId(consignment.getDispatcher().getId());
			}
			consignment.getPersons().stream().forEach(p -> {
				if (p.getType().equals(ApplicationConfig.CONSIGNEE)) {
					res.setTo(p.getCity());
					res.setConsingee(p.getName());
				} else if (p.getType().equals(ApplicationConfig.CONSIGNOR)) {
					res.setFrom(p.getCity());
					res.setConsignor(p.getName());
				}
			});
			response.add(res);
		});

		return response;
	}

}
