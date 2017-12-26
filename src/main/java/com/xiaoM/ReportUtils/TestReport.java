package com.xiaoM.ReportUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.xiaoM.Utils.EnvironmentVersion;
import com.xiaoM.Utils.IOMananger;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestReport implements IReporter {
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd（HH.mm.ss）");
	static String date = dateFormat.format(new Date());
	private static final String OUTPUT_FOLDER = "test-output/";
	private static final String FILE_NAME = "TestReport "+date+".html";
	private ExtentReports extent;



	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		for(int i = 0; i<TestListener.BrowserVersion.size(); i++){ //处理日志文件
			IOMananger.DealwithRunLog(TestListener.BrowserVersion.get(i));
		}
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
		extent.setSystemInfo("Selenium Version", EnvironmentVersion.getVersionForPOM("org.seleniumhq.selenium"));
		extent.setReportUsesManualConfiguration(true);
	}
	private void buildTestNodes(IResultMap tests, Status status) {
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				String ID = result.getParameters()[0].toString();
				String CaseName = result.getParameters()[2].toString();
				String Version = result.getParameters()[4].toString();
				String BrowserName = Version.equals("")||Version.isEmpty()?result.getParameters()[3].toString():result.getParameters()[3]+"("+Version+")";
				String TestCategory = ID+"_"+ CaseName +"_"+ BrowserName;
				ExtentTest test = extent.createTest(TestCategory);
				test.assignCategory(BrowserName);//根据设备分类
				switch (result.getStatus()) {
					case 1://成功用例s
						test.getModel().setStartTime(getTime(TestListener.RuntimeStart.get(TestCategory)));
						test.getModel().setEndTime(getTime(TestListener.RuntimeEnd.get(TestCategory)));
						test.log(status, "Test " + status.toString().toLowerCase() + "ed");
						test.log(status,TestListener.logList.get(TestCategory));//测试日志

						break;
					case 2://失败用例
						if(TestListener.screenMessageList.containsKey(TestCategory)){
							test.getModel().setStartTime(getTime(TestListener.RuntimeStart.get(TestCategory)));
							test.getModel().setEndTime(getTime(TestListener.RuntimeEnd.get(TestCategory)));
							try {
								test.fail("报错截图：", MediaEntityBuilder.createScreenCaptureFromPath(TestListener.screenMessageList.get(TestCategory)).build());
							} catch (IOException e) {
								e.printStackTrace();
							}
							test.log(status, TestListener.failMessageList.get(TestCategory));  //添加自定义报错
							test.log(status,TestListener.logList.get(TestCategory));//测试日志
							test.log(status, TestListener.failThrowable.get(TestCategory)); //testng捕抓报错
							break;
						}else{
							test.log(status,TestListener.logList.get(TestCategory));//测试日志
							test.log(status,TestListener.failThrowable.get(TestCategory)); //testng捕抓报错
							break;
						}
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
