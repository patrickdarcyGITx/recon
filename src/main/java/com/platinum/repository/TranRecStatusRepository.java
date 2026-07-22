package com.platinum.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.platinum.model.TranRecStatus;
import com.platinum.model.Transaction;

// @Query(value = "SELECT role_name AS role, COUNT(*) AS totalCount FROM users GROUP BY role_name", nativeQuery = true)

@Repository
public interface TranRecStatusRepository extends JpaRepository<TranRecStatus, Long >{
	@Query(value = "SELECT id, card_type, card_last4, type,  reconcile_status, captured_at, gross_amount, tran_settlement_amount, set_settlement_amount"
			+ "  FROM tran_rec_status_test\r\n"
			+ " order by card_type, card_last4, type, reconcile_status, gross_amount, set_settlement_amount ", nativeQuery = true)
	List<TranRecStatus> getTranRecStatusSummary();
	              
} 
