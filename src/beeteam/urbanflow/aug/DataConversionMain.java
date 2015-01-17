package beeteam.urbanflow.aug;

import java.io.File;

public class DataConversionMain {

	public static void main(String[] args) throws Exception
	{
		File root = new File("C:\\Users\\Augustin\\Desktop\\24H");
		convert(root);
	}
	
	private static void convert(File root) throws Exception
	{
		DataConversion dc = new DataConversion(root);
		dc.convertAll();
	}
	
}
