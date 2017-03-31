package com.cargo.datasource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cargo.config.ApplicationConfig;
import com.cargo.model.Consignment;

import com.cargo.model.Consignment_;
import com.cargo.service.responce.ConsignmentInfoResponse;

public class ConsignmentRepositoryImpl implements ConsignmentRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Consignment> getConsigments(Map<String, String> params) {
		    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	        CriteriaQuery<Consignment> query = builder.createQuery(Consignment.class);
	        Root<Consignment> r = query.from(Consignment.class);
	        
	        Predicate predicate = builder.conjunction();
	        try {
	        	
	        	if(params.get("date1") != null && params.get("date2") != null ){
				
	        		predicate = builder.between(r.get(Consignment_.entry_Date), formatter.parse(params.get("date1")), formatter.parse(params.get("date2")));
	        	}
	        	
	        	else{
	        		
	        		Date currentDate = formatter.parse(formatter.format(new Date()));
	        		predicate = builder.between(r.get(Consignment_.entry_Date), currentDate , currentDate);
	        	}
			    
				if(params.get("type")!=null && !params.get("type").equals("") && !params.get("type").equals("All")){
					
					predicate = builder.and(predicate,builder.equal(r.get(Consignment_.payment_Type), params.get("type")));
				}
                if(params.get("status")!=null && !params.get("status").equals("") && !params.get("status").equals("All")){
					
					predicate = builder.and(predicate,builder.equal(r.get(Consignment_.Status), params.get("status")));
				}
                
                predicate = builder.and(predicate,builder.notEqual(r.get(Consignment_.isDeleted), true)); 
				
	        } catch (ParseException e) {
	        	 return new ArrayList<Consignment>();
			}
	        query.where(predicate);
	        List<Consignment> result = entityManager.createQuery(query).getResultList();
	        if(result == null){
	        	return new ArrayList<Consignment>();
	        }
	        return result;
	}

}
