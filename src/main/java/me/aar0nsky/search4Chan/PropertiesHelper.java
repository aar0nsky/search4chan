package me.aar0nsky.search4Chan;

import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class PropertiesHelper {

	private static Properties properties = new Properties();

	PropertiesHelper(String fileName) {
		loadFile(fileName);
	}

	private static void loadFile(String fileName) {
		try {
			properties.load(new FileInputStream(filePathBuilder(fileName)));
		} catch (Exception e) {
			System.out.println("Something went wrong in loadFile");
		}
	}

	private static String filePathBuilder(String configFileName) {
		String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			String decodedPath = URLDecoder.decode(path, "UTF-8");
			return decodedPath + configFileName;
		} catch (Exception e) {
			System.out.println("Something went wrong in filePathBuilder");
			return "";
		}
	}

	public String getPropertyValue(String propertyName){
		String value = properties.getProperty(propertyName);
		if(!StringUtils.isEmpty(value))
		{
			return value;
			}
		System.out.println("Soemthing went wrong in getPropertyValue");
		return "";
	}

}
