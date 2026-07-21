
select itt.gross_amount, pst.settled_amount, itt.*,
        

select pst.*		
  from processor_settlement_test pst

-- this returns 3 rows out of 18 total
select itt.*
   from internal_transactions_test itt
  where itt.merchant_ref like '%BAD%'  

--this returns 2 rows out of 19 total 
select pst.*
   from processor_settlement_test pst 
  where pst.merchant_ref like '%BAD%'  

		
--select sum(itt.gross_amount)
select itt.gross_amount, pst.settled_amount, itt.card_type, pst.interchange_fee, pst.processor_fee
   from internal_transactions_test itt,
        processor_settlement_test pst 
  where itt.card_last4 =  pst.card_last4


-- 92.63   79.49	13.14
 
  select   sum(pst.interchange_fee) + sum(pst.processor_fee)
--  select pst.*
  from  processor_settlement_test pst 
  where pst.merchant_ref not like '%BAD%'
    and pst.card_type = 'VISA'

--this code is the valid fee  72.930400
  select  itt.merchant_id, itt.merchant_ref, itt.gross_amount, (itt.gross_amount * .0180) + 0.10, itt.*
--  select  sum ( (itt.gross_amount * .0180) + 0.10 )
    from internal_transactions_test itt
   where itt.tran_type = 'SALE'	
     and itt.card_type = 'VISA'  --  ('DISCOVER','MASTERCARD','VISA')
	 and itt.merchant_ref not like '%BAD%'   





  delete from processor_settlement_test where id = 3  -- 4.32	
   

select itt.*
  from internal_transactions_test itt

select  sum(pst.processor_fee)   --pst.processor_fee --
    from processor_settlement_test pst
   where pst.merchant_ref not like '%BAD%';


--this code is the valid fee  72.930400
  select   itt.gross_amount, (itt.gross_amount * .0180) + 0.10
--  select  sum ( (itt.gross_amount * .0180) + 0.10 )
    from internal_transactions_test itt
   where itt.tran_type = 'SALE'	
     and itt.card_type = 'VISA'  --  ('DISCOVER','MASTERCARD','VISA')
	 and itt.merchant_ref not like '%BAD%'   
 
--this code is the valid fee  12.0974   '2285' = 12.0974
 -- select   itt.gross_amount, (itt.gross_amount * .0180) -- + 0.10
  select   ( (itt.gross_amount * .02 ) + 0.10 )
    from internal_transactions_test itt
   where itt.type = 'SALE'	
     and itt.card_type = 'DISCOVER'  --  ('DISCOVER','MASTERCARD','VISA')
	 and itt.card_last4 = '2285'
	 and itt.merchant_ref not like '%BAD%'   


--13.95   12.0974  '2285' =  13.95  
   select   sum(pst.interchange_fee) + sum(pst.processor_fee)
  from  processor_settlement_test pst 
  where pst.merchant_ref not like '%BAD%'
    and pst.card_type = 'DISCOVER' 
	and pst.card_last4 = '2285'
	 

  --there are no AMEX records 
   -- select   itt.gross_amount, (itt.gross_amount * .0180) + 0.10
  select  sum ( (itt.gross_amount * .025) + 0.15 )
    from internal_transactions_test itt
   where itt.tran_type = 'SALE'	
     and itt.card_type = 'AMEX'  --  ('DISCOVER','MASTERCARD','VISA')
	 and itt.merchant_ref not like '%BAD%'   	 

-- fee 42.037550   CEIL(column_name × 200) ÷ 200.0    1241 =  6.7804   343.72  1241-  gross-amout 351.6
    select  sum(itt.gross_amount)  - sum ( (itt.gross_amount * .0190) + 0.10 )
--both fees 7.8852	
	select sum(itt.gross_amount) 
--	   - CEIL((sum ( (itt.gross_amount * .0190) + 0.10 ) +  sum ( (itt.gross_amount * .0030) + 0.05 ) ) * 200) / 200
        - (sum ( (itt.gross_amount * .0190) + 0.10 ) +  sum ( (itt.gross_amount * .0030) + 0.05 ) ) 
--	select  sum ( (itt.gross_amount * .0030) + 0.05 )
 --select itt.gross_amount
    from internal_transactions_test itt
   where itt.type = 'SALE'	
     and itt.card_type = 'MASTERCARD'  --  ('DISCOVER','MASTERCARD','VISA','AMEX')
	 and itt.merchant_ref not like '%BAD%'  
	 and   itt.card_last4 = '1241'

--45.16   1241 - settled-amount 343.72   6.78  1.1  (7.88)   7.880000000000001
  select   sum(pst.interchange_fee) + sum(pst.processor_fee), sum(pst.settled_amount)
  -- select (pst.interchange_fee) ,(pst.processor_fee)
  from  processor_settlement_test pst 
  where pst.merchant_ref not like '%BAD%'
    and pst.card_type = 'MASTERCARD' 
   and  pst.card_last4 = '1241'
   and pst.settled_amount > 0



-- fee  21.012360
    select  sum ( (itt.gross_amount * .0030) + 0.05 )
    from internal_transactions_test itt
   where itt.tran_type = 'SALE'	
     and itt.card_type in ('DISCOVER','MASTERCARD','VISA','AMEX')  -- dont forget to do the AMEX for the above
	 and itt.merchant_ref not like '%BAD%'   	

--this code is valid settlement rows 
  select sum(pst.settled_amount)
    from processor_settlement_test pst
   where pst.merchant_ref not like '%BAD%';

--this code is the valid gross 
  select   sum(itt.gross_amount)
    from internal_transactions_test itt
   where itt.tran_type = 'SALE'	
   --  and itt.card_type in ('DISCOVER','MASTERCARD','VISA')
	 and itt.merchant_ref not like '%BAD%'

--this is valid Refund
  select   sum(itt.gross_amount)
    from internal_transactions_test itt
   where itt.tran_type = 'REFUND'	
   --  and itt.card_type in ('DISCOVER','MASTERCARD','VISA')
	 and itt.merchant_ref not like '%BAD%'

--this is ?????????????????????????
  select   sum(itt.gross_amount)
    from internal_transactions_test itt
   where itt.tran_type in ('REFUND','SALE')	
   --  and itt.card_type in ('DISCOVER','MASTERCARD','VISA')
	 and itt.merchant_ref not like '%BAD%'

    select  distinct merchant_ref
    from internal_transactions_test itt

	 select     itt.card_last4, itt.* --count(*)
    from internal_transactions_test itt
	where itt.card_last4 = '1241' --'2285'
	group by  itt.card_last4

  select   itt.type, itt.*, pst.* 
     from  processor_settlement_test pst,
	       internal_transactions_test itt
    where pst.card_last4 = '0811' 	 
	  and pst.card_last4 = itt.card_last4
	  and itt.type = 'SALE'
	  and pst.settled_amount > 0

 select   pst.* 
     from  processor_settlement_test pst 
    where pst.card_last4 = '0811' 	 

	  select itt.* 
     from   internal_transactions_test itt
    where itt.card_last4 = '0811' 	 
	   

	  

     select    pst.merchant_id, pst.card_type, pst.card_last4, count(*)  
    from  processor_settlement_test pst 
	where pst.settled_amount > 0
--	order by pst.merchant_id, pst.card_type, pst.card_last4
	group by  pst.merchant_id, pst.card_type, pst.card_last4

   select pst.*
     from processor_settlement_test pst
	  where pst.merchant_ref not like '%BAD%' 
	    and pst.settle_amount > 0
	 order by pst.card_last4
	 
  select  itt.* -- sum(itt.gross_amount)
    from internal_transactions_test itt
  where itt.merchant_ref not like '%BAD%'
	order by itt.card_last4, itt.merchant_ref, itt.card_type
	
--   where itt.tran_type = 'SALE'	
   select  itt.*
     from internal_transactions_test itt
   where itt.merchant_ref  like '%BAD%'      itt.card_type in ('DISCOVER','MASTERCARD','VISA')
	 and itt.merchant_ref not like '%BAD%'

	 