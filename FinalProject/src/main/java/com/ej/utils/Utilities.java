package com.ej.utils;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.ej.entities.AccountInfo;

import jaxb.BranchDocument;
import jaxb.BranchDocument.IndexingInfo;
import jaxb.BranchDocument.IndexingInfo.ContentMetadata;
import jaxb.BranchDocument.IndexingInfo.ContentMetadata.Metadata;

public class Utilities {
	
	
	
	/**
	 * This method reads the data from the input file and writes to output file
	 * 
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	private static Logger log = Logger.getLogger(Utilities.class);
	public void readAndWrite(File orginalFile, String output) throws IOException {
		OutputStream oos = new FileOutputStream(output);
		byte[] buf = new byte[8192];
		InputStream is = new FileInputStream(orginalFile);
		int c = 0;
		while ((c = is.read(buf, 0, buf.length)) > 0) {
			oos.write(buf, 0, c);
			oos.flush();
		}
		oos.close();
		is.close();
	}
	/**
	 * 
	 * @param Trailer
	 * @return
	 */
	public int getTrailerCount(String Trailer) {
		String[] elements = Trailer.split("\\|");
		return Integer.parseInt(elements[1]);
	}
	/**
	 * 
	 * @param header
	 * @return
	 */
	public Boolean cheakHeader(String header, String date) {
		String[] elements = header.split("\\|");
		if(elements[1].equals(date)){
		return true;
		}else{
		return false;
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
				bdoc.setFileName(elements[9]);
				accInfo.setFILENAME(elements[9]);
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
			if (null != elements[7]){
				accInfo.setFILEPATH(elements[8]);
			}
			if (null != elements[8]){
				accInfo.setFILENAME(elements[9]);
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
	 * This method checks Touch File Count
	 * @param record
	 * @return
	 */
	public static Boolean checkTouchFileCount(String outputFolderLocation,String appId,int touchFileCoutnt,int sleepTime){
	

		while (checkFileCout(outputFolderLocation+"status/"+appId) > touchFileCoutnt) {
			try {
				log.info("Sleeping "+sleepTime+" : Touch File count more than "+touchFileCoutnt);
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				log.error("checkTouchFileCount "+e.getMessage());
			}catch (Exception e) {
				log.error("checkTouchFileCount "+e.getMessage());
			}
		}
		
		
		
		return true;
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
	
	


}
