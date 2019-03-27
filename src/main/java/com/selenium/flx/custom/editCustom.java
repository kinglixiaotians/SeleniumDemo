package com.selenium.flx.custom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class editCustom{
    public String customNo;

    @Test
    public void queryCustom(WebDriver driver) {
        driver.switchTo().frame("mainframe");
        driver.findElement(By.id("customNo$text")).click();
        driver.findElement(By.id("customNo$text")).sendKeys(customNo);
        driver.findElement(By.className("search-condition"));
        driver.findElement(By.id("queryForm"));
        driver.findElement(By.className("mini-button-text")).click();
        driver.switchTo().defaultContent();
    }

    @Test
    public void saveCustom(WebDriver driver) {
        try {
            driver.switchTo().frame("mainframe");
            //点击添加按钮，弹出添加界面
            //driver.get("http://192.168.2.100:28080/FlxServer/custom/cusprofile/editCusProfileNew.jsp?type=add&_winid=w3689&_t=473420");
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-add")).click();
            //返回主窗体
            driver.switchTo().defaultContent();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-17"));
            driver.findElement(By.className("mini-panel-body"));
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-17\"]/div/div[2]/div[2]/iframe")));
            //driver.switchTo().frame(0);
            System.out.println(driver.getTitle());
            customNo = driver.findElement(By.name("entity.customNo")).getAttribute("value");
            driver.findElement(By.id("entity.company$text")).click();
            driver.findElement(By.id("entity.company$text")).sendKeys("ceshi0322");
            //点击选择业务员
            driver.findElement(By.xpath("//*[@id=\"entity.salesmanid\"]/span/span/span[2]/span")).click();
            //返回主窗体切换业务员iframe
            driver.switchTo().defaultContent();
            driver.findElement(By.id("mini-18"));
            driver.findElement(By.className("mini-panel-body"));
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-18\"]/div/div[2]/div[2]/iframe")));
            //获取第一个checkbox---业务员姓名
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-ok")).click();
            //返回主窗体切换业务员iframe---进入添加页面
            driver.switchTo().defaultContent();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-17"));
            driver.findElement(By.className("mini-panel-body"));
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-17\"]/div/div[2]/div[2]/iframe")));
            //授权人姓名，身份证，电话
            driver.findElement(By.id("entity.contactPerson$text")).click();
            driver.findElement(By.id("entity.contactPerson$text")).sendKeys("dzk");
            driver.findElement(By.id("entity.contactPersonIdcard$text")).click();
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("420984199701051755");
            driver.findElement(By.id("entity.cellPhone$text")).click();
            driver.findElement(By.id("entity.cellPhone$text")).sendKeys("12345678912");
            //主站需要进行实名认证
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            //认证失败---点击alert继续
            driver.findElement(By.id("mini-114")).click();
            //保存
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-119")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-117")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void errorSaveCustom(WebDriver driver) {
        try {
            driver.switchTo().frame("mainframe");
            //点击添加按钮，弹出添加界面
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-add")).click();
            //返回主窗体
            driver.switchTo().defaultContent();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-17"));
            driver.findElement(By.className("mini-panel-body"));
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-17\"]/div/div[2]/div[2]/iframe")));
            System.out.println(driver.getTitle());
            customNo = driver.findElement(By.name("entity.customNo")).getAttribute("value");
            driver.findElement(By.id("entity.company$text")).click();
            driver.findElement(By.id("entity.company$text")).sendKeys("ceshi0322");
            //点击选择业务员
            driver.findElement(By.xpath("//*[@id=\"entity.salesmanid\"]/span/span/span[2]/span")).click();
            //返回主窗体切换业务员iframe
            driver.switchTo().defaultContent();
            driver.findElement(By.id("mini-18"));
            driver.findElement(By.className("mini-panel-body"));
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-18\"]/div/div[2]/div[2]/iframe")));
            //获取第一个checkbox---业务员姓名
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-ok")).click();
            //返回主窗体切换业务员iframe---进入添加页面
            driver.switchTo().defaultContent();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-17"));
            driver.findElement(By.className("mini-panel-body"));
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-17\"]/div/div[2]/div[2]/iframe")));
            //授权人姓名，身份证，电话(有错的)
            driver.findElement(By.id("entity.contactPerson$text")).sendKeys("asfewdsa");
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("saefea");
            driver.findElement(By.id("entity.cellPhone$text")).sendKeys("2345s423");
            //主站需要进行实名认证,点击后提示输入的身份证号码有误---点击alert继续
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-114")).click();
            //输入正确的身份证号
            driver.findElement(By.id("entity.contactPersonIdcard$text")).clear();
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("420984199701051755");
            //继续实名认证,点击后提示输入的手机号码有误---点击alert继续
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-116")).click();
            //输入正确的电话号码
            driver.findElement(By.id("entity.cellPhone$text")).clear();
            driver.findElement(By.id("entity.cellPhone$text")).sendKeys("12345678902");
            //继续实名认证
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            //认证失败---点击alert继续
            driver.findElement(By.id("mini-118")).click();
            //保存
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-123")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-121")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void auditCustom(WebDriver driver) {
        try {
            driver.switchTo().frame("mainframe");
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            //点击修改按钮，弹出修改界面
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-edit")).click();
            //返回主窗体，进入修改页面
            Thread.sleep(500);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/custom/cusprofile/editCusProfileNew.jsp')]")));
            //企业审核
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-goto")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-119")).click();
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateCustom(WebDriver driver) {
        try {
            driver.switchTo().frame("mainframe");
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            Thread.sleep(500);
            //点击修改按钮，弹出修改界面
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-edit")).click();
            //返回主窗体，进入修改页面
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/custom/cusprofile/editCusProfileNew.jsp')]")));
            //driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-2\"]/div/div[2]/div[2]/iframe")));
            //修改 企业档案 授权人信息
            driver.findElement(By.id("entity.contactPerson$text")).click();
            driver.findElement(By.id("entity.contactPerson$text")).sendKeys("dzk123");
            //主站需要进行实名认证
            driver.findElement(By.id("recertbtn")).click();
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            //认证失败---点击alert继续
            driver.findElement(By.id("mini-117")).click();

            //修改 协议信息
            driver.findElement(By.xpath("//*[@id=\"mini-2$3\"]/span")).click();
            driver.findElement(By.id("mini-62$ck$0")).click();
            driver.findElement(By.id("mini-62$ck$20")).click();
            //保存
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-119")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-125")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-123")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void errorUpdateCustom(WebDriver driver) {
        try {
            driver.switchTo().frame("mainframe");
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            Thread.sleep(500);
            //点击修改按钮，弹出修改界面
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-edit")).click();
            //返回主窗体，进入修改页面
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/custom/cusprofile/editCusProfileNew.jsp')]")));
            //授权人姓名，身份证，电话（有错误的）
            driver.findElement(By.id("entity.contactPerson$text")).clear();
            driver.findElement(By.id("entity.contactPerson$text")).sendKeys("asfewdsa");
            driver.findElement(By.id("entity.contactPersonIdcard$text")).clear();
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("saefea");
            driver.findElement(By.id("entity.cellPhone$text")).clear();
            driver.findElement(By.id("entity.cellPhone$text")).sendKeys("2345s423");
            //主站需要进行实名认证,点击后提示输入的身份证号码有误---点击alert继续
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-114")).click();
            //输入正确的身份证号
            driver.findElement(By.id("entity.contactPersonIdcard$text")).clear();
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("420984199701051755");
            //继续实名认证,点击后提示输入的手机号码有误---点击alert继续
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-116")).click();
            //输入正确的电话号码
            driver.findElement(By.id("entity.cellPhone$text")).clear();
            driver.findElement(By.id("entity.cellPhone$text")).sendKeys("12345678902");
            //继续实名认证
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            //认证失败---点击alert继续
            driver.findElement(By.id("mini-118")).click();
            //修改 协议信息
            driver.findElement(By.xpath("//*[@id=\"mini-2$3\"]/span")).click();
            driver.findElement(By.id("mini-62$ck$0")).click();
            driver.findElement(By.id("mini-62$ck$20")).click();
            //保存
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-119")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-125")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-123")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
