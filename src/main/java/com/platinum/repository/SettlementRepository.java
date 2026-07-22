package com.platinum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platinum.model.Settlement;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long >{

	//void saveAll(List<Settlement> settlements);

}

 