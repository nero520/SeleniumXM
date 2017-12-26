package com.xiaoM.Utils;

import com.xiaoM.ReportUtils.TestListener;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecuteScript {
	public WebDriver driver;
	public ExecuteScript(WebDriver driver) {
		this.driver = driver;
	}
	public Object runScript(String ClassName,String MethodName,Map<String, Object> map,String Steps,String TestCategory){
		Object result = null;
		try {
			File file=new File(TestListener.ProjectPath+"/src/main/java");//类路径(包文件上一层)
			URL url=file.toURI().toURL();
			ClassLoader loader = new URLClassLoader(new URL[]{url});//创建类加载器   
			Class<?> cls = loader.loadClass("com.xiaoM.PageObject."+ClassName);//加载指定类，注意一定要带上类的包名  
			Object obj = cls.newInstance();//初始化一个实例  
			Pattern p = Pattern.compile("(?<=\\()(.+?)(?=\\))"); 
			Matcher m = p.matcher(MethodName); 
			String data = null;
			Object[] args;
			while(m.find()) { 
				data = m.group().replace("\"", "");
			}
			if(data!=null){
				if(data.contains("driver")){
					if(data.contains(",")){	
						String[] datas = data.split(",");
						args = new Object[datas.length];
						args[0] = driver;
						for(int i=1;i<datas.length;i++){
							args[i] = datas[i];
						}
						for(int i=1;i<args.length;i++){
							if(args[i].toString().contains("Steps")){
								args[i] = map.get(args[i]).toString();
							}
						}
						@SuppressWarnings("rawtypes")
						Class[] argsClass = new Class[args.length];  
						argsClass[0] = WebDriver.class;
						for (int i = 1, j = args.length; i < j; i++) {      
							argsClass[i] = args[i].getClass();                 
						}      
						result = cls.getMethod(MethodName.split("\\(")[0],argsClass).invoke(obj,args);	
					}else{
						MethodName = MethodName.replace("(\"driver\")", "");
						result = cls.getMethod(MethodName,WebDriver.class).invoke(obj,driver);
					}	
				}else{
					if(data.contains(",")){
						args = data.split(",");
						for(int i=0;i<args.length;i++){
							if(args[i].toString().contains("Steps")){
								args[i] = map.get(args[i]).toString();
							}
						}
						@SuppressWarnings("rawtypes")
						Class[] argsClass = new Class[args.length];      
						for (int i = 0, j = args.length; i < j; i++) {      
							argsClass[i] = args[i].getClass();                 
						}      
						result = cls.getMethod(MethodName.split("\\(")[0],argsClass).invoke(obj,args);	
					}else{
						if(data.contains("Steps")){
							data = map.get(data).toString();
						}
						result = cls.getMethod(MethodName.split("\\(")[0],String.class).invoke(obj,data);
					}
				}
			}else{
				MethodName = MethodName.replace("()", "");
				result = cls.getMethod(MethodName).invoke(obj);
			}
		} catch (IllegalArgumentException e) {
			TestListener.failMessageList.put(TestCategory,"【出错步骤】 > "+Steps);
			e.printStackTrace();
		} catch (SecurityException e) {
			TestListener.failMessageList.put(TestCategory,"【出错步骤】 > "+Steps);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			TestListener.failMessageList.put(TestCategory,"【出错步骤】 > "+Steps);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			TestListener.failMessageList.put(TestCategory,"【出错步骤】 > "+Steps);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {	
			TestListener.failMessageList.put(TestCategory,"【出错步骤】 > "+Steps);
			e.printStackTrace();
		} catch (MalformedURLException e) {
			TestListener.failMessageList.put(TestCategory,"【出错步骤】 > "+Steps);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			TestListener.failMessageList.put(TestCategory,"【出错步骤】 > "+Steps);
			e.printStackTrace();
		} catch (InstantiationException e) {
			TestListener.failMessageList.put(TestCategory,"【出错步骤】 > "+Steps);
			e.printStackTrace();
		}
		return result;	
	}
}
