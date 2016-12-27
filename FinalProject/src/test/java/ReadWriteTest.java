import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.ej.utils.Utilities;

import junit.framework.Assert;

public class ReadWriteTest {
	File file = null;
	String filePath = null;

	@Before
	public void initialize() {
		file = new File("src/test/resources/IN/abcd.txt");
		filePath = "src/test/resources/Out/abcd.txt";
	}
	@Test
	public void readAndWriteTest(){
		Utilities utilities = new Utilities();
		try {
			utilities.readAndWrite(file, filePath);
			File f= new File(filePath);
			Assert.assertEquals(true, f.exists());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
