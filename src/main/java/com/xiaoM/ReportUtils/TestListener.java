package com.xiaoM.ReportUtils;

import com.xiaoM.Utils.IOMananger;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TestListener  extends TestListenerAdapter{

	public static String[][] RunCase;//执行测试case
	public static Map<String, String> screenMessageList = new HashMap<String, String>();
	public static Map<String, String> failMessageList = new HashMap<String,String>();
	public static Map<String, String> logList = new HashMap<String, String>();
	public static Map<String, Throwable> failThrowable = new HashMap<String, Throwable>();
	public static Map<String, Long> RuntimeStart = new HashMap<String, Long>();
	public static Map<String, Long> RuntimeEnd = new HashMap<String, Long>();
	public static List<String> Category = new ArrayList<String>();
	public static String Selenium_Gird_Address;
	public static String OS;
	public static String ProjectPath;//工程路径
	public static String TestCase;//测试用例所在的表
	public static String Selenium_Gird;
	public static String CasePath;
	public static String Browser_Path;
	//配置初始化
	static{	
		//获取操作系统
		String os = System.getProperty("os.name");
		if(os.contains("Mac")){	
			OS = "MAC";
		}else if(os.contains("Windows")){
			OS = "WINDOWS";
		}else if(os.contains("Linux")){
			OS = "LINUX";
		}
		//读取配置文件
		Properties pp = new Properties();
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream("config.properties"),"UTF-8");
			pp.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ProjectPath = new File(System.getProperty("user.dir")).getPath();// 工程根目录
		TestCase = pp.getProperty("TESTCASE");
		Selenium_Gird = pp.getProperty("SELENIUM_GRID");
		Selenium_Gird_Address = pp.getProperty("SELENIUM_GRID_ADDRESS");
		CasePath = ProjectPath +"/testCase/"+ TestCase+".xlsx";
		Browser_Path = pp.getProperty("BROWSER_PATH");
		//获取测试执行用例
		try {
			RunCase = IOMananger.runTime("TestCases", CasePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String logPath = ProjectPath + "/test-output/log/RunLog.log";
		File path = new File(logPath);
		if (path.exists()) {
			path.delete();//删除日志文件
		}
	}

	@Override
	public void onTestFailure( ITestResult result){
		String ID = result.getParameters()[0].toString();
		String CaseName = result.getParameters()[2].toString();
		String Version = result.getParameters()[4].toString();
		String BrowserName = Version.equals("")||Version.isEmpty()?result.getParameters()[3].toString():result.getParameters()[3]+"("+Version+")";
		String TestCategory = ID+"_"+ CaseName +"_"+ BrowserName;
		TestListener.failThrowable.put(TestCategory,result.getThrowable());
	}
}
