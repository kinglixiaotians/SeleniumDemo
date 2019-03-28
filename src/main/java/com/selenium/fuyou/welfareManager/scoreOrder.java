package com.selenium.fuyou.welfareManager;

import com.selenium.utils.JdbcUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class scoreOrder {

    public String replyOrder(WebDriver driver, String custom) {
        try {
            //回复订单
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[4]/table/tbody/tr[2]/td[9]/a")).click();
            //获取验证码
            Thread.sleep(2000);
            driver.findElement(By.id("btnyzm")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("zeromodal-close")).click();
            JdbcUtil j = new JdbcUtil();
            String cord = j.querySmsCode(j.queryCellPhone(custom));
            Thread.sleep(2000);
            driver.findElement(By.id("mobileCode")).sendKeys(cord);
            driver.findElement(By.name("btnReply")).click();
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
