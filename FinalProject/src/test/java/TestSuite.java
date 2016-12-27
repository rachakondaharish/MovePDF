import org.junit.runner.RunWith;
import org.junit.runners.Suite;



@RunWith(Suite.class)
@Suite.SuiteClasses({
	JaxbUtilTest.class,
	PDFMoverProcessTest.class,
	ReadWriteTest.class,
	XmlConvertionTest.class,
	//ProcessControllerTest.class,
})
public class TestSuite {
	

}
