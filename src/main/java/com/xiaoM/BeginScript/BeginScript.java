package com.xiaoM.BeginScript;

import com.xiaoM.ReportUtils.TestListener;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Run;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class BeginScript{
	Log log= new Log(this.getClass());
	@DataProvider
	public Object[][] TestCases() throws IOException{
		return TestListener.RunCase;
	}

	@Test(dataProvider = "TestCases")
	public void runCase(String Description,String CaseName) throws Exception{
		String BrowserName = "Chrome";
		String Version = "58.0.3029.81";
		String BrowserVersion = BrowserName+"("+Version+")";
	    log.info(BrowserVersion + ": "+ Description);
		TestListener.BrowserNamelist.add(BrowserVersion);
		Run test = new Run();
		test.runCase(BrowserName,Version,CaseName);
	}

	/*@Test(dataProvider = "TestCases")
	public void runCase2(String Description,String CaseName) throws Exception{
		String BrowserName = "Chrome";
		String Version = "58.0.3029.81";
		String BrowserVersion = BrowserName+"("+Version+")";
		log.info(BrowserVersion + ": "+ Description);
		TestListener.BrowserNamelist.add(BrowserVersion);
		Run test = new Run();
		test.runCase(BrowserName,Version,CaseName);
	}*/
}
