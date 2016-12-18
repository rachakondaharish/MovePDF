import java.io.File;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public class PDFMoverProcess {

	private static Logger log = Logger.getLogger(PDFMoverProcess.class);

	/**
	 * 
	 * @param read
	 * @param write
	 * @param desti
	 */
	public static void movePDF(String read, AccountInfo accInfo, String desti,String appid) {
		String readPath = read;
		String writePath = desti;
		
		File file = new File(accInfo.getFILEPATH()+accInfo.getFILENAME());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileDate = sdf.format(file.lastModified());
		String pdfLocation = desti +  "/" + fileDate + "/" + appid + "/PDF";
		File dirFile = new File(pdfLocation);
		if (!dirFile.exists()){
			dirFile.mkdirs();
		}
		


		writePath = pdfLocation + "/" +accInfo.getVNDRACCNO() + "." + accInfo.getSTMTDOCTRACKINGID() + ".pdf";

		ReadWrite readWrite = null;
		try {
			readWrite = new ReadWrite();
			readWrite.readAndWrite(readPath, writePath);

		} catch (Exception e) {
			ProcessController.errorCount++;
			log.error(e.getMessage());
		} finally {
			readWrite = null;
		}
		
		String folder4 = desti + "status";
		
        File dirFile3 = new File(folder4);
		
		if (!dirFile3.exists()) {
			dirFile3.mkdir();
		}
		
		String folder5 = folder4 +"/"+ appid;
		
		File dirFile4 = new File(folder5);
				
				if (!dirFile4.exists()) {
					dirFile4.mkdir();
				}
		
		
		createTouchFile(folder5 + "/" + accInfo.getVNDRACCNO() + "." + accInfo.getSTMTDOCTRACKINGID() + ".txt");
	}
	
	public static void createTouchFile(String filePath){
		if (null != filePath) {
			try {
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			} else {
				file.setLastModified(System.currentTimeMillis());
			}
			}catch (Exception e){
				ProcessController.errorCount++;
				log.error(e.getMessage());
			}
		}
	}

}
