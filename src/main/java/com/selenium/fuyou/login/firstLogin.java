package com.selenium.fuyou.login;

import com.selenium.utils.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

@Slf4j
public class firstLogin {

    /**
     * 首次登录进行企业验证
     *
     * @param driver
     * @param custom
     */
    //@Test
    public boolean verificationCustom(WebDriver driver, String custom) {
        try {
            Thread.sleep(2000);
            //短信确认
            driver.findElement(By.id("sendcodebt")).click();
            //获取手机验证码
            Thread.sleep(1000);
            driver.findElement(By.className("zeromodal-close")).click();
            Thread.sleep(2000);
            JdbcUtil j = new JdbcUtil();
            String cord = j.querySmsCode(j.queryCellPhone(custom));
            driver.findElement(By.id("Telcode")).sendKeys(cord);
            Thread.sleep(1000);
            driver.findElement(By.className("scdl_an")).click();
            //企业信息确认（设置新密码）
            Thread.sleep(1000);
            driver.findElement(By.id("newpassword")).sendKeys("123456");
            driver.findElement(By.id("confirmnewpw")).sendKeys("123456");
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/form/div[6]/label")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("button")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("layui-layer-btn0")).click();
            log.info("企业：{}首次登录验证成功", custom);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("企业：{}首次登录验证失败", custom);
            return false;
        }
    }
}
