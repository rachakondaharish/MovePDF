

import java.io.*;

public class ReadWrite {
	/**
	 * This method reads the data from the input file and writes to output file
	 * 
	 * @param input
	 * @param output
	 * @throws IOException
	 */

	public void readAndWrite(String input, String output) throws IOException {
		File f = new File(input);
		OutputStream oos = new FileOutputStream(output);
		byte[] buf = new byte[8192];
		InputStream is = new FileInputStream(f);
		int c = 0;
		while ((c = is.read(buf, 0, buf.length)) > 0) {
			oos.write(buf, 0, c);
			oos.flush();
		}
		oos.close();
		is.close();
	}
}
