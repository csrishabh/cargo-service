package com.cargo.datasource;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cargo.model.City;

@Component
@Transactional
public interface CityDAO extends CrudRepository<City, String> {
	
	@Query("select c from City c where c.name like  lower(:name) ")
	public List<City> getCityByName(@Param("name") String name);
		
}
