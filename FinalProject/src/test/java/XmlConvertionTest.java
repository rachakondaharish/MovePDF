import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jaxb.BranchDocument;
import jaxb.BranchDocument.IndexingInfo;
import jaxb.BranchDocument.IndexingInfo.ContentMetadata;
import jaxb.BranchDocument.IndexingInfo.ContentMetadata.Metadata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ej.entities.AccountInfo;
import com.ej.utils.XmlConvertion;

public class XmlConvertionTest {
	private BranchDocument bdoc = null;
	private AccountInfo accInfo = null;
	private String destination = null;
	private String appId = null;

	@Before
	public void initialize() {
		accInfo = new AccountInfo();
		bdoc = new BranchDocument();
		IndexingInfo indexInfo = new IndexingInfo();
		indexInfo.setApplicationName("Statement");
		ContentMetadata cmData = new ContentMetadata();
		List<Metadata> mList = new ArrayList<Metadata>();
		bdoc.setFileName("ABC.pdf");
		accInfo.setFILENAME("ABC.pdf");
		Metadata meData = new Metadata();
		meData.setValue("12345678");
		meData.setName("account_id");
		meData.setReqInd("Y");
		mList.add(meData);
		accInfo.setVNDRACCNO("12345678");
		Metadata meData_1 = new Metadata();
		meData_1.setValue("US");
		meData_1.setName("US");
		meData_1.setReqInd("Y");
		mList.add(meData_1);
		accInfo.setCNTRYCD("US");
		Metadata meData_2 = new Metadata();
		meData_2.setValue("stmtyr");
		meData_2.setName("2016");
		meData_2.setReqInd("Y");
		mList.add(meData_2);
		accInfo.setSTMTYR("2016");
		Metadata meData_3 = new Metadata();
		meData_3.setValue("21/11/2016");
		meData_3.setName("EFF_TS");
		meData_3.setReqInd("Y");
		mList.add(meData_3);
		accInfo.setEFFDA("21/11/2016");
		Metadata meData_4 = new Metadata();
		meData_4.setValue("000000001");
		meData_4.setName("stmt_Doc_Tracking_ID");
		meData_4.setReqInd("Y");
		mList.add(meData_4);
		accInfo.setSTMTDOCTRACKINGID("000000001");
		accInfo.setACCTID("987654321");
		Metadata meData_5 = new Metadata();
		meData_5.setValue("Document");
		meData_5.setName("doc_type");
		meData_5.setReqInd("Y");
		mList.add(meData_5);

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
/*
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
		mList.add(meData11);*/
		cmData.getMetadata().addAll(mList);
		indexInfo.setContentMetadata(cmData);
		bdoc.setIndexingInfo(indexInfo);
		accInfo.setFILEPATH("src/test/resources/IN");
		destination = "src/test/resources/Out";
		appId = "custstmt1";

	}
	@Test
	public void creatXmlTest() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String fileDate = sdf.format(today);
		XmlConvertion.creatXml(bdoc, destination, accInfo,appId, fileDate);
		File file = new File(accInfo.getFILEPATH()+"/"+accInfo.getFILENAME());
		String xmlLocation = destination +  "/" + fileDate+"/"+appId+"/XML/" + accInfo.getVNDRACCNO() + "." + accInfo.getSTMTDOCTRACKINGID() + ".xml";
		File xmlFile = new File(xmlLocation);
		Assert.assertTrue(xmlFile.exists());
	}

}
