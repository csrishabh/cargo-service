package com.cargo.datasource;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cargo.model.Person;

@Component
@Transactional
public interface PersonDAO extends CrudRepository<Person, Long>  {
	
	
	public List<Person> findByType(String type); 

}
