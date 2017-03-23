package com.cargo.datasource;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



import com.cargo.model.PaymentInfo;
@Transactional
public interface PaymetDAO extends CrudRepository<PaymentInfo, Long> {
	
	@Query("select p from PaymentInfo p where p.consignment.id=:id")
	public List<PaymentInfo> findbyConsignment_Id(@Param("id") Long id); 

}
