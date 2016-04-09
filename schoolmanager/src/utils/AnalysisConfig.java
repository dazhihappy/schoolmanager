package utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class AnalysisConfig {
	private static Properties properties;
	/**
	 * @param args
	 * 用于读取配置的一个图表显示几天
	 */
	static {
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("analysis.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getProperty(String property) {
		return properties.getProperty(property);
	}
	
	public static void main(String[] args) {
		System.out.println(AnalysisConfig.getProperty("shownum"));
	}
	
}
