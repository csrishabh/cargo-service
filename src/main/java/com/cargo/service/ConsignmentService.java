package com.cargo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	private ConsignmentRepository consignmentDAO;
	
	@Autowired
	private PersonService personService;
	
	public long getNextId() {

		return consignmentDAO.getMaxId() + 1;
	}

	public ResponseEntity<List<ConsignmentInfoResponse>> getConsignmentsByDate(Map<String, String> map) {
		
		return consignmentDAO.getConsigments(map);
		/*SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return consignmentDAO.getAllConsignment(formatter.parse(map.get("date1")),
					formatter.parse(map.get("date2")));
		} catch (ParseException e) {
			throw new RuntimeException("Date can not be parse");
		}*/
	}

	public void saveConsignment(Consignment consignment) {
		consignment.setEntry_Date(new Date());
		consignmentDAO.save(consignment);
	}

	public void deleteConsignment(Long id) {

		Consignment c = consignmentDAO.findOne(id);
		c.setDeleted(true);
		consignmentDAO.save(c);
	}

	public ResponseEntity<List<ConsignmentInfoResponse>> getConsignmentByDispatchId(long id) {

		
		List<Consignment> consignments = consignmentDAO.getConsignmentByDispatcher(id);
		
		List<ConsignmentInfoResponse> response = new ArrayList<>();
		
		consignments.stream().forEach(consignment ->{
	        	ConsignmentInfoResponse res = new ConsignmentInfoResponse();
	        	BeanUtils.copyProperties(consignment, res);
	        	double total = (consignment.getWeight()*consignment.getRate()) + consignment.getS_Tax() + consignment.getOther_charge() + consignment.getCarrige_charge() ;
	        	res.setTotal(total);
	        	if(consignment.getDispatcher() != null){
	        	res.setDispatcherId(consignment.getDispatcher().getId());
	        	}
	        	consignment.getPersons().stream().forEach(person ->{
	        		if(person.getType().equals(ApplicationConfig.CONSIGNEE)){
	        			res.setTo(person.getCity());
	        			res.setConsingee(person.getName());
	        		}
	        		else if(person.getType().equals(ApplicationConfig.CONSIGNOR)){
	        			res.setFrom(person.getCity());
	        			res.setConsignor(person.getName());
	        		}
	        	});
	        	response.add(res);
	        });

		return new ResponseEntity<List<ConsignmentInfoResponse>>(response, HttpStatus.OK);
	}
	
	
	public ResponseEntity<List<ConsignmentInfoResponse>> getConsignmentByPersonId(Map<String, String> param) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Person person = personService.getPerson(Long.parseLong(param.get("pId")));
		
		Date date1 = formatter.parse(param.get("date1"));
		Date date2 = formatter.parse(param.get("date2"));
		
		if(person == null){
			throw new RuntimeException("Invalid Person Id");
		}
		
		List<Consignment> consignments = person.getConsignments();
		consignments.stream().filter(consignment -> (consignment.getEntry_Date().compareTo(date1) >= 0))
				.filter(consignment -> (consignment.getEntry_Date().compareTo(date2) <= 0))
				.collect(Collectors.toList());
		
		List<ConsignmentInfoResponse> response = new ArrayList<>();
		
		return null;
	}
	

	public void updateConsignmetCBid(Long id, String CBid) {
		Consignment consignment = consignmentDAO.findOne(id);
		consignment.setCbId(CBid);
		consignmentDAO.save(consignment);
	}

}
