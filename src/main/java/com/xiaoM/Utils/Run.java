package com.xiaoM.Utils;

import org.openqa.selenium.WebDriver;

import com.xiaoM.ReportUtils.TestListener;

public class Run {
	Log log= new Log(this.getClass());
	public static WebDriver driver = null;
	public void  runCase(String BrowserName,String CaseName) throws Exception{
		TestListener.map.clear();
		driver = BaseDriver.setUpWebDriver(BrowserName);
		String[][] testStart = null;
		try {
			testStart = IOMananger.readExcelDataXlsx(CaseName,TestListener.CasePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int a=1;a<testStart.length;a++){
			if(testStart[a][0].equals("YES")){
				String ClassName = testStart[a][4].split("::")[0];
				String MethodName = testStart[a][4].split("::")[1];
				log.info("MethodName:"+MethodName.split("\\(")[0]);
				log.info("PackageName:com.xiaoM.PageObject");
				log.info("ClassName:"+ClassName);
				log.info("执行步骤：" + testStart[a][1]);
				log.info("步骤名称：" + testStart[a][2]);
				String Steps = testStart[a][1]+":"+testStart[a][2];
				ExecuteScript Method = new ExecuteScript();
				Object result = Method.runScript(ClassName,MethodName,Steps);
				TestListener.map.put(testStart[a][1], result);
				if(result.equals(false)){
					throw new Exception("返回值为：false");
				}
				log.info("返回值:" + result);
				log.info("--------------------------------------");
			}	
		}
	}
}
