package ArcSoLu.Excel;

/**
 * Hello world!
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Hello world!
 *
 */
public class App 
{
	public static final int status=18;

	public static void main( String[] args ) throws IOException
	{

		String fileLocation= System.getProperty("user.home")+"/t/Excel/ff.xlsx";

		System.out.println(fileLocation);


		FileInputStream file = new FileInputStream(new File(fileLocation));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);

		int i = 1;
		int problem=0;

		for (Row row : sheet) {
			int col=0;	
			

			for(Cell c:row) {
				if(col!=0&&col!=8&&col!=12&&col!=15) {
					col++;
					continue;
				}
				short fontIndex = c.getCellStyle().getFontIndex();
				Font font = workbook.getFontAt(fontIndex);
				int red = 0;
				int green = 0;
				int blue = 0;
				if (font instanceof XSSFFont)
				{
					XSSFColor color = ((XSSFFont) font).getXSSFColor();
					if(color!=null) {
						byte[] rgb = color.getRGB();
						// Bytes are signed, so values of 128+ are negative!
						// 0: red, 1: green, 2: blue
						red = (rgb[0] < 0) ? (rgb[0] + 256) : rgb[0];
						green = (rgb[1] < 0) ? (rgb[1] + 256) : rgb[1];
						blue = (rgb[2] < 0) ? (rgb[2] + 256) : rgb[2];
					}
				}
				if(red!=0||green!=0||blue!=0) {
					if(red==67&&blue==67&&green==67) {

						//						Cell problemCell=row.getCell(16);
						//						problemCell.setCellValue("1");
						//workbook.write(out);

					}else if(col==0&&c.toString().trim()=="") {

						//						Cell problemCell=row.getCell(16);
						//						problemCell.setCellValue("1");

					}

					else if(c.toString().trim()=="") {
						//System.out.println(i+"-th row "+c.toString()+"-r:g:b= "+red+":"+green+":"+blue+",");



					}
					else {

						System.out.println(i+"-th row "+c.toString()+"-r:g:b= "+red+":"+green+":"+blue+",");
						problem++;

						Cell problemCell2=row.getCell(status);
						problemCell2.setCellValue(99);			



						break;
					}
				}else {

				}
				col++;
			}
			i++;
		}

		System.out.println("total : "+ i+"/"+"problem : "+problem);
		file.close();

		String output=System.getProperty("user.home")+"/t/Excel/out.xlsx";

		try {
			FileOutputStream outputStream = new FileOutputStream(output);
		//	workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}




		//
		//		FileOutputStream out=new FileOutputStream(new File(fileLocation));
		//		workbook.write(out);
		//		out.flush();
		//		out.close();
		workbook.close();




	}
}
