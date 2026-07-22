package com.platinum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.platinum.model.RuntimeMessage;
import com.platinum.model.Settlement;
import com.platinum.model.Transaction; 
import com.platinum.service.DataLoaderSettlement;
import com.platinum.service.DataLoaderTransaction;
import com.platinum.service.ReconcileTransactions;
import com.platinum.service.ReportService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173") //this was not working causing react call to be rejected 
public class TransactionController  {
 
	@Autowired
	public DataLoaderTransaction dataLoaderTransaction;
	
	@Autowired
	public DataLoaderSettlement dataLoaderSettlement;
	
	@Autowired
	ReconcileTransactions reconcileTransactions;
	
	@Autowired
	ReportService reportService;
	
	public static List<Transaction> transactions = new ArrayList<>();
	
	public static Integer int1 = 0;
	
//	public static void main() {
//		
//
//	}
	
	@GetMapping("/transactions")
    public RuntimeMessage getAllTransactipns() throws IOException {
    	
    		
    	RuntimeMessage runtimeMessage = new RuntimeMessage
				(1,"Reconcile Report: \\recon\\src\\main\\resources\\test\\RECONCILE_All_TRANSACTIONS_REPORT.csv  or target/Jarfile-execution-:\\recon\\target\\src\\main\\resources\\test\\RECONCILE_All_TRANSACTIONS_REPORT.csv ",
				 "Completed Sucessfully,  now if you want to stop the RestFul web service enter command line >curl -X POST http://localhost:8080/actuator/shutdown");
    	
     
    	HashMap<String, Transaction> transactionMap;
    	transactionMap = dataLoaderTransaction.loadTables();
    	    	 	
    	HashMap<String, Settlement> settlementMap;
     	settlementMap = dataLoaderSettlement.loadTables();     	
    
     	settlementMap.forEach((key, value) -> {
    		System.out.println("-getAllTransactipns-settlementMap -last4:" + key + " : " + value.getCard_last4());
    	});
     	
    	reconcileTransactions.balanceTransactions(settlementMap, transactionMap);
     	
     	// Create a CSV file of all the Transactions Reconciled, this allows all types of Summary looks
     	reportService.exportUsersToCsv("RECONCILE_All_TRANSACTIONS_REPORT");
     	
     //	return reportService.downloadProductReport();    
     	
     
     	 return runtimeMessage;
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
    	System.out.println(" addTransaction This is a place marker TODO later " );  //TODO later if need be
    	 
    	return transaction;
    }
}
