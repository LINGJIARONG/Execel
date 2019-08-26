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
import java.util.ArrayList;
import java.util.List;

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

	public static void treat() throws Exception
	{

		String fileLocation= System.getProperty("user.home")+"/t/Excel/ff.xlsx";

		System.out.println(fileLocation);


		FileInputStream file = new FileInputStream(new File(fileLocation));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		int i = 1;
		int problem=0;
		ArrayList<Integer> throwaway=new ArrayList<>();
		for (Row row : sheet) {
			int col=0;	
			Boolean invalid=true;
			for(Cell c:row) {
				if(col!=0&&col!=8&&col!=12&&col!=15&&col!=9&&col!=1) {
					col++;
					continue;
				}
				if(c.toString().trim()!="")
					invalid=false;
					
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
						//System.out.println(i+"-th row "+c.toString()+"-r:g:b= "+red+":"+green+":"+blue+",")
					}
					else {
						System.out.println(i+"-th row "+c.toString()+"-r:g:b= "+red+":"+green+":"+blue+",");
						problem++;
						System.out.println(row.getCell(1).toString());
						if(row.getCell(1).toString().trim().isEmpty()) {
						}else {
							try {
								Cell problemCell2=row.getCell(status);
								problemCell2.setCellValue(99);	
								System.out.println(row.getCell(1).toString());
							}catch(NumberFormatException e) {
							}
						}
						break;
					}
				}else {

				}
				col++;
			}
			if(invalid)
				throwaway.add(i);
			i++;
		}

		DriverExample.connect();
		DriverExample.prepare();
		int input =0;
		System.out.println(throwaway.toString());
		//FileOutputStream out=new FileOutputStream(fileOut);
		for (Row row : sheet) {
			input++;
			if(input==1)
				continue;
			if(throwaway.contains(input))
				continue;
			//String rowData="";
			ArrayList<String> rowDataList = new ArrayList<String>();
			for(int j=0;j<row.getLastCellNum()-1;j++) {
				Cell c=row.getCell(j);
				if(c==null) {
					rowDataList.add("");
					//rowData+="$";
				}
				else {
					rowDataList.add(c.toString());
					//rowData+=c.toString()+"$";
				}
			}
			rowDataList.add(row.getCell(row.getLastCellNum()-1).toString());
			DriverExample.insertLine(rowDataList);
			//rowData+=row.getCell(row.getLastCellNum()-1).toString()+"\r";
			//out.write(rowData.getBytes());
		}
	//	out.close();

		DriverExample.execute();

		System.out.println("total : "+ i+"/"+"problem : "+problem);
		System.out.println("total input "+i+"record");
		System.out.println("insert total:"+DriverExample.data_count);
		file.close();
		//		workbook.write(out);
		//		out.flush();
		//		out.close();
		workbook.close();
		DriverExample.close();

	}
	public static void main(String[] args) throws Exception {
		App.treat();
	}
}
