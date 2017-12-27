package com.xiaoM.Utils;

import com.google.common.io.Files;
import com.xiaoM.ReportUtils.TestListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 截图
 * @author XiaoM
 *
 */
public class ScreenShot{
	private WebDriver driver;
	private String TestCategory;
	Log log =new Log(this.getClass());
	public void setScreenName(String TestCategory){
		this.TestCategory = TestCategory;
	}
	public ScreenShot(WebDriver driver){
		this.driver = driver;
	}
	private void takeScreenshot(String screenPath) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(screenPath));
			log.error(TestCategory +" 错误截图："+screenPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void takeScreenshot() {
		String screenName =TestCategory;
		File dir = new File("test-output/snapshot");
		if (!dir.exists()){
			dir.mkdirs();
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd(HH.mm.ss)");
		String date = dateFormat.format(new Date());
		String path = "./snapshot/"+ screenName+ "_"+date+".jpg";
		TestListener.screenMessageList.put(screenName,path);
		String screenPath = dir.getAbsolutePath().concat("\\")+ screenName+ "_"+date+".jpg";
		takeScreenshot(screenPath);
	}

}
