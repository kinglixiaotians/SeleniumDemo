package com.selenium.flx.systemManagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import java.util.List;

import static com.selenium.flx.flx.journal;
import static com.selenium.flx.flxPublicMethod.taskScreenShot;

public class parameterManage {

    public boolean parameterManage(WebDriver driver) {
        try {

            Thread.sleep(500);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/parameter/code/bank_sys_code.jsp')]")));

            //选择类型名称并查询
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"codeType\"]/span/span/span[2]/span")).click();
            Thread.sleep(500);
            List<WebElement> list = driver.findElements(By.className("mini-listbox-item"));
            list.get(1).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-search")).click();
            Thread.sleep(500);
            list.get(6).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-search")).click();






            if (journal) {
                Reporter.log("系统管理--参数管理--测试完成 <br/>");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (journal) {
                taskScreenShot(driver);
                Reporter.log("系统管理--参数管理--测试失败。错误：" + e.toString() + "<br/>");
            }
            return false;
        }
    }
}
