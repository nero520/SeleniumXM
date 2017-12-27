# SeleniumXM
基于selenium 的web自动化测试框架，执行将写好的操作步骤（例如：登录的步骤）存放到工程 src/main/java/com/xiaoM/PageObject 下，在Excel中进行测试场景组装即可进行测试
## 特点
### 支持多浏览器(更多支持可自行扩展)
* OSX 平台的 Safari、Chrome、Firefox
* Linux 平台的 Chrome、Firefox
* Win 平台的 Chrome、Firefox、IE、Edge、基于Chromium 内核的浏览器（例如 360浏览器、360极速浏览器、UC浏览器等等）
### 支持 Selenium Grid集群
* 只需修改工程目录下的 config.properties 中的 SELENIUM_GRID 和 SELENIUM_GRID_ADDRESS 参数即可实现快速部署
* 推荐结合 Docker 使用，[Docker 在 WebUI 自动化测试中的应用](https://testerhome.com/topics/8450)
### 支持多浏览器并行
* 通过修改工程目录下的 StartTest.xml 文件中的 data-provider-thread-count 参数即可实现多浏览器并行
# 测试报告
测试报告使用 Extentreports 生成,加入了每个测试用例的运行日志、失败截图、异常信息等，并更加浏览器进行分类展示，更多扩展：[Extentreports 官网](http://extentreports.com/)
![测试报告](https://github.com/xiaoMGitHub/home/blob/master/picture/SeleniumReport.png)
