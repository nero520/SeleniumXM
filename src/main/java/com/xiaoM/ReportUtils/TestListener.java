package com.xiaoM.ReportUtils;

import com.xiaoM.Utils.IOMananger;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TestListener  extends TestListenerAdapter{

	public static String[][] RunCase;//执行测试case
	public static Map<String, String> screenMessageList = new HashMap<String, String>();
	public static Map<String, String> failMessageList = new HashMap<String, String>();
	public static Map<String, Long> RuntimeStart = new HashMap<String, Long>();
	public static Map<String, Long> RuntimeEnd = new HashMap<String, Long>();
	public static List<String> runSuccessMessageList = new ArrayList<String>();
	public static List<String> runFailMessageList = new ArrayList<String>();
	public static List<String> BrowserNamelist = new ArrayList<String>();
	public static String IP;
	public static String OS;
	public static String multithread;
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
		multithread = pp.getProperty("MULTITHREAD");
		IP = pp.getProperty("DOCKER_IP");
		CasePath = ProjectPath +"/testCase/"+ TestCase+".xlsx";
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
}
