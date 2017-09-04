package com.xiaoM.PageObject;

import org.openqa.selenium.By;

import com.xiaoM.Utils.ElementAction;
import com.xiaoM.Utils.Run;

public class Test extends Run{
	
	
	public String Demo(String http,String ss){
		driver.get(http);
		System.out.println(ss);
		ElementAction.waitForElement(driver, By.id("sdd"), 5).click();
		return "sjsjsj";
	}
	public  String Demo2(String n,String m){
		System.out.println(n);
		System.out.println(m);
		return "http://www.hao123.com";
	}
	
	public boolean Demo3(String http){
		driver.get(http);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
}
