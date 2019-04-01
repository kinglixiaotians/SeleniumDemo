package com.selenium.fuyou.transactionManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class transactionRecord {

    //region 查询交易记录

    @Test
    public void searchInvoice(WebDriver driver){
        try {
            //精确日期查询
            Thread.sleep(500);
            inputSearchDate(driver,"2019-02-19","2019-02-21");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('CompanyTransactionRecordList_startDate').removeAttribute('readonly')");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('CompanyTransactionRecordList_endDate').removeAttribute('readonly')");
            driver.findElement(By.id("CompanyTransactionRecordList_startDate")).clear();
            driver.findElement(By.id("CompanyTransactionRecordList_endDate")).clear();
            Thread.sleep(500);
            inputSearchDate(driver,"2019-02-19","2019-01-20");

            //模糊日期查询
            Thread.sleep(500);
            List<WebElement> liList = navList(driver,".xsdd_time_bm.index_1");
            for (int i = 1; i < liList.size(); i++) {
                liList = navList(driver,".xsdd_time_bm.index_1");
                Thread.sleep(500);
                liList.get(i).findElement(By.tagName("a")).click();
                Thread.sleep(500);
                driver.findElement(By.className("qyzx_search")).click();
            }

            //交易类型分类
            liList = navList(driver,".xsdd_time_bm.index_2");
            for (int i = 0; i < liList.size(); i++) {
                liList = navList(driver,".xsdd_time_bm.index_2");
                Thread.sleep(500);
                liList.get(i).findElement(By.tagName("a")).click();
            }
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //日期输入
    public void inputSearchDate(WebDriver driver,String startDate,String endDate){
        try{
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("document.getElementById('CompanyTransactionRecordList_startDate').removeAttribute('readonly')");
            driver.findElement(By.id("CompanyTransactionRecordList_startDate")).sendKeys(startDate);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("document.getElementById('CompanyTransactionRecordList_endDate').removeAttribute('readonly')");
            driver.findElement(By.id("CompanyTransactionRecordList_endDate")).sendKeys(endDate);
            Thread.sleep(500);
            driver.findElement(By.className("qyzx_search")).click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取导航列表
    public List<WebElement> navList(WebDriver driver,String ulPath){
        try {
            WebElement element = driver.findElement(By.cssSelector(ulPath));
            List<WebElement> liList = element.findElements(By.tagName("li"));
            return liList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    //endregion

}
