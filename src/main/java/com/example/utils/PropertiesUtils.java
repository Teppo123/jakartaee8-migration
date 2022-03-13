package com.example.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

	public static Properties getDefaultProperties() throws IOException {
		String name = "config.properties";
		Properties properties = new Properties();
		try (InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(name)) {
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("Properties file '" + name + "' not found!");
			}
		}
		return properties;
	}

}
