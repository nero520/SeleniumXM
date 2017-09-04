package com.xiaoM.Utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.xiaoM.ReportUtils.TestListener;

public class Run {
	Log log= new Log(this.getClass());
	public static WebDriver driver = null;
	public void  runCase(String BrowserName,String CaseName) throws Exception{
		TestListener.map.clear();
		driver = BaseDriver.setUpWebDriver(BrowserName);
		try {
			String[][] testStart = IOMananger.readExcelDataXlsx(CaseName,TestListener.CasePath);
			for(int a=1;a<testStart.length;a++){
				if(testStart[a][0].equals("YES")){
					log.info("执行步骤：" + testStart[a][1]);
					log.info("步骤名称：" + testStart[a][2]);
					String ClassName = testStart[a][4].split("::")[0];
					String MethodName = testStart[a][4].split("::")[1];
					ExecuteScript Method = new ExecuteScript();
					Object result = Method.runScript(ClassName,MethodName);	
					TestListener.map.put(testStart[a][1], result);
					log.info("返回值:" + result);
					log.info("--------------------------------------");
					if(result.equals(false)){
						throw new Exception("返回值为：false");
					}
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
