package com.selenium.fuyou.transactionManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static com.selenium.fuyou.fuYouMethod.*;


public class electronicInvoice {

    //region 电子发票

//    @Test
    public void electronicInvoiceSearch(WebDriver driver){
        try {
            inputSearchDate(driver,"2019-01-19","2019-01-21","CompanyTransactionRecordList_startDate","CompanyTransactionRecordList_endDate");
            driver.findElement(By.className("qyzx_search")).click();
            clearDate(driver,"CompanyTransactionRecordList_startDate","CompanyTransactionRecordList_endDate");
            inputSearchDate(driver,"2019-02-27","2019-2-11","CompanyTransactionRecordList_startDate","CompanyTransactionRecordList_endDate");
            driver.findElement(By.className("qyzx_search")).click();

            Thread.sleep(500);
            List<WebElement> list = getNavList(driver,null,".xsdd_time_bm.index_1","li",2);
            for (int i = 1; i < list.size(); i++) {
                list = getNavList(driver,null,".xsdd_time_bm.index_1","li",2);
                list.get(i).findElement(By.tagName("a")).click();
                Thread.sleep(500);
                driver.findElement(By.className("qyzx_search")).click();
            }

            //下载
            boolean flag = isExistBoxOrExistButton(driver,"//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[3]/table/tbody/tr[2]/td[8]/a",3);
            if(flag){
                driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[3]/table/tbody/tr[2]/td[8]/a")).click();
            }
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

}
