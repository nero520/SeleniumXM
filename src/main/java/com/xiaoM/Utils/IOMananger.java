package com.xiaoM.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * IO类
 * @author XiaoM
 *
 */
public class IOMananger {
	/**
	 * 读取excel
	 * @param sheetName
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String[][] readExcelDataXlsx(String sheetName,String path) throws IOException {
		InputStream is = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(is);//读取Excel
		XSSFSheet sheet = workbook.getSheet(sheetName);//读取sheet
		if(sheet!=null){
			int lastrowNum = sheet.getLastRowNum()+1;//获取总行数
			int collNum = sheet.getRow(0).getLastCellNum();//获取列数	 
			String[][] user = new String[lastrowNum][collNum];
			for(int rowNum=0;rowNum<lastrowNum;rowNum++){
				XSSFRow row = sheet.getRow(rowNum);
				for(int j=0;j<collNum;j++){
					if(row.getCell(j)!=null){
						row.getCell(j).setCellType(CellType.STRING);	
						user[rowNum][j] = row.getCell(j).getStringCellValue();
					}
				}	
			}
			workbook.close();
			return user;
		}else{
			System.out.println(sheet+"is null!");
			workbook.close();
			return null;
		}	
	}
	/**
	 * 获取执行测试用例
	 * @param sheetName
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String[][] runTime(String sheetName,String path) throws IOException{
		String[][] Date =  readExcelDataXlsx(sheetName,path);
		List<String> BrowserName = new LinkedList<String>();
		List<String> caseName = new LinkedList<String>();
		for(int i=1;i<Date.length;i++){
			if(Date[i][0].equals("YES")){
				BrowserName.add(Date[i][2]);
				caseName.add(Date[i][4]);	
			}
		}
		String[][] runTime  = new String[caseName.size()][2];
		for(int k =0;k<caseName.size();k++){
			runTime[k][0]=BrowserName.get(k);
			runTime[k][1]=caseName.get(k);
		}
		return runTime;
	}

	public static void main(String[]args) {
		
	}
}
