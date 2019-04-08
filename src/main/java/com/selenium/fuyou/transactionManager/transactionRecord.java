package com.selenium.fuyou.transactionManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static com.selenium.fuyou.fuYouMethod.*;

public class transactionRecord {

    //region 查询交易记录

//    @Test
    public void transactionRecordSearch(WebDriver driver){
        try {
            //精确日期查询
            Thread.sleep(500);
            inputSearchDate(driver,"2019-02-19","2019-02-21","CompanyTransactionRecordList_startDate","CompanyTransactionRecordList_endDate");
            Thread.sleep(500);
            driver.findElement(By.className("qyzx_search")).click();
            Thread.sleep(500);
            clearDate(driver,"CompanyTransactionRecordList_startDate","CompanyTransactionRecordList_endDate");
            Thread.sleep(500);
            inputSearchDate(driver,"2019-02-19","2019-01-20","CompanyTransactionRecordList_startDate","CompanyTransactionRecordList_endDate");
            Thread.sleep(500);
            driver.findElement(By.className("qyzx_search")).click();

            //模糊日期查询
            Thread.sleep(500);
            List<WebElement> liList = getNavList(driver,null,".xsdd_time_bm.index_1","li",2);
            for (int i = 1; i < liList.size(); i++) {
                liList = getNavList(driver,null,".xsdd_time_bm.index_1","li",2);
                Thread.sleep(500);
                liList.get(i).findElement(By.tagName("a")).click();
                Thread.sleep(500);
                driver.findElement(By.className("qyzx_search")).click();
            }

            //交易类型分类
            liList = getNavList(driver,null,".xsdd_time_bm.index_2","li",2);
            for (int i = 0; i < liList.size(); i++) {
                liList = getNavList(driver,null,".xsdd_time_bm.index_2","li",2);
                Thread.sleep(500);
                liList.get(i).findElement(By.tagName("a")).click();
            }
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

}
