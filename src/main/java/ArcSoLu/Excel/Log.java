package ArcSoLu.Excel;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
	 Logger logger = Logger.getLogger("MyLog");  
	 FileHandler fh;  

	public Log() {
		try {  
			// This block configure the logger with handler and formatter  
			File f=new File("/Users/ano/t/Excel/log");
			if(!f.exists())
				f.createNewFile();
			fh = new FileHandler("/Users/ano/t/Excel/log");  
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  
		}catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  



	}
	public  void add(String s) {
		try {  

			// the following statement is used to log any messages  
			logger.info(s);  

		} catch (SecurityException e) {  
			e.printStackTrace();  
		}  
	}
	

}
