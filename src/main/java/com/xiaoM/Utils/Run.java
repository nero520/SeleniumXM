package com.xiaoM.Utils;

import org.openqa.selenium.WebDriver;

import com.xiaoM.ReportUtils.TestListener;

import java.util.HashMap;
import java.util.Map;

public class Run {
	Log log= new Log(this.getClass());
	public void  runCase(String BrowserName,String Version,String CaseName) throws Exception{
		BaseDriver base = new BaseDriver();
		Map<String, Object> map = new HashMap<String, Object>();
		WebDriver driver = base.setUpWebDriver(BrowserName,Version);
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
				log.info(BrowserName+"("+Version+") MethodName:"+MethodName.split("\\(")[0]);
				log.info(BrowserName+"("+Version+") PackageName:com.xiaoM.PageObject");
				log.info(BrowserName+"("+Version+") ClassName:"+ClassName);
				log.info(BrowserName+"("+Version+") 执行步骤：" + testStart[a][1]);
				log.info(BrowserName+"("+Version+") 步骤名称：" + testStart[a][2]);
				String Steps = testStart[a][1]+":"+testStart[a][2];
				ExecuteScript Method = new ExecuteScript(driver);
				Object result = Method.runScript(ClassName,MethodName,map,Steps,BrowserName+"("+Version+")-"+CaseName);
				if(result.equals(false)){
					throw new Exception(BrowserName+"("+Version+") 返回值为：false");
				}
				log.info(BrowserName+"("+Version+") 返回值:" + result);
				log.info("--------------------------------------");
			}	
		}
		driver.quit();
	}
}
