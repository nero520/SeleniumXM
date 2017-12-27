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
		String BrowserVersion = Version.equals("")||Version.isEmpty()?BrowserName:BrowserName+"("+Version+")";
		String TestCategory = ID+"_"+ CaseName +"_"+ BrowserVersion;
		log.info(TestCategory + " "+ Description);
		TestListener.Category.add(TestCategory);
		Run test = new Run();
		test.runCase(ID,BrowserName,Version,CaseName);
	}
}
