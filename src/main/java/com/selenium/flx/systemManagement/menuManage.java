package com.selenium.flx.systemManagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

import javax.xml.bind.Element;

import java.util.List;

import static com.selenium.flx.flx.journal;
import static com.selenium.flx.flxPublicMethod.taskScreenShot;
import static com.selenium.flx.flxPublicMethod.updateInput;

public class menuManage {

    public boolean menu(WebDriver driver) {
        try {
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_manage.jsp')]")));
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_list.jsp')]")));
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-add")).click();
            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_add.jsp')]")));

            //直接保存 三个非空警告
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();

            Thread.sleep(1000);
            updateInput(driver, "name", "appmenu.menuname", "测试");
            updateInput(driver, "name", "appmenu.menucode", "test");
            updateInput(driver, "name", "appmenu.menulabel", "测试");
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();

            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_manage.jsp')]")));
            List<WebElement> list = driver.findElements(By.className("mini-tree-nodeshow"));
            list.get(1).click();

            Thread.sleep(1000);
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_info.jsp')]")));
            updateInput(driver, "name", "appmenu.menuname", "测试1");
            updateInput(driver, "name", "appmenu.menucode", "test1");
            updateInput(driver, "name", "appmenu.menulabel", "测试1");
            updateInput(driver, "name", "appmenu.displayorder", "3");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"form1\"]/table/tbody/tr[4]/td/a/span")).click();

            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_manage.jsp')]")));
            list = driver.findElements(By.className("mini-tree-nodeshow"));
            list.get(4).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"mini-16$3\"]/span")).click();

            Thread.sleep(1000);
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_list.jsp')]")));
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-add")).click();

            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_add.jsp')]")));
            updateInput(driver, "name", "appmenu.menuname", "测试菜单");
            updateInput(driver, "name", "appmenu.menucode", "menuTest");
            updateInput(driver, "name", "appmenu.menulabel", "测试菜单");
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();

            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_manage.jsp')]")));
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_list.jsp')]")));
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-edit")).click();

            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_edit.jsp')]")));
            updateInput(driver, "name", "appmenu.displayorder", "3");
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"appmenu.isleaf\"]/span/span/span[2]/span")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"mini-15$0\"]/td[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"funccode\"]/span[1]/span/span[2]/span")).click();

            Thread.sleep(500);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_function_select.jsp')]")));
            updateInput(driver,"name","criteria/_expr[2]/funcname","test");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"table1\"]/tbody/tr/td[5]/a/span")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[3]/a[1]/span")).click();
            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_edit.jsp')]")));
            driver.findElement(By.xpath("/html/body/div[2]/a[1]/span")).click();

            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_manage.jsp')]")));
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_list.jsp')]")));
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-remove")).click();
            driver.findElement(By.xpath("//*[@id=\"mini-25\"]/span")).click();
            Thread.sleep(1000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/coframe/framework/menu/menu_manage.jsp')]")));
            //创建鼠标
            Actions mouse = new Actions(driver);
            //右键测试1  点击删除
            list = driver.findElements(By.className("mini-tree-nodeshow"));
            mouse.contextClick(list.get(4)).perform();
            Thread.sleep(1000);
            driver.findElement(By.id("removemenu$text")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//a[contains(@style,'width: 58px; margin-right: 15px;')]")).click();
            Thread.sleep(1000);

            driver.switchTo().defaultContent();

            if (journal) {
                Reporter.log("系统管理--菜单管理--测试完成 <br/>");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (journal) {
                taskScreenShot(driver);
                Reporter.log("系统管理--菜单管理--测试失败。错误：" + e.toString() + "<br/>");
            }
            return false;
        }
    }

}
