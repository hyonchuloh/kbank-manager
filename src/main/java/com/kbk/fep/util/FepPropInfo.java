package com.kbk.fep.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
//@PropertySource({
//	"file:./config/txinfo.properties",
//	"file:./config/error.properties",
//	"file:./config/phone.properties"
//})
public class FepPropInfo {
	
	@Autowired
	private Environment env;
	
	public String getValue(String key) {
		return env.getProperty(key);
	}

}
