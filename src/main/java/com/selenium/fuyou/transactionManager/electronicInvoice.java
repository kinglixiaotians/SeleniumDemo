package com.selenium.fuyou.transactionManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static com.selenium.fuyou.fuYouMethod.clearDate;
import static com.selenium.fuyou.fuYouMethod.getNavList;
import static com.selenium.fuyou.fuYouMethod.inputSearchDate;


public class electronicInvoice {

    //region 电子发票

    @Test
    public void electronicInvoiceSearch(WebDriver driver){
        try {
            inputSearchDate(driver,"2019-01-19","2019-01-21","CompanyTransactionRecordList_startDate","CompanyTransactionRecordList_endDate");
            clearDate(driver,"CompanyTransactionRecordList_startDate","CompanyTransactionRecordList_endDate");
            inputSearchDate(driver,"2019-02-27","2019-2-11","CompanyTransactionRecordList_startDate","CompanyTransactionRecordList_endDate");

            Thread.sleep(500);
            List<WebElement> list = getNavList(driver,".xsdd_time_bm.index_1");
            for (int i = 1; i < list.size(); i++) {
                list = getNavList(driver,".xsdd_time_bm.index_1");
                list.get(i).findElement(By.tagName("a")).click();
                Thread.sleep(500);
                driver.findElement(By.className("qyzx_search")).click();
            }

            //下载
            boolean flag = isExistdownlaodButton(driver);
            if(flag){
                driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[3]/table/tbody/tr[2]/td[8]/a")).click();
            }
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //判断是否有下载按钮
    public boolean isExistdownlaodButton(WebDriver driver){
        try{
            driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[3]/table/tbody/tr[2]/td[8]/a"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //endregion

}
