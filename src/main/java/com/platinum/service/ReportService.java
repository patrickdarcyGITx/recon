package com.platinum.service;

import com.platinum.model.TranRecStatus;
import com.platinum.repository.TranRecStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

@Service
public class ReportService {

	@Autowired
	private final TranRecStatusRepository tranRecStatusRepository;

	public ReportService(TranRecStatusRepository tranRecStatusRepository) {
		this.tranRecStatusRepository = tranRecStatusRepository;
	}

	public void exportUsersToCsv(String filename) throws IOException {

		String userDirectory = System.getProperty("user.dir");
		
		// Ensure directories exist
		Path directoryPath = Paths.get(userDirectory +"/src/main/resources/test/");
		if (!Files.exists(directoryPath)) {
			Files.createDirectories(directoryPath);
		}

		Path filePath = directoryPath.resolve(filename + ".csv");

		// Initialize writers and CSV Printer
		try (BufferedWriter writer = Files.newBufferedWriter(filePath);

				// Write the Report Header record
				CSVPrinter csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.builder().setHeader("ID", "Card Type", "Card Last4 ", "Type ",
								"Reconcile Status", "Captured At", "Gross Amount", "Tran Set Amt", "Set Amt")
								.build())) {

			//read records from the tran_rec_status_test postgresql table 
			List<TranRecStatus> tranRecStatusList = tranRecStatusRepository.getTranRecStatusSummary();

			// Append Rows
			for (TranRecStatus trs : tranRecStatusList) {

				try {
					// write detail line to the Report
					csvPrinter.printRecord(trs.getId(), trs.getCard_type(), trs.getCard_last4(), trs.getType(),
							trs.getReconcile_status(), trs.getCaptured_at(), trs.getGross_amount(),
							trs.getTran_settlement_amount(), trs.getSet_settlement_amount());
				} catch (IOException e) {
					throw new RuntimeException("Failed to write CSV record", e);
				}
			}
		
			csvPrinter.flush();
			writer.flush();
		 
		}
	}

	public String generateCsvReport() {

		List<TranRecStatus> tranRecStatusList = tranRecStatusRepository.findAll();
		StringBuilder csvBuilder = new StringBuilder();

		// Append Header
		csvBuilder.append("ID,Card Last4,Type,Gross Amount\n"); // TODO

		// Append Rows
		for (TranRecStatus trs : tranRecStatusList) {

			System.out.println(trs.getCard_last4());
			csvBuilder.append(trs.getId()).append(",").append(trs.getCard_last4()).append(",").append(trs.getType())
					.append(",").append(trs.getGross_amount()).append("\n");
		}

		return csvBuilder.toString();
	}

	public ResponseEntity<byte[]> downloadProductReport() {
		String csvData = generateCsvReport();
		byte[] outputBuffer = csvData.getBytes();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"src/main/resources/test/StatusTransactions_report.csv")
				.contentType(MediaType.parseMediaType("text/csv")).contentLength(outputBuffer.length)
				.body(outputBuffer);
	}

}
