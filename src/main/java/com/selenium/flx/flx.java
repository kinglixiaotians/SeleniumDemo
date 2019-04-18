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


import static com.selenium.flx.flxPublicMethod.taskScreenShot;
import static com.selenium.flx.flxPublicMethod.updateInput;

@Slf4j
public class flx extends DriverBase {
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
    //是否打印日志
    public static boolean journal = true;

    @Test
    public void flx() {
        //chrom插件路径
        driver.get(flxUrl);
        driver.manage().window().maximize();
    }

    /**
     * 登录
     */
    @Test(dependsOnMethods = "flx", description = "登录")
    public void login() {
        try {
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
            if (journal) {
                Reporter.log("登入flx成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (journal) {
                taskScreenShot(driver);
                Reporter.log("登录flx。错误：" + e.toString());
            }
        }
    }

    /**
     * 正常开户
     */
    @Test(dependsOnMethods = "login", description = "正常开户")
    public void normalOpenCustom() {
        if (!se.normalCustom(driver)) {
            driver.findElement(By.id("asdf")).click();
        }
    }

    /**
     * 客户管理 企业审核
     */
    @Test(dependsOnMethods = "normalOpenCustom", description = "客户管理 企业审核")
    public void auditCustom() {
        if (!custom.queryCustom(driver, se.customNo) || !custom.auditCustom(driver))
            driver.findElement(By.id("asdf")).click();
    }

    /**
     * 销售管理 订单录入
     */
    @Test(dependsOnMethods = "auditCustom", description = "销售管理 订单录入")
    public void entryOrder() {
        if (!order.entryOrder(se.customNo, driver))
            driver.findElement(By.id("asdf")).click();
    }

    /**
     * 销售管理 订单复核
     */
    @Test(dependsOnMethods = "entryOrder", description = "销售管理 订单复核")
    public void checkOrder() {
        //订单复核
        if (!order.checkOrder(se.customNo, driver))
            driver.findElement(By.id("asdf")).click();
    }

    /**
     * 首次登录激活企业
     */
    @Test(dependsOnMethods = "checkOrder", description = "首次登录激活企业")
    public void firstLoginFuYou() {
        //首次登录激活企业
        if (!fy.login(se.customNo, "123456"))
            driver.findElement(By.id("asdf")).click();
    }

    /**
     * 激活成功后重新登录 并回复订单
     */
    @Test(dependsOnMethods = "firstLoginFuYou", description = "激活成功后重新登录 并回复订单")
    public void againLoginFuYou() {
        //激活成功后重新登录
        if (!fy.login(se.customNo, "123456") || !fy. replyCustomOrder(se.customNo))
            driver.findElement(By.id("asdf")).click();
        else
            fy.driver.close();
    }

    /**
     * 财务管理 订单业务 订单经办
     */
    @Test(dependsOnMethods = "againLoginFuYou", description = "财务管理 订单业务 订单经办")
    public void handleOrder() {
        if (!order.handleOrder(driver))
            driver.findElement(By.id("asdf")).click();
    }

    /**
     * 财务管理 订单激活
     */
    @Test(dependsOnMethods = "handleOrder", description = "财务管理 订单激活")
    public void activateOrder() {
        if (!order.activateOrder(driver))
            driver.findElement(By.id("asdf")).click();
    }

    /**
     * 客服明细查询 查询显示余额三秒后注销用户
     */
    @Test(dependsOnMethods = "activateOrder", description = "客服明细查询 查询显示余额三秒后注销用户")
    public void queryDetail() {
        if (!cd.queryDetail(driver, se.customNo))
            driver.findElement(By.id("asdf")).click();
    }

    /**
     * 特殊开户
     */
    @Test(dependsOnMethods = "queryDetail", description = "特殊开户")
    public void specialOpenCustom_1() {
        flx();
        //判断是否开启特殊开户
        if (Boolean.parseBoolean(flxOpenSwitch)) {
            Reporter.log("-----------以下为特殊开户。");
            journal = false;

            login();
            if (se.custom01(driver) && specialOpenCustomProcedure())
                Reporter.log("特殊开户1----开户并充值一千万优分成功。企业号：" + se.customNo);
            else
                Reporter.log("特殊开户1----开户失败");

            login();
            if (se.custom02(driver) && specialOpenCustomProcedure())
                Reporter.log("特殊开户2----开户并充值一千万优分成功。企业号：" + se.customNo);
            else
                Reporter.log("特殊开户1----开户失败");
        } else {
            Reporter.log("特殊开户未开启");
        }
    }

    //特殊开户流程
    public boolean specialOpenCustomProcedure() {
        //客户管理 企业审核
        if (!custom.queryCustom(driver, se.customNo))
            return false;
        if (!custom.auditCustom(driver))
            return false;
        //销售管理 订单录入
        if (!order.entryOrder(se.customNo, driver))
            return false;
        //销售管理 订单复核
        if (!order.checkOrder(se.customNo, driver))
            return false;
        //首次登录激活企业
        fuYou fy=new fuYou();
        if (!fy.login(se.customNo, "123456"))
            return false;
        //激活成功后重新登录 并回复订单
        if (!fy.login(se.customNo, "123456"))
            return false;
        if (!fy.replyCustomOrder(se.customNo))
            return false;
        else
            fy.driver.close();
        //财务管理 订单业务 订单经办
        if (!order.handleOrder(driver))
            return false;
        //财务管理 订单激活
        if (!order.activateOrder(driver))
            return false;
        //完成后注销
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[1]/p/span[2]/a[2]")).click();
        return true;
    }

}
