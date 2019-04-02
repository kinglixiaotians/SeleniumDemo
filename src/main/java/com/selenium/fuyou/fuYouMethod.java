package com.selenium.fuyou;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
    //byState：0 id,1 className,2 cssSelector,3 xPath
    public static List<WebElement> getNavList(WebDriver driver, String ulPath,String findTagName,int byState){
        try {
            WebElement element = null;
            switch (byState){
                case 0:element = driver.findElement(By.id(ulPath));break;
                case 1:element = driver.findElement(By.className(ulPath));break;
                case 2:element = driver.findElement(By.cssSelector(ulPath));break;
                case 3:element = driver.findElement(By.xpath(ulPath));break;
            }
            List<WebElement> liList = element.findElements(By.tagName(findTagName));
            return liList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //判断下拉列表是否超出
    public static int trySelectGet(WebDriver driver,String path,int num){
        try{
            WebElement ele = driver.findElement(By.id(path));
            Select downList = new Select(ele);
            downList.selectByIndex(num);
            return num;
        }catch (Exception e){
            return 0;
        }
    }

}
