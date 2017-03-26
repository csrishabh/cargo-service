package com.cargo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cargo.datasource.ConsignmentRepository;
import com.cargo.datasource.PaymetRepository;
import com.cargo.datasource.PersonRepository;
import com.cargo.model.PaymentInfo;
import com.cargo.model.Person;

@Service
public class PaymentService {

	@Autowired
	private PaymetRepository paymetDAO;

	@Autowired
	private PersonRepository personDAO;

	public ResponseEntity<List<PaymentInfo>> getPersonPayments(Map<String, String> param) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = null, date2 = null;
		Person person = personDAO.findOne(Long.parseLong(param.get("id")));
		if (person == null) {
			throw (new RuntimeException("Person not available"));
		}

		Long id = person.getId();
		try {
			if (param.get("date1") != null && param.get("date2") != null) {
				date1 = formatter.parse(param.get("date1"));
				date2 = formatter.parse(param.get("date2"));
			}
			else{
			date1 = formatter.parse(formatter.format(new Date()));
			date2 = date1;
			}
		} catch (ParseException exception) {

			System.out.println("Date Can not be parse");
			return new ResponseEntity<List<PaymentInfo>>(HttpStatus.BAD_REQUEST);
		}

		List<PaymentInfo> payments = paymetDAO.findbyPerson_Id(id, date1, date2);
		return new ResponseEntity<List<PaymentInfo>>(payments, HttpStatus.OK);

	}

	public PaymentInfo addPaymentToPerson(Long pId, PaymentInfo info) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Person person = personDAO.findOne(pId);
		if (person == null) {
			throw new RuntimeException("person not found");
		}

		if (info.getDate() == null) {
			info.setDate(formatter.parse(formatter.format(new Date())));
		}
		else{
			info.setDate(formatter.parse(formatter.format(info.getDate())));
		}
		

		info.setPerson(person);
		paymetDAO.save(info);
		return info;
	}

}
