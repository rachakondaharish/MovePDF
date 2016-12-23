import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jaxb.BranchDocument;
import jaxb.BranchDocument.IndexingInfo;
import jaxb.BranchDocument.IndexingInfo.ContentMetadata;
import jaxb.BranchDocument.IndexingInfo.ContentMetadata.Metadata;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import sun.rmi.runtime.Log;

import com.ej.entities.AccountInfo;
import com.ej.utils.Utilities;

public class ProcessController {
	public static int errorCount = 0;
	static Utilities util = new Utilities();
	static String datFile = PropertiesUtil.getPropertyValue("BASE-FILENAME");
	static String folderLocation = PropertiesUtil.getPropertyValue("BASE-FOLDER");
	static String outputFolderLocation = PropertiesUtil.getPropertyValue("DESTINATION_FOLDER");
	static Date stDate = util.getStartDate(PropertiesUtil.getPropertyValue("PROCESSING-START-TIME"));
	static Date endDate = util.getEndDate(PropertiesUtil.getPropertyValue("PROCESSING-STOP-TIME"));
	static int touchFileCoutnt = Integer.parseInt(PropertiesUtil.getPropertyValue("START-WHEN-TOUCH-FILES-THEN"));
	static int sleepTime = Integer.parseInt(PropertiesUtil.getPropertyValue("PAUSE-TIME-SEC")) * 1000;
	static int restartCheckCnt = Integer.parseInt(PropertiesUtil.getPropertyValue("RESTART_CHECK_CNT"));
	static int errorCntGreaterThen = Integer.parseInt(PropertiesUtil.getPropertyValue("ABEND-WHEN-ERROR-CNT-GREATER-THEN"));
	static String lastProcessedRecord = "";
	static int failedRecordCound = 0;
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	static Date today = new Date();
	static String todayDate = dateFormat.format(today);

	private static Logger log = Logger.getLogger(ProcessController.class);
	private static BufferedReader restartReader;
	// loading the log4j properties
	static {
		DOMConfigurator.configure("src/main/resources/Logger_log4j.xml");
	}

	/**
	 * @param args1
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		String datFileSuffex = "";
		String destLocation = "";

		destLocation = args[0];
		try {
			datFileSuffex = args[1];
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		String datFile1 = folderLocation + datFile + datFileSuffex;
		System.out.println(datFile1);
		process(destLocation, datFile1);

	}

	/**
	 * 
	 * @param datFile
	 * @param dateFile
	 * @param outputfile
	 * @throws IOException
	 */
	public static void process(String appId, String datFile) throws IOException {

		try {
			System.out.println("Process started...");
			GregorianCalendar curCal = new GregorianCalendar();
			Date currDate = curCal.getTime();
			// checking current time is greater than the start time and less
			// than the end time
			if (currDate.after(stDate) && currDate.before(endDate)) {
				// checking if any unprocessed records
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date today = new Date();
				String fileDate = sdf.format(today);
				restartReader = new BufferedReader(new FileReader(datFile + ".restart"));
				String record = restartReader.readLine();
				restartReader.close();

				// Restart file has record
				if (null != record) {
					log.info("Restart File Found with Record :" + record);
					if (new File(datFile + ".unprocessed").exists()) {
						log.debug("##### restart unprocessed.");
						processRecords(appId, datFile , fileDate, record, ".unprocessed");
						
						
					} else {
						log.debug("##### restart dat.");
						processRecords(appId, datFile , fileDate, record, ".dat");
					}

					// Restart file is empty
				} else {
					log.debug("restart empty");
					if (new File(datFile + ".unprocessed").exists()) {
						log.debug("##### Normal unprocessed.");
						processRecords(appId, datFile , fileDate, null, ".unprocessed");
						
					}
					log.debug("##### Normal dat.");
					processRecords(appId, datFile, fileDate, null, ".dat");
				}

			} else {
				System.out.println("Not in Time Range.. Current Time :" + currDate + "Start Time :" + stDate + "End Time :" + endDate);
				// Start/Stop Flag Y
				if (new File(folderLocation + "statementArchive1.stop").exists()) {
					log.info("Restart Flag Found(" + folderLocation + "statementArchive1.stop). Moving records to Unprocessed");

				} else if (new File(datFile + ".restart").exists()) {
					log.info("Restart File Found. Move records to Unprocessed");
					moveRecordsToUnprocessed(datFile);

				}
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * This method process records from statementArchive<#>.dat
	 * 
	 * @param outputfile
	 */
	public static void processRecords(String appId, String datFile, String fileDate, String record, String fileFormat) {
		

		Boolean unprocessedFileWriteFlag = true;
		PrintWriter unprocessedFilePrintWriter = null;
		int unprocessedFileRecordCount = 0;
		failedRecordCound= 0;
		log.info("Processing :" + datFile);
		Boolean restartFlag = true;
		BufferedReader br = null;
		String strLine = "";
		PrintWriter errorPrintWriter = null;
		try {
			br = new BufferedReader(new FileReader(datFile+fileFormat));
			int recordCount = 0;
			String header = br.readLine();
			log.debug("header :" + header);
			int trailerCount = 0;
			if (util.cheakHeader(header, todayDate)) {
				while ((strLine = br.readLine()) != null) {
					Date currDate1 = new Date();
					//Moving records to unprocessed after end time
					if(!currDate1.before(endDate) ){
						unprocessedFileRecordCount++;
						if(unprocessedFileWriteFlag){
							log.info("Moving records to unprocessed after end time.");
							unprocessedFilePrintWriter =new PrintWriter(datFile+".unprocessed");
							unprocessedFilePrintWriter.write("HDR|" + todayDate + "|\n");
							unprocessedFileWriteFlag = false;
						}
						if(strLine.contains("TRL")){
							unprocessedFilePrintWriter.write("TRL|" + (unprocessedFileRecordCount-1) + "|\n");
							unprocessedFilePrintWriter.close();
							break;
						}
						unprocessedFilePrintWriter.write(strLine+"\n");
						continue;
						
					}
					
					
					
					
					
					// Skip last proccessed record
					if (null != record && strLine.equals(record)) {
						restartFlag = false;
						continue;
					}

					// Skip until last proccessed record
					if (restartFlag && null != record)
						continue;

					System.out.println("Processing " + strLine);

					// checking 5times the no of touch files. if it is more than
					// exp then stop 1 min
					if (!util.checkTouchFileCount(outputFolderLocation, appId, touchFileCoutnt, sleepTime))
						break;

					if (strLine.startsWith("TRL")) {
						trailerCount = util.getTrailerCount(strLine);
						
						if(datFile.contains(".unprocessed")){
						new File(datFile).delete();
						log.info("Deleted :" + datFile + ".unprocessed");
						}
						
						log.info("Terminated program: Trailer Count(" + trailerCount + ") and records cound(" + recordCount + ") match:"
								+ (recordCount == trailerCount));
						break;
					}
					recordCount++;
					log.debug("failedRecordCound:" + failedRecordCound + " records_Processed :" + recordCount + " restart_Flag :"
							+ new File(folderLocation + "statementArchive1.stop").exists());
					
					// checking for flag file to stop process for every 100
					// records
					if (currDate1.before(endDate) && recordCount % restartCheckCnt == 0
							&& new File(folderLocation + "statementArchive1.stop").exists()) {
						PrintWriter restartPrintWriter = new PrintWriter(datFile+".restart");
						restartPrintWriter.write(lastProcessedRecord);
						restartPrintWriter.close();
						log.info("Program Terminated because Start/Stop Flag.. Record added to restart file..");
						break;

					} else {
						BranchDocument brnDoc = (BranchDocument) util.split(strLine).get(1);
						AccountInfo accInfo = (AccountInfo) util.split(strLine).get(0);
						File orginalFile = new File(accInfo.getFILEPATH() + "/" + accInfo.getFILENAME());
						if (null != accInfo && orginalFile.exists() && orginalFile.isFile()) {
							System.out.println("lastProcessedRecord" + strLine);
							lastProcessedRecord = strLine;
							XmlConvertion.creatXml(brnDoc, outputFolderLocation, accInfo, appId, fileDate);
							PDFMoverProcess.movePDF(orginalFile, accInfo, outputFolderLocation, appId, fileDate);
						} else {
							failedRecordCound++;
							if (failedRecordCound == 1){
								errorPrintWriter = new PrintWriter(datFile+".error");
								errorPrintWriter.write("HDR|" + todayDate + "|\n");
							}
								errorPrintWriter.write(strLine + "\n");
						}
					}

					if (failedRecordCound == errorCntGreaterThen) {
						PrintWriter restartPrintWriter = new PrintWriter(datFile+".restart");
						restartPrintWriter.write(lastProcessedRecord);
						restartPrintWriter.close();
						log.debug("Program Terminated because Error Count:" + errorCntGreaterThen
								+ ". Last processed Record added to restart file..");
						break;
					}	
				}
				br.close();

			} else {
				log.debug("File is Header date doesn't match with today date");
			}
		} catch (FileNotFoundException e) {
			errorCount++;
			log.error("Unable to find the file: fileName " + e.getMessage());
		} catch (IOException e) {
			errorCount++;
			log.error("Unable to read the file: fileName  " + e.getMessage());
		} finally {
			if (null != errorPrintWriter){
				errorPrintWriter.write("TRL|" + failedRecordCound + "|\n");
				errorPrintWriter.close();
			}

		}
	}

	/**
	 * This method process records from statementArchive<#>.dat
	 * 
	 * @param outputfile
	 */
	public static void moveRecordsToUnprocessed(String datFile) {

		try {
			restartReader = new BufferedReader(new FileReader(datFile + ".restart"));
			String record = restartReader.readLine();
			restartReader.close();
			BufferedReader br = new BufferedReader(new FileReader(datFile + ".dat"));
			String strLine = "";
			PrintWriter unProcessedPrintWriter = new PrintWriter(datFile + ".unprocessed");
			Boolean pushFlag = false;
			int count = 0;
			while ((strLine = br.readLine()) != null) {
				if (strLine.startsWith("HDR")) {
					unProcessedPrintWriter.write("HDR|" + todayDate + "|\n");
					continue;
				}
				if (strLine.startsWith("TRL")) {
					unProcessedPrintWriter.write("TRL|" + count + "|\n");
					break;
				}

				if (record.equals(strLine)) {
					pushFlag = true;
					continue;
				}
				if (pushFlag) {
					count++;
					unProcessedPrintWriter.write(strLine + "\n");
				}
			}
			unProcessedPrintWriter.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}