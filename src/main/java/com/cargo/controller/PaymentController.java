package com.cargo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cargo.config.PaymentURIConstants;
import com.cargo.model.PaymentInfo;
import com.cargo.service.PaymentService;

@RestController
public class PaymentController {

	public PaymentController() {

	}

	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = PaymentURIConstants.GET_CONSIGNMETS_PAYMENTS, method = RequestMethod.GET)
	@ResponseBody public  List<PaymentInfo> getPersonPayments(@PathVariable("id") Long id) {
		
		return paymentService.getPersonPayments(id);

	}
	
	@RequestMapping(value = PaymentURIConstants.ADD_PAYMENT, method = RequestMethod.POST)
	@ResponseBody public void addPayment(@PathVariable("id") Long personId , @RequestBody PaymentInfo info) {
		
		    paymentService.addPaymentToPerson(personId, info);

	}
	
	
	

}
