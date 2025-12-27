package com.kbk.fep.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

public class PropUtil {
	
	public static Set<String> keySet(String propertiesFileName) {
		Set<String> keySet = null;
		ResourceBundle bundle = ResourceBundle.getBundle(propertiesFileName);
		if ( bundle != null ) {
			keySet = bundle.keySet();
		}
		return keySet;
	}
	
	public static String getPropertiesValue(String propertiesFileName, String keyName) {
		ResourceBundle bundle = ResourceBundle.getBundle(propertiesFileName);
		if ( bundle != null && bundle.containsKey(keyName) ) {
			return bundle.getString(keyName);
		} else {
			return null;
		}
	}
	
	public static int getPropertiesValueInt(String propertiesFileName, String keyName) {
		return Integer.parseInt(getPropertiesValue(propertiesFileName, keyName));
	}
	
	public static String getPropertiesValue(String keyName) {
		return getPropertiesValue("config", keyName);
	}
	
	public static Properties load(String propsName) {
		Properties props = new Properties();
		URL url = ClassLoader.getSystemResource(propsName);
		try {
			props.load(url.openStream());
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return props;
	}
	
	public static Properties load(File propsFiles) {
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propsFiles);
			props.load(fis);
		} catch ( IOException e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( fis != null ) 
					fis.close();
			} catch ( Exception e2 ) {
				e2.printStackTrace();
			}
		}
		return props;
	}
	
}
