package com.selenium.flx;

import com.selenium.base.DriverBase;
import com.selenium.flx.customService.customDetail;
import com.selenium.flx.order.editOrder;
import com.selenium.flx.custom.editCustom;
import com.selenium.flx.custom.sepecEditCustom;
import com.selenium.fuyou.fuYou;
import com.selenium.fuyou.welfareManager.scoreOrder;
import com.selenium.utils.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

@Slf4j
public class flx extends DriverBase {
    public WebDriver driver = driverName();
    public String windowsHandle;

    //测试地址
    public String flxUrl = PropertiesConfig.getInstance().getProperty("driver.flx.url");
    //登录用户名
    private String flxUserId = PropertiesConfig.getInstance().getProperty("driver.flx.userId");
    //登录密码
    private String flxPassword = PropertiesConfig.getInstance().getProperty("driver.flx.passWord");
    //开关用于判断是否开启特殊企业开户
    private String flxOpenSwitch = PropertiesConfig.getInstance().getProperty("driver.flx.openSwitch");

    @Test
    public void testrun() {
        sepecEditCustom se = new sepecEditCustom();
        openCustom(se);
    }

    /**
     * 登录
     *
     * @return
     */
    @Test
    public boolean login() {
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
            driver.findElement(By.id("userId")).clear();
            driver.findElement(By.id("userId")).sendKeys(flxUserId);
            driver.findElement(By.className("log")).click();
            Thread.sleep(500);
            //endregion
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 开户
     *
     * @return
     */
    @Test
    public boolean openCustom(sepecEditCustom se) {
        //chrom插件路径
        driver.get(flxUrl);
        driver.manage().window().maximize();
        try {
            //region 正常开户
            //登录
            login();
            //客户管理
            driver.findElement(By.id("1061")).click();
            //客户开户档案
            driver.findElement(By.id("1062")).click();
            Thread.sleep(2000);
            //正常流程开户
            se.normalCustom(driver);
            //开户后的流程，完成后注销账户
            judgement(procedure(se));
            //endregion

            //region 特殊开户
            if ("true".equals(flxOpenSwitch)) {
                login();
                //客户管理
                driver.findElement(By.id("1061")).click();
                //客户开户档案
                driver.findElement(By.id("1062")).click();
                Thread.sleep(2000);
                judgement(se.custom01(driver));
                //开户后的流程，完成后注销账户
                judgement(procedure(se));

                login();
                //客户管理
                driver.findElement(By.id("1061")).click();
                //客户开户档案
                driver.findElement(By.id("1062")).click();
                Thread.sleep(2000);
                judgement(se.custom02(driver));
                //开户后的流程，完成后注销账户
                judgement(procedure(se));
            }
            //endregion
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 开户之后的流程，完成后注销
     *
     * @param se
     * @return
     */
    @Test
    public boolean procedure(sepecEditCustom se) {
        try {
            editCustom custom = new editCustom();
            editOrder order = new editOrder();

            //region 客户管理
            //查询客户
            Thread.sleep(1000);
            custom.queryCustom(driver, se.customNo);
            //修改客户信息
            Thread.sleep(1000);
            custom.UpdateCustom(driver);
            //企业审核
            Thread.sleep(1000);
            judgement(custom.auditCustom(driver));
            //endregion

            //region 销售管理
            driver.findElement(By.id("1081")).click();
            //订单录入
            driver.findElement(By.id("1103")).click();
            Thread.sleep(1000);
            judgement(order.entryOrder(se.customNo, driver));
            //endregion

            //region 登录企业号激活企业并回复订单
            fuYou fy = new fuYou();
            //首次登录激活企业
            judgement(fy.login(se.customNo, "123456"));
            //激活成功后重新登录
            judgement(fy.login(se.customNo, "123456"));
            //企业回复订单
            judgement(fy.scoreOrderManager(se.customNo));
            fy.driver.close();
            //endregion

            //region 财务管理
            driver.findElement(By.id("1261")).click();
            //订单业务 订单经办
            driver.findElement(By.id("1587")).click();
            driver.findElement(By.id("1594")).click();
            Thread.sleep(1000);
            judgement(order.handleOrder(driver));
            //订单激活
            driver.findElement(By.id("1595")).click();
            Thread.sleep(1000);
            judgement(order.activateOrder(driver));
            //endregion

            //region 客服明细
            //客服明细查询 查询显示余额三秒后注销用户
            Thread.sleep(2000);
            driver.findElement(By.id("1662")).click();
            driver.findElement(By.id("1663")).click();
            Thread.sleep(1000);
            customDetail cd = new customDetail();
            judgement(cd.queryDetail(driver, se.customNo));
            //endregion

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
     * @param b
     */
    public void judgement(boolean b) {
        log.info("传入的值类型为{}", b);
        if (!b) {
            log.info("已关闭当前浏览器");
            driver.close();
            System.exit(0);
        }
    }
}
