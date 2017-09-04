package com.xiaoM.Utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xiaoM.ReportUtils.TestListener;

public class ExecuteScript  {
	/**
	 * 执行指定的方法
	 * @param MethodName 方法名
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 */
	public Object runScript(String ClassName,String MethodName){
		Object result = null;
		try {
			File file=new File(TestListener.ProjectPath+"/src/main/java");//类路径(包文件上一层)  
			URL url=file.toURI().toURL();  
			@SuppressWarnings("resource")
			ClassLoader loader = new URLClassLoader(new URL[]{url});//创建类加载器   
			Class<?> cls = loader.loadClass("com.xiaoM.PageObject."+ClassName);//加载指定类，注意一定要带上类的包名  
			Object obj = cls.newInstance();//初始化一个实例  
			if(MethodName.contains("(\"")){  
				Pattern p = Pattern.compile("(?<=\\()(.+?)(?=\\))"); 
				Matcher m = p.matcher(MethodName); 
				String data = null;
				Object[] args =null;
				while(m.find()) { 
					data = m.group().replace("\"", "");
				}
				if(data.contains(",")){
					args = data.split(",");
					for(int i=0;i<args.length;i++){
						if(args[i].toString().contains("Steps")){
							args[i] = TestListener.map.get(args[i]).toString();
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
						data = TestListener.map.get(data).toString();
					}
					result = cls.getMethod(MethodName.split("\\(")[0],String.class).invoke(obj,data);
				}	
			}else{
				MethodName = MethodName.replace("()", "");
				result = cls.getMethod(MethodName).invoke(obj);
			}	
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return result;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {	
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return result;	
	}
}
