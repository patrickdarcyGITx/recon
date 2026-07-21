package com.platinum.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.*;

@Entity
@Table(name = "tran_rec_status_test")
public class TranRecStatus {

	public TranRecStatus() {}
	  
	public TranRecStatus
	  (String card_last4, String type, double gross_amount,String reconcile_status,
			  double settlement_amount, double set_settlement_amount, Date captured_at)
	  
	  {
		//this.id = id; Long id, 
		this.card_last4 = card_last4;
	   	this.type = type;
    	this.gross_amount = gross_amount;
   	   	this.reconcile_status = reconcile_status;
	   	this.tran_settlement_amount  =  settlement_amount;
	   	this.set_settlement_amount  =  set_settlement_amount;
		this.captured_at = captured_at;	
	  }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String card_last4;
	private String type;
	// this also works for a CSV file to remove "N/A" in double field
	@JsonDeserialize(using = CustomDoubleDeserializer.class)
	private double gross_amount; // BigDecimal
	private Date captured_at;
	private String reconcile_status;
   	private double tran_settlement_amount;
   	private double set_settlement_amount;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCard_last4() {
		return card_last4;
	}

	public void setCard_last4(String card_last4) {
		this.card_last4 = card_last4;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getGross_amount() {
		return gross_amount;
	}

	public void setGross_amount(double gross_amount) {
		this.gross_amount = gross_amount;
	}

	public Date getCaptured_at() {
		return captured_at;
	}

	public void setCaptured_at(Date captured_at) {
		this.captured_at = captured_at;
	}

	public String getReconcile_status() {
		return reconcile_status;
	}

	public void setReconcile_status(String reconcile_status) {
		this.reconcile_status = reconcile_status;
	}

	public double getTran_settlement_amount() {
		return tran_settlement_amount;
	}

	public void setTran_settlement_amount(double tran_settlement_amount) {
		this.tran_settlement_amount = tran_settlement_amount;
	}

	public double getSet_settlement_amount() {
		return set_settlement_amount;
	}

	public void setSet_settlement_amount(double set_settlement_amount) {
		this.set_settlement_amount = set_settlement_amount;
	}

}
