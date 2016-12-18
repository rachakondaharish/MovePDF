import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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


public class ProcessController {
	public static int errorCount = 0;

	private static Logger log = Logger.getLogger(ProcessController.class);
	// loading the log4j properties
	static {
		DOMConfigurator.configure("src/main/resources/Logger_log4j.xml");
	}

	/**
	 * @param args1
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		if (args.length == 1) {
			//C:/Users/raghu/Desktop/OUT
			String destLocation = args[0];
			System.out.println(" destination file location is " + destLocation);
			
			process(destLocation);
		}
	}
	/**
	 * 
	 * @param datFile
	 * @param dateFile
	 * @param outputfile
	 * @throws IOException
	 */
	public static void process(String outputfile) throws IOException {
		System.out.println("Process started...");
		String datFile = PropertiesUtil.getPropertyValue("BASE-FILENAME");
		String folderLocation = PropertiesUtil.getPropertyValue("BASE-FOLDER");
		String outputFolderLocation = PropertiesUtil.getPropertyValue("DESTINATION_FOLDER");
		Date stDate = getStartDate(PropertiesUtil.getPropertyValue("PROCESSING-START-TIME"));
		Date endDate = getEndDate(PropertiesUtil.getPropertyValue("PROCESSING-STOP-TIME"));
		String touchFileCoutnt = PropertiesUtil.getPropertyValue("START-WHEN-TOUCH-FILES-THEN");
		int sleepTime = Integer.parseInt(PropertiesUtil.getPropertyValue("PAUSE-TIME-SEC"))*100;
		int restartCheckCnt = Integer.parseInt(PropertiesUtil.getPropertyValue("RESTART_CHECK_CNT"));
		int errorCntGreaterThen = Integer.parseInt(PropertiesUtil.getPropertyValue("ABEND-WHEN-ERROR-CNT-GREATER-THEN"));
		
		
		
		GregorianCalendar curCal = new GregorianCalendar();
		Date currDate = curCal.getTime();
		//checking current time is greater than the start time and less than the end time
		if (currDate.after(stDate) && currDate.before(endDate)) {
			PrintWriter pw = null;
			// checking the no of touch files. if it is more than exp then stop 1 min
			while (checkFileCout(outputFolderLocation+"status/"+outputfile) > Integer.parseInt(touchFileCoutnt)) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					errorCount++;
					log.error(e.getMessage());
				}
			}
			// checking if any unprocessed records
			
			processUnprocessRecords(folderLocation, outputFolderLocation,outputfile);
			BufferedReader br = null;
			String strLine = "";
			try {
				br = new BufferedReader(new FileReader(folderLocation + datFile+".dat"));
				int recordCount=0;
				while ((strLine = br.readLine()) != null) {
					recordCount++;
					System.out.print("records_Processed :"+recordCount%restartCheckCnt);
					Date currDate1 = new Date();
					System.out.println(" restart_Flag :"+checkFileCout(folderLocation+"abc/"));
					//checking for flag file to stop process for every 100 records
					if (currDate1.before(endDate) && recordCount%restartCheckCnt == 0 && checkFileCout(folderLocation+"abc/")!=0){
						PrintWriter  restartPrintWriter = new PrintWriter(folderLocation +datFile+ ".restart");
						restartPrintWriter.write(strLine+"\n");
						restartPrintWriter.close();
						System.out.println("Program Terminated..");
						break;
						
					}else{
						BranchDocument brnDoc = (BranchDocument) split(strLine).get(1);
						AccountInfo accInfo = (AccountInfo) split(strLine).get(0);
						if (null != accInfo) {
							XmlConvertion.creatXml(brnDoc, outputFolderLocation, accInfo,outputfile);
							PDFMoverProcess.movePDF(accInfo.getFILEPATH(), accInfo, outputFolderLocation,outputfile);
						}
					}
					
					
					if(errorCount==errorCntGreaterThen){
						pw = new PrintWriter(folderLocation +datFile+ ".Unprocessed");
						pw.write(strLine+"\n");
					}
				}
			} catch (FileNotFoundException e) {
				errorCount++;
				log.error("Unable to find the file: fileName " + e.getMessage());
			} catch (IOException e) {
				errorCount++;
				log.error("Unable to read the file: fileName  "	+ e.getMessage());
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}else{
			System.out.println("Current Time :"+currDate +"Start Time :"+stDate +"End Time :"+endDate );
			if(checkFileCout(folderLocation+"abc/")!=0){
				System.out.println("Restart Flag ON. Move records to Unprocessed");
				
			}
		}
	}
	/**
	 * 
	 * @param dir
	 * @return
	 */
	public static int checkFileCout(String dir) {
		int count = 0;
		if (null != dir) {
			File file = new File(dir);
			if (file.isDirectory() && file.list().length > 0) {
				count = file.list().length;
			}
		}
		return count;
	}
	/**
	 * This method split the .dat file record into xml object
	 * @param record
	 * @return
	 */
	public static List<Object> split(String record) {
		String[] elements = record.split("\\|");
		List<Object> list = new ArrayList<Object>();		
		AccountInfo accInfo = new AccountInfo();
		BranchDocument bdoc = new BranchDocument();
		IndexingInfo indexInfo = new IndexingInfo();
		indexInfo.setApplicationName("Statement");
		ContentMetadata cmData = new ContentMetadata();
		List<Metadata> mList = new ArrayList<Metadata>();
		if (elements.length >= 9) {
			if (null != elements[7]) {				
				bdoc.setFileName(elements[7]);
				accInfo.setFILENAME(elements[7]);
			}			
			//accInfo.setACCTID(elements[5]);
			if (null != elements[0]){
				Metadata meData = new Metadata();
				meData.setValue(elements[0]);
				meData.setName("account_id");
				meData.setReqInd("Y");
				mList.add(meData);
				accInfo.setVNDRACCNO(elements[0]);
			} else {
				System.out.println("VNDRACCNO should not be null");
			}
			if (null != elements[1]){
				Metadata meData = new Metadata();
				meData.setValue(elements[1]);
				meData.setName("country_code");
				meData.setReqInd("Y");
				mList.add(meData);
				accInfo.setCNTRYCD(elements[1]);
			}
			if (null != elements[2]){
				Metadata meData = new Metadata();
				meData.setValue(elements[2]);
				meData.setName("stmtyr");
				meData.setReqInd("Y");
				mList.add(meData);
				accInfo.setSTMTYR(elements[2]);
			}
			if (null != elements[3])
				accInfo.setSTMTMO(elements[3]);
			if (null != elements[4]){
				Metadata meData = new Metadata();
				meData.setValue(elements[4]);
				meData.setName("EFF_TS");
				meData.setReqInd("Y");
				mList.add(meData);
				accInfo.setEFFDA(elements[4]);
			}
			if(null != elements[5]){
				accInfo.setACCTID(elements[5]);
			}
			if (null != elements[6]){
				Metadata meData = new Metadata();
				meData.setValue(elements[6]);
				meData.setName("stmt_Doc_Tracking_ID");
				meData.setReqInd("Y");
				mList.add(meData);
				accInfo.setSTMTDOCTRACKINGID(elements[6]);
			}			
			if (null != elements[8]){
				accInfo.setFILEPATH(elements[8]);
			}
			Metadata meData = new Metadata();
			meData.setValue("Document");
			meData.setName("doc_type");
			meData.setReqInd("Y");
			mList.add(meData);
			
			Metadata meData1 = new Metadata();
			meData1.setValue("statements");
			meData1.setName("title");
			meData1.setReqInd("Y");
			mList.add(meData1);
			Metadata meData2 = new Metadata();
			meData2.setValue("jfwcheck");
			meData2.setName("author");
			meData2.setReqInd("Y");
			mList.add(meData2);
			
			Metadata meData3 = new Metadata();
			meData3.setValue("custstmt_r");
			meData3.setName("security_account");
			meData3.setReqInd("Y");
			mList.add(meData3);
			
			Metadata meData4 = new Metadata();
			meData4.setValue("EjContent");
			meData4.setName("security_group");
			meData4.setReqInd("Y");
			mList.add(meData4);
			
			Metadata meData5 = new Metadata();
			meData5.setValue("Client");
			meData5.setName("content_category");
			meData5.setReqInd("Y");
			mList.add(meData5);
			
			Metadata meData6 = new Metadata();
			meData6.setValue("custstmt");
			meData6.setName("profile_trigger");
			meData6.setReqInd("Y");
			mList.add(meData6);
			
			Metadata meData7 = new Metadata();
			meData7.setValue("plua_10_yrs");
			meData7.setName("retention_class");
			meData7.setReqInd("Y");
			mList.add(meData7);
			Metadata meData8 = new Metadata();
			meData8.setValue("CUSTSTMT");
			meData8.setName("form_code");
			meData8.setReqInd("Y");
			mList.add(meData8);
			
			Metadata meData9 = new Metadata();
			meData9.setValue("stmtyr");
			meData9.setName("additional_info");
			meData9.setReqInd("Y");
			mList.add(meData9);
			
			Metadata meData10 = new Metadata();
			meData10.setValue("stmt month");
			meData10.setName("reference_information");
			meData10.setReqInd("Y");
			mList.add(meData10);
			Metadata meData11 = new Metadata();
			meData11.setValue("Stmt_Doc_Tracking_ID");
			meData11.setName("ref_id");
			meData11.setReqInd("Y");
			mList.add(meData11);
		}
		cmData.getMetadata().addAll(mList);
		indexInfo.setContentMetadata(cmData);
		bdoc.setIndexingInfo(indexInfo);
		list.add(0, accInfo);
		list.add(1, bdoc);
		
		return list;
	}

	/**
	 *  This method converts the time HHMM to date format
	 * @param time
	 * @return
	 */
	public static Date getStartDate(String time) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
		cal.set(Calendar.MINUTE, Integer.parseInt(time.substring(2, 4)));
		return cal.getTime();

	}

	/**
	 * This method converts the time HHMM to date format
	 * @param endDate
	 * @return
	 */
	public static Date getEndDate(String endDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endDate.substring(0, 2)));
		cal.set(Calendar.MINUTE, Integer.parseInt(endDate.substring(2, 4)));
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	/**
	 * This method process the unprocessed records
	 * @param dateFileLocation
	 * @param outputfile
	 */
	public static void processUnprocessRecords(String dateFileLocation, String outputfile,String appid) {
		File file = new File(dateFileLocation + "/Unprocessed" + ".dat");
		if (null != file && file.exists()) {
			BufferedReader br2 = null;
			String strLine2 = "";
			try {
				br2 = new BufferedReader(new FileReader(dateFileLocation + "/Unprocessed" + ".dat"));
				while ((strLine2 = br2.readLine()) != null) {
					BranchDocument brnDoc = (BranchDocument) split(strLine2).get(1);
					AccountInfo accInfo = (AccountInfo) split(strLine2).get(0);
					if (null != accInfo) {
						XmlConvertion.creatXml(brnDoc, outputfile,accInfo,appid);										
						PDFMoverProcess.movePDF(accInfo.getFILEPATH(), accInfo, outputfile,appid);
					}
				}
			} catch (FileNotFoundException e) {
				errorCount++;
				log.error("Unable to find the file: fileName " + e.getMessage());
			} catch (IOException e) {
				errorCount++;
				log.error("Unable to read the file: fileName  "	+ e.getMessage());
			} finally {
				try {
					if (br2 != null) {
						br2.close();
					}
					File f = new File(outputfile + "/Unprocessed" + ".dat");
					f.delete();
				} catch (Exception e) {
					errorCount++;
					log.error(e.getMessage());
				}
			}
		}
	}
	
	public static List<Date> getStarAndEndDates(String location){
		String statDate = null;
		String edDate = null;
		//fetching the start date and end date from the date.dat file
		BufferedReader br1 = null;
		String strLine1 = "";
		try {
			br1 = new BufferedReader(new FileReader(location));
			int i = 0;
			while ((strLine1 = br1.readLine()) != null) {
				if (i == 0) {
					statDate = strLine1;
					i++;
				} else {
					edDate = strLine1;
				}
			}
		} catch (FileNotFoundException e) {
			errorCount++;
			log.error("Unable to find the file: fileName " + e.getMessage());
		} catch (IOException e) {
			errorCount++;
			log.error("Unable to read the file: fileName  " + e.getMessage());
		} finally {
			if (br1 != null) {
				try {
					br1.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
		List<Date> list = new ArrayList<Date>();
		list.add(getStartDate(statDate));
		list.add(getEndDate(edDate));
		return list;
	}
	public static boolean isReStart(String restatPath){
		File f = new File(restatPath);
		return f.exists();
	}
	
}