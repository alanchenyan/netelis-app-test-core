package com.netelis.app.test.core;

import static io.appium.java_client.touch.offset.PointOption.point;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

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
	 * 是否是当前页面
	 */
	public abstract void assertExpectCurrentPage();

	/**
	 * 期望的页面
	 * 
	 * @param appActivity
	 */
	protected void assertExpectCurrentPage(String appActivity) {
		if (DriverManager.getPlatform() == AppPlatformEnum.ANDROID) {
			assertEquals(DriverManager.getAndroidDriverInstance().currentActivity(), appActivity);
		} else {
			try {
				DriverManager.getiOSDriverInstance().findElementByAccessibilityId(appActivity);
				assertTrue(true);
			} catch (Exception e) {
				assertTrue(false);
			}
		}

	}

	/**
	 * 不期望的页面
	 * 
	 * @param appActivity
	 */
	protected void assertUnExpectCurrentPage(String appActivity) {

		if (DriverManager.getPlatform() == AppPlatformEnum.ANDROID) {
			assertNotEquals(DriverManager.getAndroidDriverInstance().currentActivity(), appActivity);
		} else {
			try {
				DriverManager.getDriverInstance().findElementByAccessibilityId(appActivity);
				assertTrue(false);
			} catch (Exception e) {
				assertTrue(true);
			}
		}
	}

	/**
	 * 是否是当前页面
	 * 
	 * @param appActivity
	 * @return
	 */
	protected boolean isExpectPage(String appActivity) {
		boolean result = false;
		if (DriverManager.getPlatform() == AppPlatformEnum.ANDROID) {
			if (DriverManager.getAndroidDriverInstance().currentActivity().equals(appActivity)) {
				result = true;
			}
		} else {
			try {
				DriverManager.getiOSDriverInstance().findElementByAccessibilityId(appActivity);
				result = true;
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 通过Id获取文本信息
	 * 
	 * @param resId
	 * @return
	 */
	protected String getViewTextById(String resId) {
		MobileElement wl = findElementById(resId);
		return wl.getText();
	}

	/**
	 * 给输入框设置值
	 * 
	 * @param value
	 * @param resId
	 */
	protected void setValueById(String value, String resId) {
		MobileElement wl = findElementById(resId);
		wl.clear();
		wl.sendKeys(value);
	}

	/**
	 * 通过Id获取单个元素
	 * 
	 * @param id
	 * @return
	 */
	protected MobileElement findElementById(String id) {
		return DriverManager.getDriverInstance().findElement(By.id(id));
	}

	/**
	 * 通过Id获取元素列表
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<MobileElement> findElementsById(String id) {
		return (List<MobileElement>) DriverManager.getDriverInstance().findElementsById(id);
	}

	/**
	 * 通过名称获取元素
	 * 
	 * @param value
	 * @return
	 */
	protected MobileElement findElementByName(String value) {
		if (DriverManager.getPlatform() == AppPlatformEnum.ANDROID) {
			return DriverManager.getAndroidDriverInstance().findElementByAndroidUIAutomator("text(\"" + value + "\")");
		} else {
			return DriverManager.getiOSDriverInstance().findElementByName(value);
		}
	}

	/**
	 * 通过Id触发元素点击
	 * 
	 * @param resId
	 */
	protected void doClickById(String resId) {
		findElementById(resId).click();
	}

	/**
	 * 通过名称触发元素点击
	 * 
	 * @param resId
	 */
	protected void doClickByName(String value) {
		findElementByName(value).click();
	}

	/**
	 * 调整页面 暂时只支持Android
	 * 
	 * @param appActivity
	 */
	protected void forwardPage(String appActivity) {
		if (DriverManager.getPlatform() == AppPlatformEnum.ANDROID) {
			Activity activity = new Activity(DriverManager.getAppPackge(), appActivity);
			DriverManager.getAndroidDriverInstance().startActivity(activity);
		} else {
			// TODO
		}
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
	 * 连续点击某个按钮 暂时只支持Android
	 * 
	 * @param viewType
	 * @param resId
	 * @param clickCount
	 * @return
	 */
	protected boolean quickClickBtn(String viewType, String resId, int clickCount) {
		if (DriverManager.getPlatform() == AppPlatformEnum.ANDROID) {
			AndroidElement secret = DriverManager.getAndroidDriverInstance()
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
		} else {
			// TODO
			return true;
		}
	}

	/**
	 * Tap by coordinates
	 * 
	 * @param x
	 * @param y
	 */
	@SuppressWarnings("rawtypes")
	private void tapByCoordinates(int x, int y) {
		new TouchAction(DriverManager.getDriverInstance()).tap(point(x, y)).perform();
	}

	/**
	 * 滚动到某一元素 暂时只支持Android
	 * 
	 * @param scrolviewID
	 * @param textViewValue
	 * @return
	 */
	protected MobileElement scrollToTextView(String scrolviewID, String textViewValue) {
		if (DriverManager.getPlatform() == AppPlatformEnum.ANDROID) {
			MobileElement element = DriverManager.getDriverInstance()
					.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\""
							+ DriverManager.getAppPackge() + ":id/" + scrolviewID + "\")).getChildByText("
							+ "new UiSelector().className(\"android.widget.TextView\"), \"" + textViewValue + "\")"));
			return element;
		} else {
			// TODO
			return null;
		}
	}

	/**
	 * 查找父元素下的子元素 暂时只支持Android
	 * 
	 * @param parentsId
	 * @param parentElementIndex
	 * @param childId
	 * @return
	 */
	protected List<MobileElement> findElementsByIdInParents(String parentsId, Integer parentElementIndex,
			String childId) {
		if (DriverManager.getPlatform() == AppPlatformEnum.ANDROID) {
			List<MobileElement> elements = new ArrayList<MobileElement>();
			List<AndroidElement> parents = DriverManager.getAndroidDriverInstance().findElementsById(parentsId);
			if (parents.size() > parentElementIndex && parentElementIndex >= 0) {
				elements = parents.get(parentElementIndex).findElementsById(childId);
			}
			return elements;
		} else {
			// TODO
			return null;
		}
	}

	/**
	 * 在控制台打印页面信息
	 */
	protected void outPutPageSource() {
		if (DriverManager.getPlatform() == AppPlatformEnum.ANDROID) {
			System.out.println(DriverManager.getAndroidDriverInstance().getPageSource());
		} else {
			System.out.println(DriverManager.getiOSDriverInstance().getPageSource());
		}
	}

}
