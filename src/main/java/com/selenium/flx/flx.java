package com.selenium.flx;

import com.selenium.base.DriverBase;
import com.selenium.flx.customService.customDetail;
import com.selenium.flx.order.editOrder;
import com.selenium.flx.custom.editCustom;
import com.selenium.flx.custom.sepecEditCustom;
import com.selenium.fuyou.fuYou;
import com.selenium.utils.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;


import static com.selenium.flx.flxPublicMethod.updateInput;

@Slf4j
public class flx extends DriverBase {
    public String customNo;
    public WebDriver driver = driverName();
    public String windowsHandle;
    public sepecEditCustom se = new sepecEditCustom();
    public editCustom custom = new editCustom();
    public editOrder order = new editOrder();
    public customDetail cd = new customDetail();
    public fuYou fy = new fuYou();
    //测试地址
    public String flxUrl = PropertiesConfig.getInstance().getProperty("driver.flx.url");
    //登录用户名
    private String flxUserId = PropertiesConfig.getInstance().getProperty("driver.flx.userId");
    //登录密码
    private String flxPassword = PropertiesConfig.getInstance().getProperty("driver.flx.passWord");
    //开关用于判断是否开启特殊企业开户
    private String flxOpenSwitch = PropertiesConfig.getInstance().getProperty("driver.flx.openSwitch");

    @Test
    public void flx() {
        //chrom插件路径
        driver.get(flxUrl);
        driver.manage().window().maximize();
    }

    /**
     * 登录
     */
    @Test(dependsOnMethods = "flx",description = "登录")
//    @Test
    public void login() {
        try {
            //region 登录
            Thread.sleep(1000);
            windowsHandle = driver.getWindowHandle();
            driver.findElement(By.id("userId")).sendKeys("789456");
            driver.findElement(By.id("password")).sendKeys(flxPassword);
            driver.findElement(By.className("log")).click();
            Thread.sleep(500);
            driver.findElement(By.className("mini-button-text")).click();
            driver.findElement(By.id("userId")).click();
            updateInput(driver, "id", "userId", flxUserId);
            driver.findElement(By.className("log")).click();
            Thread.sleep(500);
            Reporter.log("登入flx成功");
            //endregion
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 特殊开户1
     */
    public boolean specialOpenCustom_1() {
        try {
            log.info("-----------以下为特殊开户。");
            se.custom01(driver);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 特殊开户2
     */
    public boolean specialOpenCustom_2() {
        try {
            se.custom02(driver);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 正常开户
     */
    @Test(dependsOnMethods = "login",description = "正常开户")
//    @Test
    public void normalOpenCustom() {
        try {
            se.normalCustom(driver);
            Reporter.log("正常开户成功，企业号为：" + se.customNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户管理 企业审核
     */
    @Test(dependsOnMethods = "normalOpenCustom",description = "客户管理 企业审核")
//    @Test
    public void auditCustom() {
        try {
            //查询客户
            Thread.sleep(1000);
            custom.queryCustom(driver, se.customNo);
            //企业审核
            Thread.sleep(1000);
            custom.auditCustom(driver);
            Reporter.log("企业审核成功，企业号为：" + se.customNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销售管理 订单录入
     */
    @Test(dependsOnMethods = "auditCustom",description = "销售管理 订单录入")
//    @Test
    public void entryOrder() {
        try {
            driver.findElement(By.id("1081")).click();
            //订单录入
            driver.findElement(By.id("1103")).click();
            Thread.sleep(1000);
            order.entryOrder(se.customNo, driver);
            Reporter.log("销售管理 订单录入成功，企业号为：" + se.customNo + " 。订单号为：" + order.orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销售管理 订单复核
     */
    @Test(dependsOnMethods = "entryOrder",description = "销售管理 订单复核")
//    @Test
    public void checkOrder() {
        try {
            //订单复核
            order.checkOrder(se.customNo, driver);
            Reporter.log("销售管理 订单复核成功，企业号为：" + se.customNo + " 。订单号为：" + order.orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 首次登录激活企业
     */
    @Test(dependsOnMethods = "checkOrder",description = "首次登录激活企业")
//    @Test
    public void firstLoginFuYou(sepecEditCustom se) {
        try {
            //首次登录激活企业
            fy.login(se.customNo, "123456");
            Reporter.log("首次登录激活企业成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 激活成功后重新登录 并回复订单
     */
    @Test(dependsOnMethods = "firstLoginFuYou",description = "激活成功后重新登录 并回复订单")
//    @Test
    public void againLoginFuYou() {
        try {
            //激活成功后重新登录
            fy.login(se.customNo, "123456");
            //企业回复订单
            fy.replyCustomOrder(se.customNo);
            Thread.sleep(1000);
            //关闭fuyou页面
            fy.driver.close();
            Reporter.log("企业回复订单成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 财务管理 订单业务 订单经办
     */
    @Test(dependsOnMethods = "againLoginFuYou",description = "财务管理 订单业务 订单经办")
//    @Test
    public void handleOrder() {
        try {
            driver.findElement(By.id("1261")).click();
            //订单业务 订单经办
            driver.findElement(By.id("1587")).click();
            driver.findElement(By.id("1594")).click();
            Thread.sleep(1000);
            order.handleOrder(driver);
            Reporter.log("财务管理 订单业务 订单经办成功，订单号为：" + order.orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 财务管理 订单激活
     */
    @Test(dependsOnMethods = "handleOrder",description = "财务管理 订单激活")
//    @Test
    public void activateOrder() {
        try {
            //订单激活
            driver.findElement(By.id("1595")).click();
            Thread.sleep(1000);
            order.activateOrder(driver);
            Reporter.log("财务管理 订单激活成功，订单号为：" + order.orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 客服明细查询 查询显示余额三秒后注销用户
     */
    @Test(dependsOnMethods = "activateOrder",description = "客服明细查询 查询显示余额三秒后注销用户")
//    @Test
    public void queryDetail() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("1662")).click();
            driver.findElement(By.id("1663")).click();
            Thread.sleep(1000);
            cd.queryDetail(driver, se.customNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
