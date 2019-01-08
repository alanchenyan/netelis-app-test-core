package com.netelis.app.test.core;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.List;
import org.openqa.selenium.By;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidElement;

public abstract class BaseUIAction {
	
	/**
	 * 用断言的方式校验是否是当前页面
	 */
	public abstract void assertExpectCurrentPage();
	
	/**
	 * 期望的页面
	 * 
	 * @param appActivity
	 */
	protected void assertExpectCurrentPage(String appActivity) {
		assertEquals(DriverManager.getDriverInstance().currentActivity(), appActivity);
	}

	/**
	 * 不期望的页面
	 * 
	 * @param appActivity
	 */
	protected void assertUnExpectCurrentPage(String appActivity) {
		assertNotEquals(DriverManager.getDriverInstance().currentActivity(), appActivity);
	}

	protected boolean isExpectPage(String appActivity) {
		boolean result = false;
		if (DriverManager.getDriverInstance().currentActivity().equals(appActivity)) {
			result = true;
		}
		return result;
	}

	protected String getViewTextById(String resId) {
		AndroidElement wl = findElementById(resId);
		return wl.getText();
	}

	protected void setValueById(String value, String resId) {
		AndroidElement wl = findElementById(resId);
		wl.clear();
		wl.sendKeys(value);
	}

	protected AndroidElement findElementById(String id) {
		return DriverManager.getDriverInstance().findElement(By.id(id));
	}
	
	protected List<AndroidElement> findElementsById(String id) {
		return DriverManager.getDriverInstance().findElementsById(id);
	}

	protected void doClickById(String resId) {
		findElementById(resId).click();
	}
	
	protected AndroidElement findElementByName(String value) {
		return DriverManager.getDriverInstance().findElementByAndroidUIAutomator("text(\"" + value + "\")");
	}

	protected void doClickByName(String value) {
		findElementByName(value).click();
	}

	protected void forwardPage(String appActivity) {
		Activity activity = new Activity(DriverManager.getAppPackge(),appActivity);
		DriverManager.getDriverInstance().startActivity(activity);
	}
}
