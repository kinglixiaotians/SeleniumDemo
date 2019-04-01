package com.selenium.fuyou.welfareManager;

import com.selenium.utils.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class welfareManager {

    //返回当前时间  格式为：yyyyMMddHHmmss（例：20190401160321）
    public static String nowDate() {
        //获取当前时间并进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 回复企业订单
     */
    @Test
    public boolean replyOrder(WebDriver driver, String custom) {
        try {
            //回复订单
            Thread.sleep(1000);
            driver.findElement(By.xpath("//tbody/tr[@id='spqingdan_title']/following-sibling::tr/td[9]/a")).click();
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

    /**
     * 福利发放
     */
    @Test
    public boolean provideWelfare(WebDriver driver, String custom) {
        try {

            //region 单个发放
            //福利名目
            driver.findElement(By.id("welfareName")).sendKeys("测试" + nowDate());
            //统一分配
            driver.findElement(By.id("Score")).sendKeys("100");
            //精确查找
            driver.findElement(By.id("txtkey")).sendKeys("四");
            driver.findElement(By.className("iconSearch")).click();
            Thread.sleep(500);
            driver.findElement(By.id("txtkey")).clear();
            driver.findElement(By.id("txtkey")).sendKeys("五");
            driver.findElement(By.className("iconSearch")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"fbgg_choice\"]/ul/li")).click();
            driver.findElement(By.id("txtkey")).clear();
            Thread.sleep(500);
            //手动选择发放对象
            driver.findElement(By.xpath("//*[@id=\"fbgg_choice\"]/ul/li[3]")).click();
            driver.findElement(By.xpath("//*[@id=\"fbgg_choice\"]/ul/li[5]")).click();
            driver.findElement(By.xpath("//*[@id=\"fbgg_choice\"]/ul/li[7]")).click();
            //删除已选成员
            listDelete(driver);
            Thread.sleep(1000);
            //全选
            driver.findElement(By.xpath("//*[@id=\"fbgg_choice\"]/ul/li[1]")).click();
            //更改一位优分为200
            driver.findElement(By.xpath("//*[@id=\"fbgg_con_bg\"]/div[5]/input")).clear();
            driver.findElement(By.xpath("//*[@id=\"fbgg_con_bg\"]/div[5]/input")).sendKeys("200");
            //获取验证码
            driver.findElement(By.id("btnyzm")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("zeromodal-close")).click();
            Thread.sleep(1000);
            JdbcUtil j = new JdbcUtil();
            String cord = j.querySmsCode(j.queryCellPhone(custom));
            driver.findElement(By.id("mobileCode")).sendKeys(cord);
            driver.findElement(By.id("btnSubmit")).click();
            log.info("福利发放成功");
            //endregion

            //region    批量发放



            //endregion

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("福利发放失败");
            return false;
        }
    }

    //删除已选成员
    public void listDelete(WebDriver driver) {
        List ls = driver.findElements(By.xpath("//*[@id=\"fbgg_con_bg\"]/div"));
        //遍历每个权限
        for (int i = 0; i < ls.size(); i++) {
            WebElement element = (WebElement) ls.get(i);
            element.findElement(By.cssSelector("div>i")).click();
        }
    }

    /**
     * 一卡通兑换
     */
    @Test
    public boolean companyCardPassExchange(WebDriver driver, String custom) {
        try {
            //兑换金额   金额格式：大于0，可保留两位小数；例如：500 或 450.25
            driver.findElement(By.id("exchangeAmount")).sendKeys("100");
            //获取验证码
            driver.findElement(By.id("btnyzm")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("zeromodal-close")).click();
            Thread.sleep(1000);
            JdbcUtil j = new JdbcUtil();
            String cord = j.querySmsCode(j.queryCellPhone(custom));
            driver.findElement(By.id("mobileCode")).sendKeys(cord);
            //确认兑换
            driver.findElement(By.id("btnSubmit")).click();
            Thread.sleep(3000);
            driver.findElement(By.className("zeromodal-close")).click();
            Thread.sleep(1000);
            log.info("一卡通兑换成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("一卡通兑换失败");
            return false;
        }
    }

    //region 企业收款管理

    /**
     * 设置固定金额的收款
     *
     * @return
     */
    @Test
    public boolean editFixedCompanyGatheringQrcode() {
        try {



            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 修改企业收款管理(b为是否固定金额)
     * 固定金额修改为动态金额，动态金额改为固定金额
     *
     * @return
     */
    @Test
    public boolean updateCompanyGatheringQrcode(WebDriver driver, boolean b) {
        try {
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[3]/table/tbody/tr[1]/td[7]/div/button[2]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[2]/div/div/div/span")).click();
            Thread.sleep(500);
            if (!b) {
                driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[3]/div/div/div/div/input")).clear();
                driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[3]/div/div/div/div/input")).sendKeys("5");
            }
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[3]/div/button[2]/span")).click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 增加企业收款管理(b为是否固定金额)
     */
    @Test
    public boolean addCompanyGatheringQrcode(WebDriver driver, boolean b) {
        try {
            //新增
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/div/button[1]")).click();
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[1]/div/div/div/input")).sendKeys("测试收款" + nowDate());
            //是否固定金额
            if (b) {
                driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[3]/div/div/div/div/input")).clear();
                driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[3]/div/div/div/div/input")).sendKeys("10");
            } else {
                driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[2]/div/div/div/span")).click();
            }
            // 确定
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[3]/div/button[2]/span")).click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //endregion

}
