package com.cargo.datasource;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cargo.model.Via;

@Component
@Transactional
public interface ViaDAO extends CrudRepository<Via, String> {

	@Query("select v from Via v where v.city like lower(:name) ")
	public List<Via> getViaByName(@Param("name") String name);
}
