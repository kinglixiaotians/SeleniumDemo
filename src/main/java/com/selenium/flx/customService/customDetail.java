package com.selenium.flx.customService;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
            driver.switchTo().frame("mainframe");
            driver.findElement(By.id("customNo$text")).sendKeys(customNo);
            driver.findElement(By.xpath("//*[@id=\"shopScore\"]/span")).click();
            Thread.sleep(3000);
            String str = driver.findElement(By.className("mini-messagebox-content-text")).getText();
            driver.findElement(By.xpath("//*[@class=\"mini-messagebox-buttons\"]/a")).click();
            driver.switchTo().defaultContent();
            driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[1]/p/span[2]/a[2]")).click();
            log.warn("客服明细查询--企业：{}，{}", customNo,str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
