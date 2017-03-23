package com.cargo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargo.datasource.ConsignmentRepository;
import com.cargo.datasource.DispatcherRepository;
import com.cargo.model.Consignment;
import com.cargo.model.Dispatcher;

@Service
public class DispatcherService {

	@Autowired
	private DispatcherRepository dispatcherDAO;

	@Autowired
	private ConsignmentRepository consignmentDAO;

	public void saveDispatcher(Dispatcher dispatcher) {

		dispatcher.setDispatch_Date(new Date());
		List<Consignment> consignments = new ArrayList<>();
		dispatcher.getConsignments().stream().forEach(consignment ->{
			consignment = consignmentDAO.findOne(consignment.getId());
			consignments.add(consignment);
		});
		dispatcher.setConsignments(consignments);
		dispatcherDAO.save(dispatcher);
		dispatcher.getConsignments().stream().forEach(consignment ->{
			consignment.setDispatcher(dispatcher);
			consignment.setStatus("DISPATCHED");
			consignmentDAO.save(consignment);
			
		});
	}

	public long getNextId() {

		return dispatcherDAO.getMaxId() + 1;
	}
	
	
	public List<Dispatcher> getDispatcherByDate(Map<String, String> map) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return dispatcherDAO.getAllDispatcher(formatter.parse(map.get("date1")), formatter.parse(map.get("date2")));
		} catch (ParseException e) {
			throw new RuntimeException("Date can not be parse");
		}		
	}

}
