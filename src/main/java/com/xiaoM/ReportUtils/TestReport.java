package com.xiaoM.ReportUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
		extent.setReportUsesManualConfiguration(true);
	}  
	private void buildTestNodes(IResultMap tests, Status status) {
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				switch (result.getStatus()) {
				case 1://成功用例
					String caseName = TestListener.runMessageList.get(0);
					TestListener.runMessageList.remove(0);
					ExtentTest test = extent.createTest(caseName.split("::")[1]);
					test.assignCategory(caseName.split("::")[0]);
					test.getModel().setStartTime(getTime(result.getStartMillis()));
					test.getModel().setEndTime(getTime(result.getEndMillis()));
					test.log(status, "Test " + status.toString().toLowerCase() + "ed");
					break;
				case 2://失败用例
					String caseName2 = TestListener.runMessageList.get(0);
					TestListener.runMessageList.remove(0);
					ExtentTest test2 = extent.createTest(caseName2.split("::")[1]);
					test2.assignCategory(caseName2.split("::")[0]);
					test2.getModel().setStartTime(getTime(result.getStartMillis()));
					test2.getModel().setEndTime(getTime(result.getEndMillis()));
					try {
						test2.fail("报错截图：",MediaEntityBuilder.createScreenCaptureFromPath(TestListener.screenMessageList.get(0)).build());
						TestListener.screenMessageList.remove(0);
					} catch (IOException e) {
						e.printStackTrace();
					}
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
