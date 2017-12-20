package com.xiaoM.Utils;

import com.xiaoM.ReportUtils.TestListener;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class IOMananger {
	//设置日期格式
	static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	//获取当前日期
	static String date=dateFormat.format(new Date()).toString();
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
		List<String> ID = new LinkedList<String>();
		List<String> Description = new LinkedList<String>();
		List<String> CaseName = new LinkedList<String>();
		List<String> BrowserName = new LinkedList<String>();
		List<String> Version = new LinkedList<String>();
		for(int i=1;i<Date.length;i++) {
			if (Date[i][0].equals("YES")) {
				ID.add(Date[i][1]);
				Description.add(Date[i][2]);
				CaseName.add(Date[i][3]);
				BrowserName.add(Date[i][4]);
				Version.add(Date[i][5]);
			}
		}
		String[][] runTime  = new String[CaseName.size()][5];
		for(int k =0;k<CaseName.size();k++){
			runTime[k][0]=ID.get(k);
			runTime[k][1]=Description.get(k);
			runTime[k][2]=CaseName.get(k);
			runTime[k][3]=BrowserName.get(k);
			runTime[k][4]=Version.get(k);
		}
		return runTime;
	}
	/**
	 * 功能：Java读取txt文件的内容
	 * 步骤：1：先获得文件句柄
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流
	 * 4：一行一行的输出。readline()。
	 * 备注：需要考虑的是异常情况
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	public static List<String> readTxtFile(String filePath) throws FileNotFoundException{
		List<String> txt = new ArrayList<String>();
		Scanner in = new Scanner(new File(filePath));
		while(in.hasNext()){
			String str=in.nextLine();
			if(!str.isEmpty()){
				txt.add(str.toString());
			}
		}
		in.close();
		return txt;
	}
	public static void DealwithRunLog(String DeviceName) {
		String logPath = TestListener.ProjectPath+"/test-output/log/";
		List<String> DriversLog = null;
		try {
			DriversLog = IOMananger.readTxtFile(logPath+"RunLog.log");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		logPath = logPath + DeviceName;
		File destDir = new File(logPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		String logDriverPath  = logPath +"/"+ DeviceName+"-"+date+".log";
		for(String logDriver:DriversLog){
			if(logDriver.contains(DeviceName)){
				IOMananger.saveToFile(logDriverPath, logDriver);
			}
		}
	}
	/**
	 * 写入数据到txt文本中
	 * @param conent
	 */
	public static void saveToFile(String Path, String conent) {
		BufferedWriter bw = null;
		try {
			/**
			 * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
			 * 清空重新写入：把第二个参数设为false
			 */
			FileOutputStream fo = new FileOutputStream(Path, true);
			OutputStreamWriter ow = new OutputStreamWriter(fo);
			bw = new BufferedWriter(ow);
			bw.append(conent);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (Exception e) {
			System.out.println("写入数据失败！！！");
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[]args) {
		
	}
}
