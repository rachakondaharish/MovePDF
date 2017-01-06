import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import jaxb.BranchDocument;
import jaxb.BranchDocument.IndexingInfo;
import jaxb.BranchDocument.IndexingInfo.ContentMetadata;
import jaxb.BranchDocument.IndexingInfo.ContentMetadata.Metadata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ej.utils.JAXBUtil;

public class JaxbUtilTest {
	private BranchDocument bdoc = null;

	@Before
	public void initialize() {

		bdoc = new BranchDocument();
		IndexingInfo indexInfo = new IndexingInfo();
		indexInfo.setApplicationName("Statement");
		ContentMetadata cmData = new ContentMetadata();
		List<Metadata> mList = new ArrayList<Metadata>();
		bdoc.setFileName("ABC.pdf");
		Metadata meData = new Metadata();
		meData.setValue("12345678");
		meData.setName("account_id");
		meData.setReqInd("Y");
		mList.add(meData);
		Metadata meData_1 = new Metadata();
		meData.setValue("US");
		meData.setName("country_code");
		meData.setReqInd("Y");
		mList.add(meData_1);
		Metadata meData_2 = new Metadata();
		meData.setValue("stmtyr");
		meData.setName("2016");
		meData.setReqInd("Y");
		mList.add(meData_2);
		Metadata meData_3 = new Metadata();
		meData.setValue("21/11/2016");
		meData.setName("EFF_TS");
		meData.setReqInd("Y");
		mList.add(meData_3);
		Metadata meData_4 = new Metadata();
		meData.setValue("000000001");
		meData.setName("stmt_Doc_Tracking_ID");
		meData.setReqInd("Y");
		mList.add(meData_4);
		Metadata meData_5 = new Metadata();
		meData.setValue("Document");
		meData.setName("doc_type");
		meData.setReqInd("Y");
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
		cmData.getMetadata().addAll(mList);
		indexInfo.setContentMetadata(cmData);
		bdoc.setIndexingInfo(indexInfo);

	}

	@Test
	public void marshallXMLTest() {
		try {
			String xmlString = JAXBUtil.marshallXML(BranchDocument.class, bdoc);
			Assert.assertNotNull(xmlString);
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}
