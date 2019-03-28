package com.selenium.flx.customService;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class customDetail {

    public String queryDetail(WebDriver driver,String customNo) {
        try {
            driver.switchTo().frame("mainframe");
            driver.findElement(By.id("customNo$text")).sendKeys(customNo);
            driver.findElement(By.xpath("//*[@id=\"shopScore\"]/span")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[@class=\"mini-messagebox-buttons\"]/a")).click();
            driver.close();
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

}
