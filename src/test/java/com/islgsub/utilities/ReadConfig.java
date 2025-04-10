package com.islgsub.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	Properties properties; // object of prop

	// path of config.prop file
	String Path = "C:\\Users\\amol.gondkar\\eclipse-workspace-new1\\ISLG-Subscriber_New1\\Configurations\\config.properties";

	// constructor to intialise the prop.object

	public ReadConfig() {
		try {
			properties = new Properties();

			FileInputStream f1 = new FileInputStream(Path);

			properties.load(f1);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	// now lets create method for the 'kEYS'  to call there values from the config.prop

	public String getBaseURL() {

		String value = properties.getProperty("baseUrl");

		if (value != null)

			return value;
		else
			throw new RuntimeException("Url is not mentioned in the config File ");

	}

	public String getBrowser() {
		String value = properties.getProperty("browser");

		if (value != null)
			return value;

		else
			throw new RuntimeException(" browser value is not mentioned in the config file ");

	}

	public String getUId() {
		String username = properties.getProperty("username");
		if (username != null)
			return username;
		else
			throw new RuntimeException("email not specified in config file.");

	}
  
	
	public String getPassword() {
		String password = properties.getProperty("password");
		if (password != null)
			return password;
		else
			throw new RuntimeException("password not specified in config file.");

	}

}
