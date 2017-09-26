package com.xiaoM.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Web163Mail {

    public boolean login163Mail(WebDriver driver,String userName,String passWord){
        driver.get("http://mail.163.com/");
        driver.manage().window().maximize();
        driver.switchTo().frame("x-URS-iframe");
        driver.findElement(By.xpath("//input[@data-placeholder='邮箱帐号或手机号']")).sendKeys(userName);
        driver.findElement(By.xpath("//input[@data-placeholder='密码']")).sendKeys(passWord);
        driver.findElement(By.xpath("//*[@id='dologin']")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  true;
    }
}
