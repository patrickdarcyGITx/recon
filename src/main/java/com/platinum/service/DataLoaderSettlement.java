package com.platinum.service;

import java.io.File;
import java.io.IOException;
 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.csv.CsvMapper;
//import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.platinum.model.Settlement;
import com.platinum.model.Transaction;
import com.platinum.repository.SettlementRepository;

@Service
public class DataLoaderSettlement {

	@Autowired
	private  SettlementRepository settlementRepository;
	
	@Autowired
	private ReconcileTransactions reconcileTransactions;
	
	public HashMap<String,Settlement> loadTables() throws IOException {

		// Path to your JSON file
//	File jsonFile = new File("src/main/resources/test/processor_settlement.json");
//Production	
		File jsonFile = new File("src/main/resources/test/processor_settlement.json");

		// there was a bug where it could not find ObjectMapper so I did a kludge
		final ObjectMapper objectMapper = new ObjectMapper();

		// Parse JSON array directly into a List of Product entities
		List<Settlement> settlements = objectMapper.readValue(jsonFile, new TypeReference<List<Settlement>>() {
		});
		

 	    List<Settlement> settlementsBad = settlements.stream()
 	    		.filter(n -> n.getMerchant_ref().contains("BAD"))
 	    		.collect(Collectors.toList());
 		
 	    settlementsBad.forEach(x -> System.out.println("foreach : " + x.getMerchant_ref()));
 	    
 	   Date currentDate = new Date();
 	    
 	   for (Settlement settl : settlementsBad) {
              reconcileTransactions.loadReconcileStatus(settl.getCard_last4(), "Settlement", 0,
        		   "Bad Rec : " + settl.getMerchant_ref(), 0, 0, currentDate);                  
        }
 	    
 
 	   List<Settlement>    settlementsGood = settlements.stream()
     		.filter(n -> !(n.getMerchant_ref().contains("BAD")) )
	    		.collect(Collectors.toList());

		  HashMap<String,Settlement> settlementMap = new HashMap<String,Settlement>();
	 	   
		  // settlements card_last4 needs another field in HashMap to avoid duplicates and getting overwritten 
	 	  for (Settlement settlement : settlementsGood) {
	        
	          settlementMap.put(settlement.getCard_last4() + settlement.getNetwork_ref(),settlement); 
	      }
		
		settlementRepository.saveAll(settlements);
		System.out.println("Successfully persisted Settlement JSON data to database!");
		
		settlementMap.forEach((key, value) -> {
			System.out.println(key + " : " + value.getCard_last4());
		});
		
		return(settlementMap);

	}
}
