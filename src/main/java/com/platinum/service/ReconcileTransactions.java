package com.platinum.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.platinum.model.CardFee;
import com.platinum.model.Settlement;
import com.platinum.model.TranRecStatus;
import com.platinum.model.Transaction;
import com.platinum.repository.TranRecStatusRepository;

@Service
public class ReconcileTransactions {

	@Autowired
	private TranRecStatusRepository tranRecStatusRepository;

	TranRecStatus trsRec;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public void balanceTransactions(HashMap<String, Settlement> settlementMap,
			HashMap<String, Transaction> transactionMap) throws StreamReadException, DatabindException, IOException {

		// I had problems with this fee_schedule JSON file and since it was really just
		// one record I loaded by hand due to time constraints TODO figure this out
		// later

		// these numbers came from the src/main/resources/test/fee_schedule.json file
		// within this project
		CardFee cf = new CardFee(1, 0.018, 0.10, 0.019, 0.10, 0.025, 0.15, 0.020, 0.10, 0.003, 0.05);

		HashMap<String, Double> feePercent = new HashMap<String, Double>();
		HashMap<String, Double> feeFlat = new HashMap<String, Double>();

		feePercent.put("VISA", cf.getVisa_percent());
		feePercent.put("MASTERCARD", cf.getMaster_percent());
		feePercent.put("AMEX", cf.getAmex_percent());
		feePercent.put("DISCOVER", cf.getDiscover_percent());
		feePercent.put("MARKUP", cf.getMarkup_percent());

		feeFlat.put("VISA", cf.getVisa_flat());
		feeFlat.put("MASTERCARD", cf.getMaster_flat());
		feeFlat.put("AMEX", cf.getAmex_flat());
		feeFlat.put("DISCOVER", cf.getDiscover_flat());
		feeFlat.put("MARKUP", cf.getMarkup_flat());

		transactionMap.forEach((key, value) -> {

			//validate that Transaction record has at least one Settlement record
			List<String> matchingKeys = settlementMap.keySet().stream()
					.filter(keyx -> keyx != null && keyx.startsWith(value.getCard_last4())).toList(); //
		
			System.out.println("balanceTransactions-matchingkeys : " + matchingKeys);
			
			if (matchingKeys.isEmpty()) {
				try {
					loadReconcileStatus(value.getCard_last4(),value.getCard_type(),value.getType(), value.getGross_amount(),
							"Missing Setttlement Match", 0.00, 0.00, value.getCaptured_at());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (value.getType().equals("SALE")) {
				double settleAmount = 0.0;

				settleAmount = value.getGross_amount() - (value.getGross_amount() * feePercent.get(value.getCard_type())
						+ feeFlat.get(value.getCard_type()) + (value.getGross_amount() * feePercent.get("MARKUP"))
						+ feeFlat.get("MARKUP"));

				// Iterating using for loop
				for (int i = 0; i < matchingKeys.size(); i++) {

					// Printing and display the elements in ArrayList
					System.out.println("Settlement-key : " + matchingKeys.get(i) + " i:  " + i);

					Settlement sd = settlementMap.get(matchingKeys.get(i));

					System.out.println("Settlement-key-sd-getCard_Last4 : " + sd.getCard_last4());
					System.out.println("Settlement-sd.getSettled_amount(): " + sd.getSettled_amount());

					// do not process negative numbers indicates a refund
					if (sd.getSettled_amount() > 0) {

						// TODO the below code needs to be cleaned up and put into a lambda boolean
						// function
						int diff = (int) (settleAmount - sd.getSettled_amount());

						String status;

						// I allow for 2 for rounding
						if (diff < 3 && diff >= 0) {

							status = "Transact Settlment Balanced";
						} else {
							status = "Transact Settlment DId NOT Balanced";
						}

						try {
							loadReconcileStatus(value.getCard_last4(),value.getCard_type(), value.getType(), value.getGross_amount(), status,
									settleAmount, sd.getSettled_amount(), value.getCaptured_at());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("This is the  REFUND CODE : "); 

				// Iterating using for loop
				for (int i = 0; i < matchingKeys.size(); i++) {

					// Printing and display the elements in ArrayList
					System.out.println("Settlement-key : " + matchingKeys.get(i) + " i:  " + i);

					Settlement sd = settlementMap.get(matchingKeys.get(i));
//
//					System.out.println("Settlement-key-sd-getCard_Last4 : " + sd.getCard_last4());
//					System.out.println("Settlement-sd.getSettled_amount(): " + sd.getSettled_amount());

					// negative numbers indicates a refund
					if (sd.getSettled_amount() < 0) {

						String status;

						// these should be a negative number match exactly with no fees
						if (value.getGross_amount() == sd.getSettled_amount()) {

							status = "Transact Refund Settlement Balanced";
						} else {
							status = "Transact Refund Settlment DID NOT Balance";
						}

						try {
							loadReconcileStatus(value.getCard_last4(),value.getCard_type(), value.getType(), value.getGross_amount(), status,
									0.0, sd.getSettled_amount(), value.getCaptured_at());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		});

	}

	public void loadReconcileStatus(String cardLast4, String card_type, String type, double grossAmt, String reconcileStatus,
			double settlementAmount, double setSettlementAmount, java.util.Date date)
			throws StreamReadException, DatabindException, IOException {

		LocalDate localDate = LocalDate.now();

		trsRec = new TranRecStatus(cardLast4, card_type, type, grossAmt, reconcileStatus, settlementAmount, setSettlementAmount,
				date);

		tranRecStatusRepository.save(trsRec);

	}
}
