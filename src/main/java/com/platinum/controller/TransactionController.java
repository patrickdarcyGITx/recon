package com.platinum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.platinum.model.RuntimeMessage;
import com.platinum.model.Settlement;
import com.platinum.model.Transaction; 
import com.platinum.service.DataLoaderSettlement;
import com.platinum.service.DataLoaderTransaction;
import com.platinum.service.ReconcileTransactions;

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
	
	public static List<Transaction> transactions = new ArrayList<>();
	
	public static Integer int1 = 0;
	
//	public static void main() {
//		
//
//	}
	
	@GetMapping("/transactions")
    public RuntimeMessage getAllTransactipns() throws IOException {
    	
    	System.out.println("DARCYX-getAllTransactions");
    	
    	RuntimeMessage runtimeMessage = new RuntimeMessage
				(1,"Reconcile Report","Completed Sucessfully");
    	
     
    	HashMap<String, Transaction> transactionMap;
      	transactionMap = dataLoaderTransaction.loadTables();
    	    	 	
    	HashMap<String, Settlement> settlementMap;
     	settlementMap = dataLoaderSettlement.loadTables();     	
    
     	settlementMap.forEach((key, value) -> {
    		System.out.println("DARCYX-getAllTransactipns-settlementMap -last4:" + key + " : " + value.getCard_last4());
    	});
     	
     	reconcileTransactions.balanceTransactions(settlementMap, transactionMap);
  
     	
        return runtimeMessage;
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
    	System.out.println("DARCYX-addProduct-product-name " );
    	System.out.println("DARCYX-addProduct-product-price " );
    	
//        product.setId((long) (products.size() + 1));
//        products.add(product);
//        
//        System.out.println("DARCYX-addProduct-infront-of-return  " + product.getPrice());
        return transaction;
    }
}
