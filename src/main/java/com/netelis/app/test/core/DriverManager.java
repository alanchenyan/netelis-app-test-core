package com.netelis.app.test.core;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class DriverManager {

	private static AppiumDriverLocalService service;
	private static AndroidDriver<AndroidElement> driver;
	private static DriverManager driverManager = new DriverManager();
	private static String DEFAULT_URL = "http://0.0.0.0:4723/wd/hub";
	private static String appPackge;
	

	public static DriverManager getInstance() {
		return driverManager;
	}

	public static AndroidDriver<AndroidElement> getDriverInstance() {
		return driver;
	}
	
	public void buildDriver(DesiredCapabilities capabilities) {
		buildDriver(capabilities,DEFAULT_URL);
	}
	
	public void buildDriver(DesiredCapabilities capabilities,String url) {
	     try {
	    	appPackge = (String) capabilities.getCapability("appPackage");
			driver = new AndroidDriver<AndroidElement>(new URL(url), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	

	public void doQuit() {
		if (driver != null) {
			driver.quit();
		}

		if (service != null) {
			service.stop();
		}
	}

	public static String getAppPackge() {
		return appPackge;
	}
	
	
//	protected void buildDriver() {
//	DesiredCapabilities capabilities = new AndroidCapabilities().build();
//     try {
//		driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
//	} catch (MalformedURLException e) {
//		e.printStackTrace();
//	}
//}

//protected void buildDriver2() {
//	service = AppiumDriverLocalService.buildDefaultService();
//	service.start();
//
//	if (service == null || !service.isRunning()) {
//		throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
//	}
//
//	DesiredCapabilities capabilities = new AndroidCapabilities().build();
//	driver = new AndroidDriver<AndroidElement>(service.getUrl(), capabilities);
//}
	
}
