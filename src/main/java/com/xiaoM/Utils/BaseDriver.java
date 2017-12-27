package com.xiaoM.Utils;

import com.xiaoM.ReportUtils.TestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseDriver {
	private Log log= new Log(this.getClass());
	final private static String url = TestListener.Selenium_Gird_Address;

	public  WebDriver setUpWebDriver(String ID,String BrowserName, String Version, String CaseName) throws Exception {
		WebDriver driver = null;
		String BrowserVersion = Version.equals("")||Version.isEmpty()?BrowserName:BrowserName+"("+Version+")";
		String TestCategory = ID+"_"+ CaseName +"_"+ BrowserVersion;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(BrowserName.toLowerCase());
		capabilities.setVersion(Version);
		switch (BrowserName.toLowerCase()){
		case "chrome" :
			if(TestListener.Selenium_Gird.toLowerCase().contains("true")){
				try {
					if(Version.equals("")||Version.isEmpty()){
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
					if(Version.equals("")||Version.isEmpty()){
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
					if(Version.equals("")||Version.isEmpty()){
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
						log.error(TestCategory +" 非 Windows 系统不支持 IE 浏览器自动化");
						log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
						log.error(TestCategory +" -----------------------------------");
						throw new Exception("非 Windows 系统不支持 IE 浏览器自动化");
					case "WINDOWS":
						System.setProperty("webdriver.ie.driver", "baseDriver/WIN/IEDriverServer.exe");
						break;
					case "LINUX":
						log.error(TestCategory +" 非 Windows 系统不支持 IE 浏览器自动化");
						log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
						log.error(TestCategory +" -----------------------------------");
						throw new Exception("非 Windows 系统不支持 IE 浏览器自动化");
				}
				driver = new InternetExplorerDriver();
			}
			break;
		case "edge":
			if(TestListener.Selenium_Gird.toLowerCase().contains("true")){
				try {
					if(Version.equals("")||Version.isEmpty()){
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
						log.error(TestCategory +" 非 Windows 系统不支持 Edge 浏览器自动化");
						log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
						log.error(TestCategory +" -----------------------------------");
						throw new Exception("非 Windows 系统不支持 Edge 浏览器自动化");
					case "WINDOWS":
						if(System.getProperty("os.name").contains("10")){
							System.setProperty("webdriver.edge.driver", "baseDriver/WIN/MicrosoftWebDriver.exe");
						}else{
							log.error(TestCategory +" 非 Windows 10 系统不支持Edge浏览器自动化");
							log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
							log.error(TestCategory +" -----------------------------------");
							throw new Exception("非 Windows 10 系统不支持Edge浏览器自动化");
						}
						break;
					case "LINUX":
						log.error(TestCategory +" 非 Windows 系统不支持 Edge 浏览器自动化");
						log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
						log.error(TestCategory +" -----------------------------------");
						throw new Exception("非 Windows 系统不支持 Edge 浏览器自动化");
				}
				driver = new EdgeDriver();
			}
			break;
		case "safari" :
			if(TestListener.Selenium_Gird.toLowerCase().contains("true")){
				try {
					if(Version.equals("")||Version.isEmpty()){
						driver = new RemoteWebDriver(new URL(url),DesiredCapabilities.safari());
					}else{
						driver = new RemoteWebDriver(new URL(url),capabilities);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}else{
				switch(TestListener.OS) {
					case "WINDOWS":
						log.error(TestCategory +" 非 OSX 系统不支持 Safari 浏览器自动化");
						log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
						log.error(TestCategory +" -----------------------------------");
						throw new Exception("非 OSX 系统不支持 Safari 浏览器自动化");
					case "LINUX":
						log.error(TestCategory +" 非 OSX 系统不支持 Safari 浏览器自动化");
						log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
						log.error(TestCategory +" -----------------------------------");
						throw new Exception("非 OSX 系统不支持 Safari 浏览器自动化");
				}
			/*
			 * 1、需前往 http://www.seleniumhq.org/download/ 中下载 SafariDriver.safariextz 扩展并安装中 Safari 中
			 * 2、在 Safari 的开发菜单中允许远程自动化
			 */
				driver = new SafariDriver();
			}
			break;
		case "other":
			String Browser_Path = TestListener.Browser_Path;
			if(Browser_Path.isEmpty()||Browser_Path.equals("")){
				log.error(TestCategory +" 请在 config.properties 中配置 BROWSER_PATH 的值");
				log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
				log.error(TestCategory +" -----------------------------------");
				throw new Exception("请在 config.properties 中配置 BROWSER_PATH 的值");
			}else if(!Browser_Path.isEmpty()){
				File filePath = new File(Browser_Path);
				if (!filePath.exists()){
					log.error(TestCategory +" 其他浏览器的安装路径不存在，请确认正确："+ Browser_Path);
					log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
					log.error(TestCategory +" -----------------------------------");
					throw new Exception("其他浏览器的安装路径不存在，请确认正确："+ Browser_Path);
				}
			}
			System.setProperty("webdriver.chrome.driver", "baseDriver/WIN/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.setBinary(TestListener.Browser_Path);
			driver = new ChromeDriver(options);
			break;
		default:
			log.error(TestCategory +" 指定执行的浏览器无法识别，请确认正确");
			log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
			log.error(TestCategory +" -----------------------------------");
			throw new Exception("指定执行的浏览器无法识别，请确认正确");
		}
		return driver;
	}
}
