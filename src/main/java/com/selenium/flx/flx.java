package com.selenium.flx;

import com.selenium.base.DriverBase;
import com.selenium.flx.customService.customDetail;
import com.selenium.flx.order.editOrder;
import com.selenium.flx.custom.editCustom;
import com.selenium.flx.custom.sepecEditCustom;
import com.selenium.fuyou.fuYou;
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
    private String flxUserId = PropertiesConfig.getInstance().getProperty("driver.flx.userId");
    private String flxPassword = PropertiesConfig.getInstance().getProperty("driver.flx.passWord");

    @Test
    public void testrun() {
        runs();
    }

    @Test
    public boolean runs() {
        //chrom插件路径
        driver.get(flxUrl);
        driver.manage().window().maximize();
        try {
            editCustom custom = new editCustom();
            editOrder order = new editOrder();
            sepecEditCustom se = new sepecEditCustom();
            Thread.sleep(1000);
            //登录
            windowsHandle = driver.getWindowHandle();
            driver.findElement(By.id("userId")).sendKeys(flxUserId);
            driver.findElement(By.id("password")).sendKeys(flxPassword);
            driver.findElement(By.className("log")).click();
            Thread.sleep(500);


            //客户管理
            driver.findElement(By.id("1061")).click();
            //客户开户档案
            driver.findElement(By.id("1062")).click();

//            //正常开户
//            Thread.sleep(2000);
//            custom.saveCustom(driver);

            //特殊开户
            judgement(se.custom01(driver));

            //查询客户
            Thread.sleep(1000);
            custom.queryCustom(driver, se.customNo);
            //企业审核
            Thread.sleep(1000);
            judgement(custom.auditCustom(driver));
            //销售管理
            driver.findElement(By.id("1081")).click();
            //订单录入
            driver.findElement(By.id("1103")).click();
            Thread.sleep(1000);
            judgement(order.entryOrder(se.customNo, driver));

            //登录企业号回复订单
            fuYou fy = new fuYou();
            if(!fy.fuYouLogin(se.customNo, "123456")){
                driver.close();
            }

            //财务管理
            driver.findElement(By.id("1261")).click();
            //订单业务 订单经办
            driver.findElement(By.id("1587")).click();
            driver.findElement(By.id("1594")).click();
            Thread.sleep(1000);
            judgement(order.handleOrder(driver));
            //订单激活
            driver.findElement(By.id("1595")).click();
            judgement(order.activateOrder(driver));

            //客服明细
            //客服明细查询
            driver.findElement(By.id("1662")).click();
            driver.findElement(By.id("1663")).click();
            Thread.sleep(1000);
            customDetail cd=new customDetail();
            judgement(cd.queryDetail(driver,"01510320"));

//            //修改客户信息
//            Thread.sleep(1000);
//            custom.updateCustom(driver);
//            //企业审核
//            Thread.sleep(1000);
//            custom.auditCustom(driver);

//            //不正常开户
//            Thread.sleep(2000);
//            custom.errorSaveCustom(driver);
//            //查询客户
//            Thread.sleep(1000);
//            custom.queryCustom(driver);
//            //不正常修改
//            Thread.sleep(2000);
//            custom.errorUpdateCustom(driver);


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断返回值是否为true
     * 否，则关闭当前浏览器
     *
     * @param str
     */
    public void judgement(String str) {
        if (!"true".equals(str)) {
            driver.close();
        }
    }
}
