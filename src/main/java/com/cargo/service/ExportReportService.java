package com.cargo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargo.model.PaymentInfo;
import com.cargo.model.Person;
import com.cargo.service.responce.ConsignmentInfoResponse;
import com.cargo.service.responce.PaymentSummary;

@Service
public class ExportReportService {

	@Autowired
	private PersonService personService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private ConsignmentService consignmentService;

	public Map<String, Object> exportPaymentReport(Map<String, String> param) {

		Person person = personService.getPerson(Long.parseLong(param.get("id")));
		List<ConsignmentInfoResponse> consignments = personService.getConsignmentByPersonId(param).getBody();
		// Double totalDue = personService.getTotalDue(param).getBody();
		List<PaymentInfo> payments = paymentService.getPersonPayments(param).getBody();

		Map<String, Object> fileData = new HashMap<>();
		fileData.put("person", person);
		fileData.put("consignments", consignments);
		fileData.put("payments", payments);

		return fileData;

	}

	public Map<String, Object> exportCollectionReport(Map<String, String> param) {

		List<ConsignmentInfoResponse> consignments = consignmentService.getConsignmentsByDate(param).getBody();

		Map<String, Object> fileData = new HashMap<>();
		fileData.put("consignments", consignments);
		return fileData;

	}

	public Map<String, Object> exportPaymentSummaryReport(Map<String, String> param) {

		List<PaymentSummary> summaryList = paymentService.buildPaymentSummary(param);

		Map<String, Object> fileData = new HashMap<>();
		fileData.put("summaryList", summaryList);
		return fileData;

	}

}
