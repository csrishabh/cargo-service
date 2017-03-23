package com.cargo.datasource;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cargo.model.Consignment;

@Repository
public interface ConsignmentRepository extends CrudRepository<Consignment, Long> , ConsignmentRepositoryCustom{

	@Query("select coalesce(max(c.id), '0') from Consignment c")
	public long getMaxId();

	@Query("select c from Consignment c where c.entry_Date>= :start_date and c.entry_Date<= :end_date")
	public List<Consignment> getAllConsignment(@Param("start_date") Date start_date, @Param("end_date") Date end_date);

	@Query("select c from Consignment c where c.dispatcher.id = :id")
	public List<Consignment> getConsignmentByDispatcher(@Param("id") Long id);

}
