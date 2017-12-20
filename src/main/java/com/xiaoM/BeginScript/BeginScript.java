package com.xiaoM.BeginScript;

import com.xiaoM.ReportUtils.TestListener;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Run;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class BeginScript{
	Log log= new Log(this.getClass());
	@DataProvider(parallel=true)
	public Object[][] TestCases() throws IOException{
		return TestListener.RunCase;
	}

	@Test(dataProvider = "TestCases")
	public void runCase(String ID,String Description,String CaseName,String BrowserName,String Version) throws Exception{
		String BrowserVersion = Version==null||Version.isEmpty()?BrowserName+"_"+ID:BrowserName+"("+Version+")_"+ID;
	    log.info(BrowserVersion + ": "+ Description);
		TestListener.BrowserNamelist.add(BrowserVersion);
		Run test = new Run();
		test.runCase(ID,BrowserName,Version,CaseName);
	}
}
