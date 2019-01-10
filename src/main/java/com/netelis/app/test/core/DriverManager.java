package com.netelis.app.test.core;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class DriverManager {

	private static AndroidDriver<AndroidElement> androidDriver;
	private static IOSDriver<IOSElement> iOSDriver;
	
	private static DriverManager driverManager = new DriverManager();
	private static String DEFAULT_URL = "http://0.0.0.0:4723/wd/hub";
	private static String appPackge;
	private static AppPlatformEnum platform;
	private static AppPlatformEnum DEFAULT_PLATFORM = AppPlatformEnum.ANDROID;
	

	public static DriverManager getInstance() {
		return driverManager;
	}
	
	public static AndroidDriver<AndroidElement> getAndroidDriverInstance() {
		return androidDriver;
	}

	public static IOSDriver<IOSElement> getiOSDriverInstance() {
		return iOSDriver;
	}

	public static AppiumDriver<? extends MobileElement> getDriverInstance() {
		if (platform == AppPlatformEnum.ANDROID) {
			return androidDriver;
		} else {
			return iOSDriver;
		}
	}

	public void buildDriver(DesiredCapabilities capabilities) {
		buildDriver(capabilities,DEFAULT_URL,DEFAULT_PLATFORM);
	}
	
	public void buildDriver(DesiredCapabilities capabilities,AppPlatformEnum appPlatform) {
		buildDriver(capabilities,DEFAULT_URL,appPlatform);
	}
	
	protected void buildDriver(DesiredCapabilities capabilities,String url,AppPlatformEnum appPlatform) {
		platform = appPlatform;
		if (platform == AppPlatformEnum.ANDROID) {
			buildAndroidDriver(capabilities,url);
		} else {
			buildiOSDriver(capabilities,url);
		}
	}
	
	
	private void buildAndroidDriver(DesiredCapabilities capabilities,String url) {
		appPackge = (String) capabilities.getCapability("appPackage");
		try {
			androidDriver = new AndroidDriver<AndroidElement>(new URL(url), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void buildiOSDriver(DesiredCapabilities capabilities,String url) {
		try {
			iOSDriver = new IOSDriver<>(new URL(url), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void doQuit() {
		
		if (platform == AppPlatformEnum.ANDROID) {
			if (androidDriver != null) {
				androidDriver.quit();
			}
		} else {
			if (iOSDriver != null) {
				iOSDriver.quit();
			}
		}

	}

	public static String getAppPackge() {
		return appPackge;
	}

	public static AppPlatformEnum getPlatform() {
		return platform;
	}
	
}
