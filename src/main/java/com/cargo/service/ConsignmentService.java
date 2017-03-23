package com.cargo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cargo.config.ApplicationConfig;
import com.cargo.datasource.ConsignmentRepository;
import com.cargo.model.Consignment;
import com.cargo.model.ConsignmentInfoResponse;

@Service
public class ConsignmentService {

	@Autowired
	private ConsignmentRepository consignmentDAO;
	
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

	public void updateConsignmetCBid(Long id, String CBid) {
		Consignment consignment = consignmentDAO.findOne(id);
		consignment.setCbId(CBid);
		consignmentDAO.save(consignment);
	}

}
