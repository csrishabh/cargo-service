package com.cargo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargo.datasource.ConsignmentRepository;
import com.cargo.datasource.PaymetDAO;
import com.cargo.datasource.PersonRepository;
import com.cargo.model.Consignment;
import com.cargo.model.PaymentInfo;
import com.cargo.model.Person;

@Service
public class PaymentService {

	@Autowired
	private PaymetDAO paymetDAO;

	@Autowired
	private ConsignmentRepository consignmentDAO;
	
	@Autowired
	private PersonRepository personDAO;

	public List<PaymentInfo> getPersonPayments(Long id) {

		Person person = personDAO.findOne(id);
		if (person == null) {
			throw (new RuntimeException("Person not available"));
		}

		/*
		 * else if (consignment.getPayment_Type().equals("CASH")) { throw (new
		 * RuntimeException("PAYMENT NOT REQURIED")); }
		 */

		return paymetDAO.findbyPerson_Id(id);

	}

	public void addPaymentToPerson(Long pId, PaymentInfo info) {

		Person person = personDAO.findOne(pId);
		if (person == null) {
			throw new RuntimeException("person not found");
		}
		
		if(info.getDate()==null){
			info.setDate(new Date());
		}
		
		info.setPerson(person);
	    paymetDAO.save(info);
	}

}
