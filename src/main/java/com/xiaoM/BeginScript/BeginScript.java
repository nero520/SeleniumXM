package com.xiaoM.BeginScript;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xiaoM.ReportUtils.TestListener;
import com.xiaoM.Utils.Run;

public class BeginScript{
	
	@DataProvider
	public String[][]TestCases() throws IOException{
		return TestListener.RunCase;
	}

	@Test(dataProvider = "TestCases")
	public void runCase(String BrowserName,String CaseName) throws Exception{
		Run test = new Run();
		TestListener.runMessageList.add(BrowserName+"::"+CaseName);
		test.runCase(BrowserName,CaseName);
	}
}
