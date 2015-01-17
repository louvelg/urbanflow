package beeteam.urbanflow.aug;

import java.io.File;

public class DataConversionMain {

	

	public static void main(String[] args) throws Exception
	{
		File input = new File("C:\\Users\\Augustin\\Desktop\\24H\\data_files3");
		File output = new File("C:\\Users\\Augustin\\Desktop\\24H\\dir1");
		
		DataConversion dc = new DataConversion(input,output);
		dc.convertAll();
	}
}
