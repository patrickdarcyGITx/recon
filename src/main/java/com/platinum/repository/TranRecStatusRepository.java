package com.platinum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platinum.model.TranRecStatus;
import com.platinum.model.Transaction;

@Repository
public interface TranRecStatusRepository extends JpaRepository<Transaction, Long >{

	void save(TranRecStatus trsRec);
	              
} 