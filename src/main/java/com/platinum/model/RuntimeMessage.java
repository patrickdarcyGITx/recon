package com.platinum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "runtime_message")
public class RuntimeMessage {

	public RuntimeMessage() {
	}

	public RuntimeMessage(long id, String reconcileMsg, String reconcileStatus)

	{
		this.id = id;
		this.reconcileMsg = reconcileMsg;
		this.reconcileStatus = reconcileStatus;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String reconcileMsg;
	private String reconcileStatus;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReconcileMsg() {
		return reconcileMsg;
	}

	public void setReconcileMsg(String reconcileMsg) {
		this.reconcileMsg = reconcileMsg;
	}

	public String getReconcileStatus() {
		return reconcileStatus;
	}

	public void setReconcileStatus(String reconcileStatus) {
		this.reconcileStatus = reconcileStatus;
	}
}
