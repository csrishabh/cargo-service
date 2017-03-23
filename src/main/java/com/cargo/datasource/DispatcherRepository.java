package com.cargo.datasource;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cargo.model.Dispatcher;


@Component
@Transactional
public interface DispatcherRepository extends CrudRepository<Dispatcher, Long> {
	
@Query("select coalesce(max(d.id), '0') from Dispatcher d")
public long getMaxId();

@Query("select d from Dispatcher d where d.dispatch_Date>= :start_date and d.dispatch_Date<= :end_date")
public List<Dispatcher> getAllDispatcher( @Param("start_date") Date start_date , @Param("end_date") Date end_date );

}
