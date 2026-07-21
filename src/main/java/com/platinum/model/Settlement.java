package com.platinum.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
//@Table(name = "processor_settlement_test")
@Table(name = "processor_settlement_test")  //prod
public class Settlement {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String network_ref;
	    private String merchant_ref;
	    private String merchant_id;
	    private String card_last4;
	    private String card_type;
	    private double settled_amount;    //BigDecimal
	    private double interchange_fee;    //BigDecimal
	    private double processor_fee;    //BigDecimal
	    private String currency;
	    private Date settlement_date;   //??????  double check this  here and sql boat
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNetwork_ref() {
			return network_ref;
		}
		public void setNetwork_ref(String network_ref) {
			this.network_ref = network_ref;
		}
		public String getMerchant_ref() {
			return merchant_ref;
		}
		public void setMerchant_ref(String merchant_ref) {
			this.merchant_ref = merchant_ref;
		}
		public String getMerchant_id() {
			return merchant_id;
		}
		public void setMerchant_id(String merchant_id) {
			this.merchant_id = merchant_id;
		}
		public String getCard_last4() {
			return card_last4;
		}
		public void setCard_last4(String card_last4) {
			this.card_last4 = card_last4;
		}
		public String getCard_type() {
			return card_type;
		}
		public void setCard_type(String card_type) {
			this.card_type = card_type;
		}
		public double getSettled_amount() {
			return settled_amount;
		}
		public void setSettled_amount(double settled_amount) {
			this.settled_amount = settled_amount;
		}
		public double getInterchange_fee() {
			return interchange_fee;
		}
		public void setInterchange_fee(double interchange_fee) {
			this.interchange_fee = interchange_fee;
		}
		public double getProcessor_fee() {
			return processor_fee;
		}
		public void setProcessor_fee(double processor_fee) {
			this.processor_fee = processor_fee;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public Date getSettlement_date() {
			return settlement_date;
		}
		public void setSettlement_date(Date settlement_date) {
			this.settlement_date = settlement_date;
		}
	    
}
