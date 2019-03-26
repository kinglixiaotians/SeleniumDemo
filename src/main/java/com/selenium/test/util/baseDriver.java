package com.selenium.test.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;

/**
 * Created by 李啸天 on 2019/3/22.
 */
public class baseDriver {

    WebDriver driver;

    public baseDriver(){
        System.setProperty("webdriver.chrome.driver", "E:\\2019\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void taskScreenShot(){
        long date = System.currentTimeMillis();
        String path = String.valueOf(date);
        String cusPath = System.getProperty("user.dir");
        path = path+".png";
        String screenPath = cusPath+"/"+path;
        System.out.println(screenPath);
        //实现截图
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,new File(screenPath));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
