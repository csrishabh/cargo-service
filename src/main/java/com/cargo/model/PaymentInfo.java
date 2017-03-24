package com.cargo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="PAYMENTINFO")
public class PaymentInfo {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_seq_gen")
	@SequenceGenerator(name="my_seq_gen", sequenceName="ENTITY_SEQ")
	Long id = 0L;
	
	@Column(name="amt")
	Double amount;
	
	@Column
	PaymentType type = PaymentType.CASH;
	
	@Column(name="chqNum")
	String chequeNum;
	
	@Column
	private Date date; 
	
	@ManyToOne
	@JoinColumn(name = "PERSON_ID")
	@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id" , scope= Person.class)
	@JsonIdentityReference(alwaysAsId=true)
	Person person;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}

	public String getChequeNum() {
		return chequeNum;
	}

	public void setChequeNum(String chequeNum) {
		this.chequeNum = chequeNum;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
