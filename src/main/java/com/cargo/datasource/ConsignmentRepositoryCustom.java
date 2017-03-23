package com.cargo.datasource;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cargo.model.Consignment;
import com.cargo.model.ConsignmentInfoResponse;

public interface ConsignmentRepositoryCustom {
	
	public ResponseEntity<List<ConsignmentInfoResponse>> getConsigments(Map<String, String> params);

}
