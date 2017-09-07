package com.xiaoM.ReportUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.xiaoM.Utils.IOMananger;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Run;
import com.xiaoM.Utils.SeleniumScreenShot;

public class TestListener  extends TestListenerAdapter{
	Log log= new Log(this.getClass());
	public static String[][] RunCase;//执行测试case
	public static Map<String, Object> map = new HashMap<String, Object>();
	public static List<String> screenMessageList=new ArrayList<String>();
	public static List<String> runMessageList=new ArrayList<String>();
	public static List<String> failMessageList=new ArrayList<String>();
	public static String OS;
	public static String ProjectPath;//工程路径
	public static  String TestCase;//测试用例所在的表
	public static String CasePath;
	//配置初始化
	static{	
		//获取操作系统
		String os = System.getProperty("os.name");
		if(os.contains("Mac")){	
			OS = "MAC";
		}else if(os.contains("Windows")){
			OS = "WINDOWS";
		}
		//读取配置文件
		Properties pp = new Properties();
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream("config.properties"),"UTF-8");
			pp.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ProjectPath = pp.getProperty("WORKSPAC_PATH");
		TestCase = pp.getProperty("TESTCASE");
		CasePath = ProjectPath +"/testCase/"+ TestCase+".xlsx";
		//获取测试执行用例
		try {
			RunCase = IOMananger.runTime("TestCases", CasePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onTestStart(ITestResult  tr){
		log.info("测试用例:"+tr.getParameters()[1].toString()+"---Start");
	}
	
	@Override
	public void onTestFailure(ITestResult tr) {
		WebDriver driver = Run.driver;
		String CaseName = tr.getParameters()[1].toString();
		SeleniumScreenShot screenShot = new SeleniumScreenShot(driver);
		screenShot.setscreenName(CaseName);
		screenShot.takeScreenshot();
		driver.quit();
		log.error("测试用例:"+tr.getParameters()[1].toString()+"---End");
		log.info("--------------------------------------");
	}
	
	@Override
	public void onTestSuccess(ITestResult tr) {
		WebDriver driver = Run.driver;
		driver.quit();
		log.info("测试用例:"+tr.getParameters()[1].toString()+"---End");
		
	}
}
