package com.selenium.fuyou.login;

import com.selenium.utils.JdbcUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class firstLogin {

    /**
     * 首次登录进行企业验证
     *
     * @param driver
     * @param custom
     */
    @Test
    public String verificationCustom(WebDriver driver, String custom) {
        try {
            //短信确认
            Thread.sleep(1000);
            driver.findElement(By.id("sendcodebt")).click();
            JdbcUtil j = new JdbcUtil();
            //获取手机验证码
            String cord = j.querySmsCode(j.queryCellPhone(custom));
            Thread.sleep(1000);
            driver.findElement(By.className("zeromodal-close")).click();
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
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
