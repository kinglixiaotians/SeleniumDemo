package com.selenium.fuyou;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class fuYouMethod {

    //日期输入
    public static void inputSearchDate(WebDriver driver, String startDate, String endDate, String startDateJSPath, String endDateJSPath){
        try{
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("document.getElementById('"+startDateJSPath+"').removeAttribute('readonly')");
            driver.findElement(By.id(startDateJSPath)).sendKeys(startDate);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("document.getElementById('"+endDateJSPath+"').removeAttribute('readonly')");
            driver.findElement(By.id(endDateJSPath)).sendKeys(endDate);
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //清空日期输入
    public static void clearDate(WebDriver driver, String startDateJSPath, String endDateJSPath){
        try{
            ((JavascriptExecutor) driver).executeScript("document.getElementById('"+startDateJSPath+"').removeAttribute('readonly')");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('"+endDateJSPath+"').removeAttribute('readonly')");
            Thread.sleep(500);
            driver.findElement(By.id(startDateJSPath)).clear();
            Thread.sleep(500);
            driver.findElement(By.id(endDateJSPath)).clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取导航列表
    public static List<WebElement> getNavList(WebDriver driver, String ulPath){
        try {
            WebElement element = driver.findElement(By.cssSelector(ulPath));
            List<WebElement> liList = element.findElements(By.tagName("li"));
            return liList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
