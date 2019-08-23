package ArcSoLu.Excel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CsvReader {

	public CsvReader() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws IOException {
		List<String> array=new ArrayList<>();
		App.treat(array);
		System.out.println(array.size());

		File writename = new File(System.getProperty("user.home")+"/t/Excel/fout.csv");
		writename.createNewFile();

		BufferedWriter out = new BufferedWriter(new FileWriter(writename));

		String csvFile = System.getProperty("user.home")+"/t/Excel/ff.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String next="";
		boolean isMatch=false;
		try {
			System.out.println("begin");
			int i=0;
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					csvFile), "GBK"));
			boolean hasNext=true;
			while (true&&hasNext) {
				isMatch=false;

				if(next!="") {
					line=next;
					next="";
				}else {
					line = br.readLine() ;
				}
				if(line==null)
					break;
				while(!isMatch) {
					String line2=br.readLine();
					if(line2==null) {
						hasNext=false;
						break;
					}
					String[] check = line2.split(",");
					
					
					if(line2!=null) {
						if(line2.length()>=10) {
							String NewLine=line2.substring(0, 10);
							isMatch = NewLine.matches(".*\\/.*\\/.*");
						}else {
							isMatch=false;
						}
						if(!isMatch) {
							line=line+line2;

						}else {
							next=line2;
						}
					}
				}
				String line1=line;
				String[] list=line1.split(",");
				if(list.length>=3) {
					if(array.contains(list[1])) {
						line=line.substring(0,line.length()-2)+"99";
					}
				}
				
				
				out.write(line+"\n"); 
				i++;
			}
			System.out.println(i);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/* 写入Txt文件 */
		// 相对路径，如果没有则要建立一个新的output。txt文件
		try {

			out.flush(); 
			out.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件



	}
}


