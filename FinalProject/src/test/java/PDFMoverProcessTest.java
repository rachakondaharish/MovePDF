import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ej.entities.AccountInfo;

import jaxb.BranchDocument;


public class PDFMoverProcessTest {
	private BranchDocument bdoc = null;
	private AccountInfo accInfo = null;
	private String destination = null;
	private String appId =null;

	@Before
	public void initialize() {
		accInfo = new AccountInfo();		
		accInfo.setFILENAME("ABC.pdf");		
		accInfo.setVNDRACCNO("12345678");
		accInfo.setCNTRYCD("US");
		accInfo.setSTMTYR("2016");
		accInfo.setEFFDA("21/11/2016");
		accInfo.setSTMTDOCTRACKINGID("000000001");
		accInfo.setACCTID("987654321");
		accInfo.setFILEPATH("src/test/resources/IN");
		destination = "src/test/resources/Out";
		appId = "custstmt1";
		
	}
	@Test
	public void creatXmlTest() {
		
		String readPath = accInfo.getFILEPATH()+"/"+ accInfo.getFILENAME();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String fileDate = sdf.format(today);
		PDFMoverProcess.movePDF(new File(readPath),accInfo, destination,appId, fileDate);
		String pdfLocation = destination +  "/" + fileDate+"/" + appId+"/PDF";
		String writePath = pdfLocation+ "/" + accInfo.getVNDRACCNO() + "." + accInfo.getSTMTDOCTRACKINGID() + ".pdf";
		File xmlFile = new File(writePath);
		Assert.assertTrue(xmlFile.exists());
	}

}
