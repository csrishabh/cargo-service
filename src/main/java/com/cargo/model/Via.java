package com.cargo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="VIA")
@NamedQuery(query="select city from Via",name="GET_ALL_VIA")
public class Via {
	@Id
	@Column(name = "CITY")
	String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
