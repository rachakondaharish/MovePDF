
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertiesUtil {
	private static Logger log = Logger.getLogger(PropertiesUtil.class);
	private static Properties prop = new Properties();
	private static Properties prop2 = new Properties();
	
	InputStream is = null;
	InputStream is2 = null;
	static {
		InputStream is = PropertiesUtil.class.getResourceAsStream("TransferConstants.properties");
		InputStream is2 = PropertiesUtil.class.getResourceAsStream("StateArcConstants.properties");
		try {
			prop.load(is);
			prop2.load(is2);
			prop.putAll(prop2);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	public static String getPropertyValue(String property){
		return prop.getProperty(property);
		
	}

}
