package com.xiaoM.Utils;

import com.xiaoM.ReportUtils.TestListener;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class Run {
    Log log = new Log(this.getClass());

    public void runCase(String ID,String BrowserName, String Version, String CaseName) throws Exception {
        String BrowserVersion = Version.equals("")||Version.isEmpty()?BrowserName:BrowserName+"("+Version+")";
        String TestCategory = ID+"_"+ CaseName +"_"+ BrowserVersion;
        String[][] testStart = IOMananger.readExcelDataXlsx(CaseName, TestListener.CasePath);
        if(testStart!=null){
            BaseDriver base = new BaseDriver();
            Map<String, Object> map = new HashMap<String, Object>();
            WebDriver driver = base.setUpWebDriver(ID,BrowserName, Version,CaseName);
            long StartTime = System.currentTimeMillis();
            try {
                for (int a = 1; a < testStart.length; a++) {
                    if (testStart[a][0].equals("YES")) {
                        String ClassName = testStart[a][4].split("::")[0];
                        String MethodName = testStart[a][4].split("::")[1];
                        log.info(TestCategory + " MethodName:" + MethodName.split("\\(")[0]);
                        log.info(TestCategory + " PackageName:com.xiaoM.PageObject");
                        log.info(TestCategory + " ClassName:" + ClassName);
                        log.info(TestCategory + " 执行步骤：" + testStart[a][1]);
                        log.info(TestCategory + " 步骤名称：" + testStart[a][2]);
                        String Steps = testStart[a][1] + ":" + testStart[a][2];
                        ExecuteScript Method = new ExecuteScript(driver);
                        Object result = Method.runScript(ClassName, MethodName, map, Steps, TestCategory);
                        if (result.equals(false)) {
                            throw new Exception(TestCategory + " 返回值为：false");
                        }
                        log.info(TestCategory + " 返回值:" + result);
                        log.info(TestCategory + " -----------------------------------");
                    }
                }
                log.info(TestCategory +"  测试用例:"+ CaseName +"---End");
                long EndTime = System.currentTimeMillis();
                TestListener.RuntimeStart.put(TestCategory,StartTime);
                TestListener.RuntimeEnd.put(TestCategory,EndTime);
            } catch (Exception e) {
                ScreenShot screenShot = new ScreenShot(driver);
                screenShot.setScreenName(TestCategory);
                screenShot.takeScreenshot();
                log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
                log.error(TestCategory +" -----------------------------------");
                long EndTime = System.currentTimeMillis();
                TestListener.RuntimeStart.put(TestCategory,StartTime);
                TestListener.RuntimeEnd.put(TestCategory,EndTime);
                throw e;
            }finally {
                if (driver!=null){
                    driver.quit();
                }
            }
        }else{
            log.error("该测试用例:"+ CaseName +"在 "+TestListener.TestCase+".xlsx 中没有对应的命名的 sheet");
            log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
            log.error(TestCategory +" -----------------------------------");
            throw new Exception("该测试用例:"+ CaseName +"在 "+TestListener.TestCase+".xlsx 中没有对应的命名的 sheet");
        }
    }
}
