package com.cargo.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cargo.config.CityViaRestURIConstants;
import com.cargo.datasource.CityDAO;
import com.cargo.datasource.ViaDAO;
import com.cargo.model.City;
import com.cargo.model.Via;
import com.cargo.util.Utils;



@RestController
public class CityViaController {
	
	private static final Logger logger = LoggerFactory.getLogger(CityViaController.class);
	
	@Autowired
	private CityDAO cityDAO;
	
	@Autowired
	private ViaDAO viaDao;
	
	@RequestMapping(value = CityViaRestURIConstants.GET_ALL_CITY ,method = RequestMethod.GET)
	public List<City> getAllCity(@PathVariable("name") String name ){
		logger.info("Get all city");
		return cityDAO.getCityByName("%"+name+"%");
	}
	@RequestMapping(value = CityViaRestURIConstants.GET_ALL_VIA ,method = RequestMethod.GET)
	public List<Via> getAllVia(@PathVariable("name") String name){
		logger.info("Get all via");
		return viaDao.getViaByName("%"+name+"%");
		
	}
	
	@RequestMapping(value = CityViaRestURIConstants.ADD_CITY ,method = RequestMethod.POST)
	public void addCity(@RequestBody String city){
		logger.info("Add City");
		City c = new City();
		c.setName(city);
		cityDAO.save(c);
	}
	
	@RequestMapping(value = CityViaRestURIConstants.ADD_VIA ,method = RequestMethod.POST)
	public void addVia(@RequestBody String via){
		logger.info("Add Via");
		Via v = new Via();
		v.setCity(via);
		viaDao.save(v);
	}

}
