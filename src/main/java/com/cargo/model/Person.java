package com.cargo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="PERSON")
public class Person implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="P_ID")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_person_seq_gen")
	@SequenceGenerator(name="my_person_seq_gen", sequenceName="ENTITY_SEQ")
	private Long id = 0L;
	
	@Column(name="P_NAME")
	private String name;
	@Column(name="P_ADD")
	private String add;
	@Column(name="P_STATE")
	private String state;
	@Column(name="P_CITY")
	private String city;
	@Column(name="P_PIN")
	private String pin;
	@Column(name="P_MOBILE")
	private String mobile;
	@Column(name="P_TYPE")
	private String type;
	@Column(name="P_CATEGORY")
	private String category = "PERMANENT"; 
	
	@ManyToMany(fetch = FetchType.LAZY , mappedBy = "persons")
	
	private List<Consignment> consignments = new ArrayList<>();

	@OneToMany(mappedBy="person" , cascade = CascadeType.ALL)
	@JsonBackReference()
	private List<PaymentInfo> paymentInfos = new ArrayList<PaymentInfo>();
	
	public List<PaymentInfo> getPaymentInfos() {
		return paymentInfos;
	}

	public void setPaymentInfos(List<PaymentInfo> paymentInfos) {
		this.paymentInfos = paymentInfos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Consignment> getConsignments() {
		return consignments;
	}

	public void setConsignments(List<Consignment> consignments) {
		this.consignments = consignments;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
