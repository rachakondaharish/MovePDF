import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;

import jaxb.BranchDocument;

import org.apache.log4j.Logger;

import com.ej.entities.AccountInfo;

/**
 * 
 */

public class XmlConvertion {

	private static Logger log = Logger.getLogger(XmlConvertion.class);

	public static void creatXml(BranchDocument brAccount, String desti, AccountInfo accInfo, String appid,String fileDate) {
		try {
			String xml;
			try {
				File file = new File(accInfo.getFILEPATH()+accInfo.getFILENAME());
				String xmlLocation = desti +  "/" + fileDate + "/" + appid + "/XML";
				File dirFile = new File(xmlLocation);
				if (!dirFile.exists()){
					dirFile.mkdirs();
				}
				

				xml = JAXBUtil.marshallXML(BranchDocument.class, brAccount);

				PrintWriter pw = new PrintWriter(xmlLocation+"/"
						+ accInfo.getVNDRACCNO() + "."
						+ accInfo.getSTMTDOCTRACKINGID() + ".xml");
				pw.write(xml);
				pw.close();
			} catch (IOException e) {
				ProcessController.errorCount++;
				log.error(e.getMessage());
			}

		} catch (JAXBException e) {
			ProcessController.errorCount++;
			log.error(e.getMessage());
		}
	}

}
