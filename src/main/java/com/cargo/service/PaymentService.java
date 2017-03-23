package com.cargo.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargo.datasource.ConsignmentRepository;
import com.cargo.datasource.PaymetDAO;
import com.cargo.model.Consignment;
import com.cargo.model.PaymentInfo;

@Service
public class PaymentService {

	@Autowired
	private PaymetDAO paymetDAO;

	@Autowired
	private ConsignmentRepository consignmentDAO;

	public List<PaymentInfo> getConsignmentPayments(Long id) {

		Consignment consignment = consignmentDAO.findOne(id);
		if (consignment == null) {
			throw (new RuntimeException("Consignment not available"));
		}

		/*
		 * else if (consignment.getPayment_Type().equals("CASH")) { throw (new
		 * RuntimeException("PAYMENT NOT REQURIED")); }
		 */

		return paymetDAO.findbyConsignment_Id(id);

	}

	public void addPaymentToConsignment(Long cId, PaymentInfo info) {

		Consignment consignment = consignmentDAO.findOne(cId);
		if (consignment == null) {
			throw new RuntimeException("Consignment not found");
		}
		
		if(info.getDate()==null){
			info.setDate(new Date());
		}
		
		info.setConsignment(consignment);
	    paymetDAO.save(info);
	}

}
