package com.cargo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@NamedQueries({@NamedQuery(name="GET_NEXT_ID_CONSIGNMENT", query="select MAX(id) from Consignment"),
@NamedQuery(name="GET_CONSIGNMENTS_BY_DISPATCHER_ID", query="from Consignment where dispatcher.id = :id")})
@Table(name = "CONSIGNMENT")

public class Consignment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_con_seq_gen")
	@SequenceGenerator(name="my_con_seq_gen", sequenceName="ENTITY_SEQ")
	private long id = 0L;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "PERSON_CONSIGNMENT", joinColumns = {
			@JoinColumn(name = "CONSIGNMENT_ID", nullable = false) },
			inverseJoinColumns = { @JoinColumn(name = "PERSON_ID",
					nullable = false) })
	private List<Person> persons;
	
	@Column(name="WEIGHT")
	private double weight;
	@Column(name="QUANTITY")
	private int quantity;
	@Column(name="RATE")
	private double rate;
	@Column(name="CARRIGE_CHARGE")
	private double carrige_charge;
	@Column(name="OTHCHARGE")
	private double other_charge ;
	@Column(name= "STAX")
	private double s_Tax;
	
	@Column(name="PAYMENT_TYPE")
	private String payment_Type;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="ENTRY_DATE")
	@Type(type="date")
	private Date entry_Date;
	
	@Column(name="DELETED", nullable= false , columnDefinition = "TINYINT(1)")
	private boolean isDeleted = false;
	
	
	
	@Column(name="STATUS")
	private String Status = "BOOKED";
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="CBID")
	private String cbId;
	
	public String getCbId() {
		return cbId;
	}
	public void setCbId(String cbId) {
		this.cbId = cbId;
	}
	
	@ManyToOne
	@JoinColumn(name = "DISPATCHER_ID")
	@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id" , scope= Dispatcher.class)
	@JsonIdentityReference(alwaysAsId=true)
	private Dispatcher dispatcher;
	
	@Column(name="PAIDBY")
	private String paidBy;
	
	public String getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
	public double getCarrige_charge() {
		return carrige_charge;
	}

	public void setCarrige_charge(double carrige_charge) {
		this.carrige_charge = carrige_charge;
	}

	public double getOther_charge() {
		return other_charge;
	}
	public void setOther_charge(double other_charge) {
		this.other_charge = other_charge;
	}
	public double getS_Tax() {
		return s_Tax;
	}
	public void setS_Tax(double s_Tax) {
		this.s_Tax = s_Tax;
	}
	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getPayment_Type() {
		return payment_Type;
	}

	public void setPayment_Type(String payment_Type) {
		this.payment_Type = payment_Type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getEntry_Date() {
		return entry_Date;
	}

	public void setEntry_Date(Date entry_Date) {
		this.entry_Date = entry_Date;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
