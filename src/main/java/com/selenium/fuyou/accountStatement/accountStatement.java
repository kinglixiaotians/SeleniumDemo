package com.selenium.fuyou.accountStatement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static com.selenium.fuyou.fuYouMethod.getNavList;
import static com.selenium.fuyou.fuYouMethod.inputSearchDate;

public class accountStatement {

    //region 对账单

    @Test
    public void searchStatement(WebDriver driver){
        try{
            Thread.sleep(500);
            driver.findElement(By.id("cardNo")).sendKeys("17607153713");
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".qyzx_search.qyzx_btn")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".qyzx_clear.qyzx_btn")).click();
            Thread.sleep(500);
            driver.findElement(By.id("outOrderNo")).sendKeys("20190305173732700183");
            driver.findElement(By.cssSelector(".qyzx_search.qyzx_btn")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".qyzx_clear.qyzx_btn")).click();


            Thread.sleep(500);
            inputSearchDate(driver,"2019-01-28","2019-01-29","CompanyStatement_acceptBeginDate","CompanyStatement_acceptEndDate");
            driver.findElement(By.cssSelector(".qyzx_search.qyzx_btn")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".qyzx_clear.qyzx_btn")).click();
            Thread.sleep(500);

            inputSearchDate(driver,"2019-02-25","2019-01-22","CompanyStatement_handleBeginDate","CompanyStatement_handleEndDate");
            driver.findElement(By.cssSelector(".qyzx_search.qyzx_btn")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".qyzx_clear.qyzx_btn")).click();
            Thread.sleep(500);

            getNavList(driver,".xsdd_time_bm.index_3");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

}
