package com.xiaoM.Utils;

import org.openqa.selenium.WebDriver;

import com.xiaoM.ReportUtils.TestListener;

import java.util.HashMap;
import java.util.Map;

public class Run {
    Log log = new Log(this.getClass());

    public void runCase(String BrowserName, String Version, String CaseName) throws Exception {
        BaseDriver base = new BaseDriver();
        Map<String, Object> map = new HashMap<String, Object>();
        WebDriver driver = base.setUpWebDriver(BrowserName, Version);
        String BrowserVersion = BrowserName + "(" + Version + ")";
        String[][] testStart = null;
        try {
            testStart = IOMananger.readExcelDataXlsx(CaseName, TestListener.CasePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long StartTime = System.currentTimeMillis();
        try {
            for (int a = 1; a < testStart.length; a++) {
                if (testStart[a][0].equals("YES")) {
                    String ClassName = testStart[a][4].split("::")[0];
                    String MethodName = testStart[a][4].split("::")[1];
                    log.info(BrowserVersion + " MethodName:" + MethodName.split("\\(")[0]);
                    log.info(BrowserVersion + " PackageName:com.xiaoM.PageObject");
                    log.info(BrowserVersion + " ClassName:" + ClassName);
                    log.info(BrowserVersion + " 执行步骤：" + testStart[a][1]);
                    log.info(BrowserVersion + " 步骤名称：" + testStart[a][2]);
                    String Steps = testStart[a][1] + ":" + testStart[a][2];
                    ExecuteScript Method = new ExecuteScript(driver);
                    Object result = Method.runScript(ClassName, MethodName, map, Steps, BrowserVersion+"-" + CaseName);
                    if (result.equals(false)) {
                        throw new Exception(BrowserVersion + " 返回值为：false");
                    }
                    log.info(BrowserVersion + " 返回值:" + result);
                    log.info(BrowserVersion + " --------------------------------------");
                }
            }
            TestListener.runSuccessMessageList.add(BrowserVersion +"-"+CaseName);
            log.info(BrowserVersion +"  测试用例:"+ CaseName +"---End");
            long EndTime = System.currentTimeMillis();
            TestListener.RuntimeStart.put(BrowserVersion+"-"+CaseName,StartTime);
            TestListener.RuntimeEnd.put(BrowserVersion+"-"+CaseName,EndTime);
            driver.quit();
        } catch (Exception e) {
            TestListener.runFailMessageList.add(BrowserVersion+"-"+CaseName);
            SeleniumScreenShot screenShot = new SeleniumScreenShot(driver);
            screenShot.setScreenName(BrowserVersion,CaseName);
            screenShot.takeScreenshot();
            driver.quit();
            log.info(BrowserVersion+" --------------------------------------");
            log.info(BrowserVersion+"  测试用例:"+ CaseName +"---End");
            long EndTime = System.currentTimeMillis();
            TestListener.RuntimeStart.put(BrowserVersion+"-"+CaseName,StartTime);
            TestListener.RuntimeEnd.put(BrowserVersion+"-"+CaseName,EndTime);
            throw e;
        }
        driver.quit();
    }
}
