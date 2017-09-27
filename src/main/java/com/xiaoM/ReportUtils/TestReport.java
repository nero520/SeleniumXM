package com.xiaoM.ReportUtils;

import java.io.IOException;
import java.util.*;

import com.xiaoM.Utils.EnvironmentVersion;
import com.xiaoM.Utils.IOMananger;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestReport implements IReporter {

	private static final String OUTPUT_FOLDER = "test-output/";
	private static final String FILE_NAME = "TestReport.html";
	private ExtentReports extent;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {     
		init(TestListener.TestCase);//html文件配置
		for (ISuite suite : suites) {
			Map<String, ISuiteResult>  result = suite.getResults(); 
			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();              
				buildTestNodes(context.getFailedTests(), Status.FAIL);
				/* 
				 * 需要展示Skip用例执行该行代码
				 * buildTestNodes(context.getSkippedTests(), Status.SKIP);
				 */
				buildTestNodes(context.getPassedTests(), Status.PASS);

			}
		}     
		for (String s : Reporter.getOutput()) {
			extent.setTestRunnerOutput(s);
		}
		extent.flush();
		Set set = new HashSet();
		List newList = new  ArrayList();
		for (String cd:TestListener.BrowserNamelist) {
			if(set.add(cd)){
				newList.add(cd);
			}
		}
		for(int i=0;i<newList.size();i++){
			IOMananger.DealwithRunLog(newList.get(i).toString());
		}
	}  
	private void init(String ReportName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
		htmlReporter.config().setDocumentTitle("SeleniumXM designed by xiaoM");//html标题
		htmlReporter.config().setReportName(ReportName);//报告主题
		htmlReporter.config().setTheme(Theme.STANDARD);//主题：黑/白
		htmlReporter.config().setEncoding("utf-8");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter); 
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("Selenium Version", EnvironmentVersion.getVersionForPOM("org.seleniumhq.selenium"));
		extent.setReportUsesManualConfiguration(true);
	}
	private void buildTestNodes(IResultMap tests, Status status) {
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				switch (result.getStatus()) {
					case 1://成功用例s
						String DeviceAndCase = TestListener.runSuccessMessageList.get(0);
						TestListener.runSuccessMessageList.remove(0);
						ExtentTest test = extent.createTest(DeviceAndCase);
						test.assignCategory(DeviceAndCase.split("-")[0]);
						test.getModel().setStartTime(getTime(TestListener.RuntimeStart.get(DeviceAndCase)));
						test.getModel().setEndTime(getTime(TestListener.RuntimeEnd.get(DeviceAndCase)));
						test.log(status, "Test " + status.toString().toLowerCase() + "ed");
						break;
					case 2://失败用例
						String DeviceAndCase2 = TestListener.runFailMessageList.get(0);
						TestListener.runFailMessageList.remove(0);
						ExtentTest test2 = extent.createTest(DeviceAndCase2);
						test2.assignCategory(DeviceAndCase2.split("-")[0]);//根据设备分类
						test2.getModel().setStartTime(getTime(TestListener.RuntimeStart.get(DeviceAndCase2)));
						test2.getModel().setEndTime(getTime(TestListener.RuntimeEnd.get(DeviceAndCase2)));
						try {
							test2.fail("报错截图：",MediaEntityBuilder.createScreenCaptureFromPath(TestListener.screenMessageList.get(DeviceAndCase2)).build());
							TestListener.screenMessageList.remove(0);
						} catch (IOException e) {
							e.printStackTrace();
						}
						test2.log(status, TestListener.failMessageList.get(DeviceAndCase2));  //添加自定义报错
						TestListener.failMessageList.remove(0);
						test2.log(status, result.getThrowable()); //testng捕抓报错
						break;
				}
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();      
	}
}
