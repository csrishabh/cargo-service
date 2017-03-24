package com.cargo.service.responce;

import java.util.Date;

public class ConsignmentInfoResponse {

	private Long id;

	private double weight;

	private int quantity;

	private double rate;

	private double carrige_charge;

	private double other_charge;

	private double s_Tax;

	private String payment_Type;

	private String type;

	private Date entry_Date;

	private boolean isDeleted = false;

	private String Status = "BOOKED";

	private String remarks;

	private String cbId;
	
	private String from;
	
	private String to;
	
	private double total;
	
	private Long dispatcherId;
	
	private String consignor;
	
	private String consingee;
 
	public String getConsignor() {
		return consignor;
	}

	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}

	public String getConsingee() {
		return consingee;
	}

	public void setConsingee(String consingee) {
		this.consingee = consingee;
	}

	public Long getDispatcherId() {
		return dispatcherId;
	}

	public void setDispatcherId(Long dispatcherId) {
		this.dispatcherId = dispatcherId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getCbId() {
		return cbId;
	}

	public void setCbId(String cbId) {
		this.cbId = cbId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
}
