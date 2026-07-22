package com.platinum.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.platinum.model.Transaction;
import com.platinum.repository.TransactionRepository;

 
@Service
public class DataLoaderTransaction {
//

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ReconcileTransactions reconcileTransactions;
	
	
	public HashMap<String, Transaction> loadTables() throws IOException {
		
		
		CsvMapper csvMapper = new CsvMapper();

//		// Define schema matching the POJO structure, using headers from the file
 		CsvSchema schema = CsvSchema.emptySchema().withHeader();
 
 	// Path to your csv file for test  -- for Production	change  /test/ to  /data/
 		InputStream csvFile = new ClassPathResource("/test/internal_transactions.csv").getInputStream();

//		// Read the values into an iterator
 		MappingIterator<Transaction> iterator = csvMapper.readerFor(Transaction.class).with(schema).readValues(csvFile);

//		// Slurp all rows into a traditional List
 		List<Transaction> transactions = iterator.readAll();
 		
 	    List<Transaction> tranactionsBad = transactions.stream()
 	    		.filter(n -> n.getMerchant_ref().contains("BAD"))
 	    		.collect(Collectors.toList());
 		
  	   for (Transaction trans : tranactionsBad) {
          
           reconcileTransactions.loadReconcileStatus(trans.getCard_last4(),trans.getCard_type(), trans.getType(), 0,
        		   "Bad Rec : " + trans.getMerchant_ref(), 0, 0, trans.getCaptured_at());                  
        }
 	    
 
 	   List<Transaction>    tranSalesGood = transactions.stream()
    // 		.filter(n -> !(n.getMerchant_ref().contains("BAD")) && n.getGross_amount() > 0)
     		.filter(n -> !(n.getMerchant_ref().contains("BAD")) )
	    		.collect(Collectors.toList());
 	   HashMap<String,Transaction> tran = new HashMap<String,Transaction>();
 	   
 	  for (Transaction trans : tranSalesGood) {
          tran.put(trans.getCard_last4() + trans.getType(), trans) ;
       }
 	  	   
 		transactionRepository.saveAll(transactions);
	 		
		System.out.println("Successfully persisted Settlement CSV data to database!");
		
		return(tran);
		
	}
	
//	public static void main(String[] args) throws IOException {
//		// TODO Auto-generated method stub
//		System.out.println("Successfully persisted Settlement CSV data to database!");
//
//	}

}
