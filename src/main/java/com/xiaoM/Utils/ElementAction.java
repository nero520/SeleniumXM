package com.xiaoM.Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author XiaoM
 */
public class ElementAction{

	/**
	 * 等待元素出现
	 * @param by
	 * @param timeOut
	 * @return
	 */
	public static WebElement waitForElement(WebDriver driver,By by,int timeOut){
		WebElement webElement=(new WebDriverWait(driver, timeOut)).until(
				new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver dr) {
						return  dr.findElement(by);
					}
				});
		return webElement;
	}

	/**
	 * 查找一组元素
	 * @param driver
	 * @param by
	 * @param timeOut
	 * @return
	 */
	public static List<WebElement>  waitForElements(WebDriver driver,By by,int timeOut){
		List<WebElement>  webElements=(new WebDriverWait(driver, timeOut)).until(
				new ExpectedCondition<List<WebElement>>() {
					@Override
					public List<WebElement> apply(WebDriver dr) {
						List<WebElement> element = dr.findElements(by);
						return element;
					}
				});
		return webElements;
	}
}
