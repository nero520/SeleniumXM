package com.xiaoM.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.xiaoM.ReportUtils.TestListener;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseDriver {

	public  WebDriver setUpWebDriver(String BrowserName,String Version){
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
			/*DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(BrowserName);
			capabilities.setBrowserName(Version);
			try {
				driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub/"),capabilities.chrome());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}*/
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
