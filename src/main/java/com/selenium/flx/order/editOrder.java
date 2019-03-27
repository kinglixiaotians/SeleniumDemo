package com.selenium.flx.order;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class editOrder {
    public String customNo;
    public String orderId="20190327016";

    //订单录入
    @Test
    public void entryOrder(String customNo, WebDriver driver) {
        try {
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/sales/order/orderQueryList.jsp')]")));
            //点击订单录入按钮
            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div[2]/div/table/tbody/tr/td/a[1]/span")).click();
            driver.switchTo().defaultContent();
            //*[@id="mini-17"]/div/div[2]/div[2]/iframe
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-17\"]/div/div[2]/div[2]/iframe")));
            //选择客户编号
            driver.findElement(By.xpath("//*[@id=\"customCode\"]/span/span/span[2]/span")).click();
            //输入客户编号查询
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/pub/customPub/getCustom.jsp')]")));
            driver.findElement(By.xpath("//*[@id=\"customNo$text\"]")).clear();
            driver.findElement(By.xpath("//*[@id=\"customNo$text\"]")).sendKeys(customNo);
            driver.findElement(By.xpath("//*[@id=\"queryForm\"]/table/tbody/tr[1]/td[7]/a/span")).click();
            Thread.sleep(500);
            //选择
            driver.findElement(By.id("42$cell$1")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-ok")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/sales/order/entry_order.jsp')]")));
            //进入购买优分，输入优分
            orderId = driver.findElement(By.name("orderInfo.orderNo")).getAttribute("value");
            driver.findElement(By.xpath("//*[@id=\"form1\"]/div/div/div[5]")).click();
            driver.findElement(By.id("integral$text")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "300");
            //订单录入时经办
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"savebtn0\"]/span")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-146")).click();
            //订单录入时复核
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/sales/order/orderQueryList.jsp')]")));
            driver.findElement(By.id("orderNo$text")).sendKeys(orderId);
            driver.findElement(By.xpath("//*[@id=\"queryForm\"]/table/tbody/tr[2]/td[9]/a")).click();
            Thread.sleep(500);
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            Thread.sleep(500);
            driver.findElement(By.id("btn_editDictType")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/sales/order/entry_order.jsp')]")));
            driver.findElement(By.id("savebtn1")).click();
            Thread.sleep(500);
            driver.findElement(By.id("savebtn2")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-151")).click();
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //订单经办
    @Test
    public void handleOrder(WebDriver driver) {
        try {
            //查询订单 处理订单
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/sales/order/orderQueryList_1.jsp')]")));
            driver.findElement(By.id("orderNo$text")).sendKeys(orderId);
            driver.findElement(By.xpath("//*[@id=\"queryForm\"]/table/tbody/tr[3]/td[5]/a/span")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-edit")).click();
            Thread.sleep(500);
            //订单经办
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/sales/order/entry_order_1.jsp')]")));
            driver.findElement(By.id("savebtn1")).click();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
