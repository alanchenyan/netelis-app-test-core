package com.netelis.app.test.core;

import static io.appium.java_client.touch.offset.PointOption.point;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
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
		Activity activity = new Activity(DriverManager.getAppPackge(), appActivity);
		DriverManager.getDriverInstance().startActivity(activity);
	}

	protected boolean doesWebElementExistById(String id) {
		try {
			findElementById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 连续点击某个按钮
	 * 
	 * @param viewType
	 * @param resId
	 * @param clickCount
	 * @return
	 */
	protected boolean quickClickBtn(String viewType, String resId, int clickCount) {
		AndroidElement secret = DriverManager.getDriverInstance()
				.findElementByXPath("//android.widget." + viewType + "[contains(@resource-id,'id/" + resId + "')]");
		int x = secret.getLocation().getX(); // 获取对象左上角的x点坐标
		int y = secret.getLocation().getY(); // 获取对象左上角的y点坐标

		for (int i = 1; i <= clickCount; i++) {
			long start = System.currentTimeMillis();
			tapByCoordinates(x, y);
			long end = System.currentTimeMillis();
			long duration = end - start;
			System.out.println("屏幕点直接单击: 第 " + i + " 次, 耗时: " + duration + "毫秒 !");
		}
		return true;
	}

	/**
	 * Tap by coordinates
	 * 
	 * @param x
	 * @param y
	 */
	private void tapByCoordinates(int x, int y) {
		new TouchAction(DriverManager.getDriverInstance()).tap(point(x, y)).perform();
	}
	
	protected MobileElement scrollToTextView(String scrolviewID, String textViewValue) {
		MobileElement element = DriverManager.getDriverInstance().findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\""+DriverManager.getAppPackge()+":id/" + scrolviewID + "\")).getChildByText("
				+ "new UiSelector().className(\"android.widget.TextView\"), \"" + textViewValue + "\")"));
		return element;
    }
	
	
	protected List<MobileElement> findElementsByIdInParents(String parentsId,Integer parentElementIndex,String childId) {
		List<MobileElement> elements = new ArrayList<MobileElement>();
		 List<AndroidElement> parents = DriverManager.getDriverInstance().findElementsById(parentsId);
		 if(parents.size() > parentElementIndex && parentElementIndex>=0) {
			 elements = parents.get(parentElementIndex).findElementsById(childId);
		 }
		return elements;
	}
}
