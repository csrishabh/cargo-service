package com.cargo.datasource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cargo.model.City;

@Component
@Transactional
public interface CityDAO extends CrudRepository<City, String> {
		
}
