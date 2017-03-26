package com.cargo.datasource;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



import com.cargo.model.PaymentInfo;
@Transactional
public interface PaymetRepository extends CrudRepository<PaymentInfo, Long> {
	
	@Query("select p from PaymentInfo p where p.person.id=:id and p.date>=:date1 and p.date<=:date2")
	public List<PaymentInfo> findbyPerson_Id(@Param("id") Long id , @Param("date1") Date date1, @Param("date2") Date date2); 

}
