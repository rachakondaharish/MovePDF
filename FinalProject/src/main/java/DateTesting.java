import java.util.Calendar;
import java.util.Date;


public class DateTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		Calendar cal1 = Calendar.getInstance();
		cal1.getTime();
		cal1.add(Calendar.MINUTE, 1);
		System.out.println(cal.getTime().before(cal1.getTime()));
		System.out.println(getStartDate().before(getEndDate()));
		System.out.println(getStartDate());
		System.out.println(getEndDate());
	}
	/**
	 * 
	 * @return
	 */
	public static Date getStartDate(){
		Calendar cal = Calendar.getInstance();
		String time = PropertiesUtil.getPropertyValue("START_TIME");
		cal.set(Calendar.HOUR, Integer.parseInt(time.substring(0, 2)));
		cal.set(Calendar.MINUTE, Integer.parseInt(time.substring(2, 4)));
		return cal.getTime();
		
	}
	/**
	 * 
	 * @return
	 */
	public static Date getEndDate(){
		Calendar cal = Calendar.getInstance();
		String time = PropertiesUtil.getPropertyValue("END_TIME");
		cal.set(Calendar.HOUR, Integer.parseInt(time.substring(0, 2)));
		cal.set(Calendar.MINUTE, Integer.parseInt(time.substring(2, 4)));
		cal.add(Calendar.MINUTE, 1);
		return cal.getTime();
		
	}

}
