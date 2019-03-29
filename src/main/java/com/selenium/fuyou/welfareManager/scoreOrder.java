package com.selenium.fuyou.welfareManager;

import com.selenium.utils.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

@Slf4j
public class scoreOrder {

    /**
     * 回复企业订单
     *
     * @param driver
     * @param custom
     * @return
     */
    @Test
    public boolean replyOrder(WebDriver driver, String custom) {
        try {
            //回复订单
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[4]/table/tbody/tr[2]/td[9]/a")).click();
            //获取验证码
            driver.findElement(By.id("btnyzm")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("zeromodal-close")).click();
            //通过custom获取验证码
            Thread.sleep(2000);
            JdbcUtil j = new JdbcUtil();
            String cord = j.querySmsCode(j.queryCellPhone(custom));
            log.info("回复企业订单验证码为{}", cord);
            driver.findElement(By.id("mobileCode")).sendKeys(cord);
            Thread.sleep(1000);
            driver.findElement(By.name("btnReply")).click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
