/*import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class ProcessControllerTest {
	String rootFolder = null; 
	String datFile = null;
	String dateFile = null; 
	String outputfile = null;
	@Before
	public void initialize(){
		rootFolder = "src/test/resources/IN"; 
		datFile = "src/test/resources/IN/statementArchive1.dat";
		dateFile = "src/test/resources/IN/date.dat";
		outputfile = "src/test/resources/Out/ProcessTest";
		
	}
	@Test
	public void processTest() {
		try {
			ProcessController.process(rootFolder, datFile, dateFile, outputfile);
			File f = new File(outputfile);
			Assert.assertTrue(f.exists());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void checkFileCoutTest(){
		//Assert.ProcessController.checkFileCout(outputfile);
	}
}
*/