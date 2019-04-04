package com.selenium.flx.custom;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class editCustom {
    public String customNo;
    /**
     * 客户管理 查询用户
     *
     * @param driver
     */
    //@Test
    public void queryCustom(WebDriver driver, String customNo) {
        try {
            //隐式等待,二十秒内不出现就报错
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            this.customNo=customNo;
            driver.switchTo().frame("mainframe");
            driver.findElement(By.id("customNo$text")).click();
            driver.findElement(By.id("customNo$text")).sendKeys(customNo);
            Thread.sleep(1000);
            driver.findElement(By.className("search-condition"));
            driver.findElement(By.id("queryForm"));
            driver.findElement(By.className("mini-button-text")).click();
            driver.switchTo().defaultContent();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 客户管理 企业审核
     *
     * @param driver
     */
    //@Test
    public boolean auditCustom(WebDriver driver) {
        try {
            Thread.sleep(1000);
            driver.switchTo().frame("mainframe");
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            //点击修改按钮，弹出修改界面
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-edit")).click();
            //返回主窗体，进入修改页面
            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/custom/cusprofile/editCusProfileNew.jsp')]")));
            //企业审核
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-goto")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-119")).click();
            driver.switchTo().defaultContent();
            log.info("客户管理--企业:{}已审核",customNo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("客户管理--企业:{}审核失败",customNo);
            return false;
        }
    }

    /**
     * 修改客户信息
     *
     * @param driver
     */
    //@Test
    public boolean UpdateCustom(WebDriver driver) {
        try {
            driver.switchTo().frame("mainframe");
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            Thread.sleep(1000);
            //点击修改按钮，弹出修改界面
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-edit")).click();
            //返回主窗体，进入修改页面
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/custom/cusprofile/editCusProfileNew.jsp')]")));
            //修改为错误的身份证
            driver.findElement(By.id("entity.contactPersonIdcard$text")).clear();
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("taw4esay43eyt");
            //主站需要进行实名认证,点击后提示输入的身份证号码有误---点击alert继续
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@class='mini-messagebox-buttons']//a")).click();
            //输入正确的身份证号
            driver.findElement(By.id("entity.contactPersonIdcard$text")).clear();
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("420984199701051755");
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(2000);
            //认证失败---点击alert继续
            driver.findElement(By.xpath("//*[@class='mini-messagebox-buttons']//a")).click();
            //保存
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@class='mini-messagebox-buttons']//a[1]")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-127")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@class='mini-messagebox-buttons']//a[2]")).click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
