package com.cargo.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cargo.config.DispatcherRestURIConstants;
import com.cargo.datasource.DispatcherRepository;
import com.cargo.model.Dispatcher;
import com.cargo.service.DispatcherService;



@RestController
public class DispatcherController {
	
	private static final Logger logger = LoggerFactory.getLogger(DispatcherController.class);
	
	@Autowired
	private DispatcherRepository dispatcherDao;
	
	@Autowired
	private DispatcherService dispatcherService;
	
	
	@RequestMapping(value = DispatcherRestURIConstants.CREATE_DISPATCHER, method = RequestMethod.POST)
	public @ResponseBody Dispatcher createDispatcher( @RequestBody Dispatcher dispatcher) {
		logger.info("Start createDispatcher.");
		dispatcherService.saveDispatcher(dispatcher);
		return dispatcher;
	}
	
	@RequestMapping(value = DispatcherRestURIConstants.GET_NEXT_ID, method = RequestMethod.GET)
	public long getNextId() {
		logger.info("get Next dispatcher Id.");
		return dispatcherService.getNextId();
	}
	@RequestMapping(value = DispatcherRestURIConstants.GET_DISPATCHERS, method = RequestMethod.GET)
	public @ResponseBody List<Dispatcher> getDispatchers(@RequestParam Map<String, String> map) {
		logger.info("get dispatchers");
		return dispatcherService.getDispatcherByDate(map);
	}
	
	@RequestMapping(value = DispatcherRestURIConstants.UPDATE_DISPATCHER, method = RequestMethod.POST)
	public void updateDispatcher(@RequestBody Dispatcher dispatcher) {
		logger.info("update dispatchers");
		dispatcherDao.save(dispatcher);
	}

}
