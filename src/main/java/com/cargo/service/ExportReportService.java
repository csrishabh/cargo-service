package com.cargo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargo.model.PaymentInfo;
import com.cargo.model.Person;

@Service
public class ExportReportService {

	@Autowired
	private PersonService personService;

	@Autowired
	private PaymentService paymentService;

	public Map<String, Object> exportPaymentReport(Map<String, String> param) {
		
		Person person  = personService.getPerson(Long.parseLong(param.get("id")));
		Double totalDue = personService.getTotalDue(param).getBody();
		List<PaymentInfo> payments = paymentService.getPersonPayments(param).getBody();
		
		Map<String, Object> fileData = new HashMap<>();
		fileData.put("person", person);
		fileData.put("totaldue", totalDue);
		fileData.put("payments", payments);
		
		return fileData;

	}

}