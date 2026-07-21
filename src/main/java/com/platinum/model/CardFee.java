package com.platinum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table; 

@Entity
//@Table(name = "Card_Fee")
@Table(name = "card_fee")  //prod
public class CardFee {
	
	public CardFee() {}
	  
	public CardFee
	  (long id,double visa_percent, double visa_flat, double master_percent, double master_flat, double amex_percent, double amex_flat,
			  double discover_percent, double discover_flat, double markup_percent, double markup_flat )
	  
	  {	 this.id = id;
	     this.visa_percent = visa_percent;
	     this.visa_flat = visa_flat;   
	     this.master_percent = master_percent;
	     this.master_flat = master_flat;    
	     this.amex_percent = amex_percent;
	     this.amex_flat = amex_flat;  
	     this.discover_percent = discover_percent;    
	     this.discover_flat = discover_flat;   
	     this.markup_percent = markup_percent;   
	     this.markup_flat = markup_flat; 
	  }
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private double visa_percent;    
	    private double visa_flat;    
	    private double master_percent;    
	    private double master_flat;    
	    private double amex_percent;    
	    private double amex_flat;   
	    private double discover_percent;    
	    private double discover_flat;   
	    private double markup_percent;   
	    private double markup_flat;
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public double getVisa_percent() {
			return visa_percent;
		}
		public void setVisa_percent(double visa_percent) {
			this.visa_percent = visa_percent;
		}
		public double getVisa_flat() {
			return visa_flat;
		}
		public void setVisa_flat(double visa_flat) {
			this.visa_flat = visa_flat;
		}
		public double getMaster_percent() {
			return master_percent;
		}
		public void setMaster_percent(double master_percent) {
			this.master_percent = master_percent;
		}
		public double getMaster_flat() {
			return master_flat;
		}
		public void setMaster_flat(double master_flat) {
			this.master_flat = master_flat;
		}
		public double getAmex_percent() {
			return amex_percent;
		}
		public void setAmex_percent(double amex_percent) {
			this.amex_percent = amex_percent;
		}
		public double getAmex_flat() {
			return amex_flat;
		}
		public void setAmex_flat(double amex_flat) {
			this.amex_flat = amex_flat;
		}
		public double getDiscover_percent() {
			return discover_percent;
		}
		public void setDiscover_percent(double discover_percent) {
			this.discover_percent = discover_percent;
		}
		public double getDiscover_flat() {
			return discover_flat;
		}
		public void setDiscover_flat(double discover_flat) {
			this.discover_flat = discover_flat;
		}
		public double getMarkup_percent() {
			return markup_percent;
		}
		public void setMarkup_percent(double markup_percent) {
			this.markup_percent = markup_percent;
		}
		public double getMarkup_flat() {
			return markup_flat;
		}
		public void setMarkup_flat(double markup_flat) {
			this.markup_flat = markup_flat;
		} 
}