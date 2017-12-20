package com.xiaoM.Utils;

import com.xiaoM.ReportUtils.TestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseDriver {
	Log log= new Log(this.getClass());
	final private static String url = TestListener.Selenium_Gird_Address;

	public  WebDriver setUpWebDriver(String BrowserName,String Version){
		WebDriver driver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(BrowserName.toLowerCase());
		capabilities.setVersion(Version);
		switch (BrowserName.toLowerCase()){
		case "chrome" :
			if(TestListener.Selenium_Gird.toLowerCase().contains("true")){
				try {
					if(Version==null||Version.isEmpty()){
						driver = new RemoteWebDriver(new URL(url),DesiredCapabilities.chrome());
					}else{
						driver = new RemoteWebDriver(new URL(url),capabilities);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}else{
				switch(TestListener.OS){
					case "MAC":
						System.setProperty("webdriver.chrome.driver", "baseDriver/OSX/chromedriver");
						break;
					case "WINDOWS":
						System.setProperty("webdriver.chrome.driver", "baseDriver/WIN/chromedriver.exe");
						break;
					case "LINUX":
						System.setProperty("webdriver.chrome.driver", "baseDriver/LINUX/chromedriver");
						break;
				}
				driver =  new ChromeDriver();
			}
			break;
		case"firefox" :
			if(TestListener.Selenium_Gird.toLowerCase().contains("true")){
				try {
					if(Version==null||Version.isEmpty()){
						driver = new RemoteWebDriver(new URL(url),DesiredCapabilities.firefox());
					}else{
						driver = new RemoteWebDriver(new URL(url),capabilities);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}else{
				switch(TestListener.OS){
					case "MAC":
						System.setProperty("webdriver.gecko.driver", "baseDriver/OSX/geckodriver");
						break;
					case "WINDOWS":
						System.setProperty("webdriver.gecko.driver", "baseDriver/WIN/geckodriver.exe");
						break;
					case "LINUX":
						System.setProperty("webdriver.gecko.driver", "baseDriver/LINUX/geckodriver");
						break;
				}
				driver =  new FirefoxDriver();
			}
			break;
		case "ie":
			if(TestListener.Selenium_Gird.toLowerCase().contains("true")){
				try {
					if(Version==null||Version.isEmpty()){
						driver = new RemoteWebDriver(new URL(url),DesiredCapabilities.internetExplorer());
					}else{
						driver = new RemoteWebDriver(new URL(url),capabilities);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}else{
				switch(TestListener.OS){
					case "MAC":
						log.error("非Windows系统不支持 IE 浏览器自动化");
						System.exit(0);
						break;
					case "WINDOWS":
						System.setProperty("webdriver.ie.driver", "baseDriver/WIN/IEDriverServer.exe");
						break;
					case "LINUX":
						log.error("非Windows系统不支持 IE 浏览器自动化");
						System.exit(0);
						break;
				}
				driver = new InternetExplorerDriver();
			}
			break;
		case "edge":
			if(TestListener.Selenium_Gird.toLowerCase().contains("true")){
				try {
					if(Version==null||Version.isEmpty()){
						driver = new RemoteWebDriver(new URL(url),DesiredCapabilities.edge());
					}else{
						driver = new RemoteWebDriver(new URL(url),capabilities);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}else{
				switch(TestListener.OS){
					case "MAC":
						log.error("非Windows系统不支持 Edge 浏览器自动化");
						System.exit(0);
						break;
					case "WINDOWS":
						if(System.getProperty("os.name").contains("10")){
							System.setProperty("webdriver.edge.driver", "baseDriver/WIN/MicrosoftWebDriver.exe");
						}else{
							log.error("非 Windows 10 系统不支持Edge浏览器自动化");
							System.exit(0);
						}
						break;
					case "LINUX":
						log.error("非Windows系统不支持 Edge 浏览器自动化");
						System.exit(0);
						break;
				}
				driver = new EdgeDriver();
			}
			break;
		case "safari" :
			if(TestListener.Selenium_Gird.toLowerCase().contains("true")){
				try {
					if(Version==null||Version.isEmpty()){
						driver = new RemoteWebDriver(new URL(url),DesiredCapabilities.chrome());
					}else{
						driver = new RemoteWebDriver(new URL(url),capabilities);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}else{
				switch(TestListener.OS) {
					case "WINDOWS":
						log.error("非OSX系统不支持 Safari 浏览器自动化");
						System.exit(0);
						break;
					case "LINUX":
						log.error("非OSX系统不支持 Safari 浏览器自动化");
						System.exit(0);
						break;
				}
			/*
			 * 1、需前往 http://www.seleniumhq.org/download/ 中下载 SafariDriver.safariextz 扩展并安装中 Safari 中
			 * 2、在 Safari 的开发菜单中允许远程自动化
			 */
				driver = new SafariDriver();
			}
			break;
		}
		return driver;
	}
}
