package com.cargo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = PaymentURIConstants.GET_PERSON_PAYMENTS, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<PaymentInfo>> getPersonPayments(@RequestParam Map<String, String> param) {

		return paymentService.getPersonPayments(param);

	}

	@RequestMapping(value = PaymentURIConstants.ADD_PAYMENT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PaymentInfo> addPayment(@PathVariable("id") Long personId, @RequestBody PaymentInfo info) {

		try {
			PaymentInfo paymentInfo = paymentService.addPaymentToPerson(personId, info);
			return new ResponseEntity<PaymentInfo>(paymentInfo, HttpStatus.CREATED);
		} catch (Exception exceptions) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}
