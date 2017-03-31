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
public interface PersonRepository extends CrudRepository<Person, Long> , PersonRepositoryCustom  {
	
	
	public List<Person> findByType(String type); 
	
	@Query("select p from Person p where p.type=:type and p.name like lower(:name) and p.category = lower(:category)")
	public List<Person> findByTypeAndName(@Param("type")String type , @Param("name")String name ,@Param("category")String category);

}
