

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import jaxb.BranchDocument;

import org.apache.log4j.Logger;

/**
 * 
 */

/**
 * @author raghu
 * 
 */
public class JAXBUtil {
	private static Map<Class, JAXBContext> jaxbContexts;
	private static Logger log = Logger.getLogger(JAXBUtil.class);

	static {
		jaxbContexts = new HashMap<Class, JAXBContext>();
		try {
			jaxbContexts.put(BranchDocument.class, JAXBContext.newInstance(BranchDocument.class));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public static String marshallXML(Class jaxbClass, Object dtoToMarshall)
			throws JAXBException, IOException {
		JAXBContext jc = jaxbContexts.get(jaxbClass);
		Marshaller marshaller = jc.createMarshaller();
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(dtoToMarshall, stringWriter);
		stringWriter.close();
		return stringWriter.getBuffer().toString();
	}
}
