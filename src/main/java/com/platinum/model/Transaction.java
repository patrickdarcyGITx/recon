package com.platinum.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.*;

@Entity
@Table(name = "internal_transactions_test")
public class Transaction {

	public Transaction() {}
	  
	public Transaction
	  (long id,String internal_txn_id, String  merchant_id,String  merchant_ref,
	   String card_type, String card_last4, double gross_amount,String currency, String type,Date captured_at )
	  
	  {	this.id = id;
	    this.internal_txn_id = internal_txn_id;
	   	this.merchant_id = merchant_id;
	   	this.merchant_ref = merchant_ref;
	   	this.card_type = card_type;
	   	this.card_last4 = card_last4;
    	this.gross_amount = gross_amount;
    	this.currency = currency;
	   	this.type = type;
	   	this.captured_at = captured_at;			 
	  }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String internal_txn_id;
	private String merchant_id;
	private String merchant_ref;
	private String card_type;
	private String card_last4;
	// this also works for a CSV file to remove "N/A" in double field
	@JsonDeserialize(using = CustomDoubleDeserializer.class)
	private double gross_amount; // BigDecimal
	private String currency;
	private String type;
	private Date captured_at;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInternal_txn_id() {
		return internal_txn_id;
	}

	public void setInternal_txn_id(String internal_txn_id) {
		this.internal_txn_id = internal_txn_id;
	}

	public String getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getMerchant_ref() {
		return merchant_ref;
	}

	public void setMerchant_ref(String merchant_ref) {
		this.merchant_ref = merchant_ref;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_last4() {
		return card_last4;
	}

	public void setCard_last4(String card_last4) {
		this.card_last4 = card_last4;
	}

	public double getGross_amount() {
		return gross_amount;
	}

	public void setGross_amount(double gross_amount) {
		this.gross_amount = gross_amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getType() {
		return type;
	}

	public void setTran_type(String type) {
		this.type = type;
	}

	public Date getCaptured_at() {
		return captured_at;
	}

	public void setCaptured_at(Date captured_at) {
		this.captured_at = captured_at;
	}

}
