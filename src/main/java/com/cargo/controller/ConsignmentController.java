package com.cargo.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cargo.config.ConsignmentRestURIConstants;
import com.cargo.datasource.ConsignmentRepository;
import com.cargo.datasource.PersonRepository;
import com.cargo.model.Consignment;

import com.cargo.model.Person;
import com.cargo.service.ConsignmentService;
import com.cargo.service.responce.ConsignmentInfoResponse;

@RestController
public class ConsignmentController {

	private static final Logger logger = LoggerFactory.getLogger(ConsignmentController.class);

	@Autowired
	private ConsignmentRepository consignmentDao;
	
	@Autowired
	private ConsignmentService consignmentService;
	
	@Autowired
	private PersonRepository personDAO;

	@RequestMapping(value = ConsignmentRestURIConstants.CREATE_CONSIGNMENT, method = RequestMethod.POST)
	public @ResponseBody Consignment createConsignment(@RequestBody Consignment consignment) {
		logger.info("Start createConssignment.");
		consignmentService.saveConsignment(consignment);
		/*for(int i = 0 ; i<consignment.getPersons().size() ; i++){
		    Person p = consignment.getPersons().get(i);
		    p.getConsignments().add(consignment);
		    personDAO.save(p);
		}
		*/
		return consignment;
	}

	@RequestMapping(value = ConsignmentRestURIConstants.GET_NEXT_ID, method = RequestMethod.GET)
	public long getNextId() {
		logger.info("get Next consignment Id.");
		return consignmentService.getNextId();
	}

	@RequestMapping(value = ConsignmentRestURIConstants.GET_ALL_CONSIGNMENT, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ConsignmentInfoResponse>> getConsignmentsByDate(@RequestParam Map<String, String> map) {
		logger.info("get consignment by date.");
		return consignmentService.getConsignmentsByDate(map);
	}

	@RequestMapping(value = ConsignmentRestURIConstants.DELETE_CONSIGNMENT_BY_ID, method = RequestMethod.POST)
	public void delete(@RequestParam(value = "consignments") List<Consignment> consignments) {
		logger.info("delete consignments");
		consignmentDao.delete(consignments);
	}

	@RequestMapping(value = ConsignmentRestURIConstants.DELETE_CONSIGNMENT, method = RequestMethod.GET)
	public void delete(@RequestParam Long id) {
		logger.info("delete consignments by id");
		consignmentService.deleteConsignment(id);
	}

	@RequestMapping(value = ConsignmentRestURIConstants.GET_CONSIGNMENTS_BY_DISPATCHER_ID, method = RequestMethod.GET)
	public ResponseEntity<List<ConsignmentInfoResponse>> getConsignmentsByDispatcherId(@PathVariable Long id) {
		logger.info("get consignments by dispatcher id");
		return consignmentService.getConsignmentByDispatchId(id);
	}

	@RequestMapping(value = ConsignmentRestURIConstants.UPDATE_CONSIGNMENT, method = RequestMethod.POST)
	public void updateDispatcher(@PathVariable Long id, @RequestBody String cbid) {
		logger.info("update consignment");
		consignmentService.updateConsignmetCBid(id, cbid);
	}

}
