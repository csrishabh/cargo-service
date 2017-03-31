package com.cargo.datasource;

import java.util.List;
import java.util.Map;

import com.cargo.model.Consignment;


public interface ConsignmentRepositoryCustom {
	
	public List<Consignment> getConsigments(Map<String, String> params);
}
