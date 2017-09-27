package com.xiaoM.Utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

public class EnvironmentVersion {
	/**
	 * 根据依赖库的组织名，在 pom.xml，获取其版本号
	 * @param groupID 依赖库的组织名
	 * @return 依赖库的版本
	 */
	public static String getVersionForPOM(String groupID) {
		//创建SAXReader对象
		SAXReader reader = new SAXReader();
		//读取文件 转换成Document
		Document document = null;
		try {
			document = reader.read(new File("pom.xml"));
		} catch (DocumentException e) {
			return null;
		}
		//获取根节点元素对象
		Element root = document.getRootElement();
		//获取 pom.xml 文件中依赖库所有的信息
		List<Element> libs = root.element("dependencies").elements("dependency");
		String version = null;
		for (int i = 0; i < libs.size(); i ++) {
			if (libs.get(i).element("groupId").getText().contains(groupID)) {
				version = libs.get(i).element("version").getText();
			}
		}
		return version;
	}
}
