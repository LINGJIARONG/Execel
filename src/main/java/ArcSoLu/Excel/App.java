package ArcSoLu.Excel;

/**
 * Hello world!
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws IOException
	{
		String fileLocation= System.getProperty("user.home")+"/Desktop/Excel/ff.xlsx";
		System.out.println(fileLocation);

		FileInputStream file = new FileInputStream(new File(fileLocation));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		//Map<Integer, List<String>> data = new HashMap<>();
		int i = 0;
		System.out.println(sheet.getLastRowNum());
		//		System.out.println(sheet.);
		for (Row row : sheet) {
			for(Cell c:row) {
				System.out.print(c.toString());	
			}
			System.out.println();
			i++;
		}
		
		System.out.println(i);
		workbook.close();

	}
}
