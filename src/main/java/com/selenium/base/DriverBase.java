package com.selenium.base;

import com.selenium.utils.PropertiesConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by 李啸天 on 2019/3/27.
 * 基类
 */
public class DriverBase {
    public WebDriver driver;

    //key
    public String key = PropertiesConfig.getInstance().getProperty("driver.url");

    public WebDriver driverName(){
        System.setProperty("webdriver.chrome.driver",key);
        return new ChromeDriver();
    }
    /**
     * 获取driver
     * */
    public WebDriver getDriver() {
        return driver;
    }
}
