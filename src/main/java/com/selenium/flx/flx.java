package com.selenium.flx;

import com.selenium.base.DriverBase;
import com.selenium.flx.order.editOrder;
import com.selenium.flx.custom.editCustom;
import com.selenium.utils.PropertiesConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class flx extends DriverBase {
    public WebDriver driver = driverName();
    public String windowsHandle;
    public String customNo;

    //key
    public String flxUrl = PropertiesConfig.getInstance().getProperty("driver.flx.url");

    @Test
    public void testrun() {
        flx f = new flx();
        f.runs();
    }

    @Test
    public boolean runs() {
        //chrom插件路径
        driver.get(flxUrl);
        driver.manage().window().maximize();
        try {
            editCustom custom = new editCustom();
            editOrder order = new editOrder();
            Thread.sleep(1000);
            //登录
            windowsHandle = driver.getWindowHandle();
            driver.findElement(By.id("userId")).sendKeys("100002");
            driver.findElement(By.id("password")).sendKeys("000000");
            driver.findElement(By.className("log")).click();
            Thread.sleep(500);

            //客户管理
            driver.findElement(By.id("1061")).click();
            //客户开户档案
            driver.findElement(By.id("1062")).click();
//            //正常开户
//            Thread.sleep(2000);
//            custom.saveCustom(driver);
//            //查询客户
//            Thread.sleep(1000);
//            custom.queryCustom(driver);
//            //修改客户信息
//            Thread.sleep(1000);
//            custom.updateCustom(driver);
//            //企业审核
//            Thread.sleep(1000);
//            custom.auditCustom(driver);

            //不正常开户
            Thread.sleep(2000);
            custom.errorSaveCustom(driver);
            //查询客户
            Thread.sleep(1000);
            custom.queryCustom(driver);
            //不正常修改
            Thread.sleep(2000);
            custom.errorUpdateCustom(driver);


//            //销售管理
//            driver.findElement(By.id("1081")).click();
//            //订单录入
//            driver.findElement(By.id("1103")).click();
//            Thread.sleep(1000);
//            order.entryOrder("01510245",driver);

//            //登录企业号回复订单


//            //财务管理
//            driver.findElement(By.id("1261")).click();
//            //订单业务 订单经办
//            driver.findElement(By.id("1587")).click();
//            driver.findElement(By.id("1594")).click();
//            Thread.sleep(1000);
//            order.handleOrder(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
