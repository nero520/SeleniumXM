package com.xiaoM.Utils;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;
import com.xiaoM.ReportUtils.TestListener;

/**
 * 截图
 * @author XiaoM
 *
 */
public class SeleniumScreenShot{
	private WebDriver driver;
	private String screenName;
	Log log =new Log(this.getClass());
	public void setscreenName(String screenName){
		this.screenName = screenName;
	}
	public SeleniumScreenShot(WebDriver driver){
		this.driver = driver;
	}
	private void takeScreenshot(String screenPath) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(screenPath));
			log.error("错误截图："+screenPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void takeScreenshot() {
		String screenName =this.screenName+ ".jpg";
		File dir = new File("test-output/snapshot");
		if (!dir.exists()){
			dir.mkdirs();
		}
		String path = "./snapshot/"+screenName;
		TestListener.screenMessageList.add(path);
		String screenPath = dir.getAbsolutePath() + "/" + screenName;
		takeScreenshot(screenPath);
	}

}
