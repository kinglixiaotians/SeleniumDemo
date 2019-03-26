package com.selenium.test.flx.order;

import com.selenium.test.flx.order.editOrder;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class editOrder {
    static WebDriver driver;
    public String customNo;


    public void getCustomNo(String customNo) {
        customNo = customNo;
    }

    public static void entryOrder(String customNo) {
        try {
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/sales/order/orderQueryList.jsp')]")));
            //点击订单录入按钮
            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div[2]/div/table/tbody/tr/td/a[1]/span")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/sales/order/entry_order.jsp?_winid=w5270&_t=793865')]")));
            //选择客户编号
            driver.findElement(By.xpath("//*[@id=\"customCode\"]/span/span/span[2]/span")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
