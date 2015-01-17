package beeteam.urbanflow.aug.readtextfile;

import java.io.File;
import java.io.FileReader;


public class FileReadString {


    public String getName()				{return "gus.file.read.string";}
    public String getCreationDate()		{return "2006.08.10";}


    
    public Object transform(Object obj) throws Exception
    {
        File file = (File) obj;
        int length = (int) file.length();
        
        FileReader fr = null;	
        char[] a = null;
        
        try
        {
        	fr = new FileReader(file);	
            a = new char[length];
        	fr.read(a,0,length);
        }
        finally
        {if(fr!=null) fr.close();}
        
        return new String(a);
    }
}

