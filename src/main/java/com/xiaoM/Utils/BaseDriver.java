package com.xiaoM.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.xiaoM.ReportUtils.TestListener;

public class BaseDriver {

	public static WebDriver setUpWebDriver(String BrowserName){
		WebDriver driver = null;
		switch (BrowserName){
		case "Chrome" :
			switch(TestListener.OS){
			case "MAC":
				System.setProperty("webdriver.chrome.driver", "baseDriver/OSX/chromedriver");
				break;
			case "WINDOWS":
				System.setProperty("webdriver.chrome.driver", "baseDriver/WIN/chromedriver.exe");
				break;
			}
			driver = new ChromeDriver();
			break;
		case "Safari" :
			/*
			 * 1、需前往 http://www.seleniumhq.org/download/ 中下载 SafariDriver.safariextz 扩展并安装中 Safari 中
			 * 2、在 Safari 的开发菜单中允许远程自动化
			 */
			driver = new SafariDriver();
			break;
		}
		return driver;
	}
}
