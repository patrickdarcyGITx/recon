
1) I created a brand new project using SpringBoot and loaded into Spring Eclipse 5.2, using Postgres.sql and Java 25.

2) I ran into a problem with REACT in the front end due to security issues caused by running the front-end and the backend on different posts locally, this is not supposed to happen if you used the @CrossOrigin(origins annotation and it worked for a while and then locked up and I was unable to resolve so I went to PostMan.
@CrossOrigin(origins = "http://localhost:5173") //this was not working causing react call to be rejected 

3) There are 2 ways to run this project one is by using the jar file in the command prompt in the path that the jar file is stored, and the other is by running it out of eclipse.

   a) C:\training\Darcy-New-1-7-2026\Platinum\recon\target>java -jar recon-0.0.1-SNAPSHOT.jar

   b) Get the recon project imported into Spring eclipse and then inside the public class ReconApplication class right click and run it as 'Spring Boot App'. 
   
   note: The running of this project create the Postgresql database with the 3 tables tran_rec_status_test, 
         processor_settlement_test, internal_transactions_test.

4) I load the 2 input files into 2 tables which is very useful to help debug how the files work together. processor_settlement_test, internal_transactions_test.  (these files are currently pointing to the test files)
 
   a) class DataLoaderSettlement  File jsonFile = new File("src/main/resources/test/processor_settlement.json");
   
   b) class DataLoaderTransaction  public File csvFile = new File
   ("src/main/resources/test/internal_transactions.csv");

5) The tran_rec_status_test table is used to report on  the status of all the transactions and it allows you to get the reports in different formats since the tables are normalized you can join back to the original files to help track and debug.

6) I use PostMan /Get transaction, it is completely IDEMPOTENT every time you click it, it reloads all the files and tables from scratch.

7)  In PostMan enter the GET  http://localhost:8080/api/v1/transactions and click enter. 
    It returns the below payload everytime (idempotent) which indicates the tables have all been reloaded and reconciled.
    
    {
    "id": 1,
    "reconcileMsg": "Reconcile Report",
    "reconcileStatus": "Completed Sucessfully"
    }
8) In the code I used a lot of different coding examples to demonstrate my code knowledge, I might have done things a little unOrthodox in some cases as a result of this approach.

9) I also included my working/debug SQL queries in the folder recon\src\main\resources\SQL / platinum_balance_queries.  
 