package com.selenium.flx.customService;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

@Slf4j
public class customDetail {

    /**
     * 客服明细查询 查询客户当前优分余额
     *
     * @param driver
     * @param customNo
     * @return
     */
    //@Test
    public boolean queryDetail(WebDriver driver, String customNo) {
        try {
            //隐式等待,二十秒内不出现就报错
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.switchTo().frame("mainframe");
            driver.findElement(By.id("customNo$text")).sendKeys(customNo);
            driver.findElement(By.xpath("//*[@id=\"shopScore\"]/span")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[@class=\"mini-messagebox-buttons\"]/a")).click();
            driver.switchTo().defaultContent();
            driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[1]/p/span[2]/a[2]")).click();
            log.warn("客服明细查询--企业：{}开户成功", customNo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
