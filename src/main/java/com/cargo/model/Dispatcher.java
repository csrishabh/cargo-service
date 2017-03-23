package com.cargo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@NamedQueries({@NamedQuery(name="GET_NEXT_ID_DISPATCHER", query="select MAX(id) from Dispatcher")})
@Table(name="DISPATCHER")
public class Dispatcher implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	private long id;
	@Column(name="DRIVER_NAME")
	private String driver_Name;
	@Column(name="CITY")
	private String city;
	@Column(name="STATE")
	private String state;
	@Column(name="DISPATCH_DATE")
	@Type(type="date")
	private Date dispatch_Date;

	@OneToMany(mappedBy="dispatcher" , cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Consignment> consignments = new ArrayList<Consignment>();
	
	@Column(name="CBID")
	private String cbId;
	
	
	public String getCbId() {
		return cbId;
	}
	public void setCbId(String cbId) {
		this.cbId = cbId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDriver_Name() {
		return driver_Name;
	}
	public void setDriver_Name(String driver_Name) {
		this.driver_Name = driver_Name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public Date getDispatch_Date() {
		return dispatch_Date;
	}
	public void setDispatch_Date(Date dispatch_Date) {
		this.dispatch_Date = dispatch_Date;
	}
	public List<Consignment> getConsignments() {
		return consignments;
	}
	public void setConsignments(List<Consignment> consignments) {
		this.consignments = consignments;
	}
	
}
