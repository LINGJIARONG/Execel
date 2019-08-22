package ArcSoLu.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Testw {

	public static void modifyExistingWorkbook() throws InvalidFormatException, IOException {
		// Obtain a workbook from the excel file
		String fileLocation= System.getProperty("user.home")+"/t/Excel/ff.xlsx";
		Workbook workbook = WorkbookFactory.create(new File(fileLocation));

		// Get Sheet at index 0
		Sheet sheet = workbook.getSheetAt(0);

		// Get Row at index 1
		Row row = sheet.getRow(1);

		// Get the Cell at index 2 from the above row
		Cell cell = row.getCell(2);

		// Create the cell if it doesn't exist
		if (cell == null)
			cell = row.createCell(2);

		// Update the cell's value
		cell.setCellType(CellType.STRING);
		cell.setCellValue("Updated Value");

		// Write the output to the file
		FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.home")+"/t/Excel/out.xlsx");
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
	}
	public Testw() {

	}
	public static void main(String[] args) {
		try {
			Testw.modifyExistingWorkbook();
		} catch (InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
