package com.xiaoM.BeginScript;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xiaoM.ReportUtils.TestListener;
import com.xiaoM.Utils.Run;

public class BeginScript{
	
	@DataProvider
	public Object[][] TestCases() throws IOException{
		return TestListener.RunCase;
	}

	@Test(dataProvider = "TestCases")
	public void runCase(String c,String CaseName) throws Exception{
		String BrowserName = "Chrome";
		String Version = "58.2.342.42";
		Run test = new Run();
		test.runCase(BrowserName,Version,CaseName);
	}
}
