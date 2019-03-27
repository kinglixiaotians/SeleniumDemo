package com.selenium.EmployeeManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;


public class EmployeeList {
    public static void add(WebDriver driver) {
        try {
            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/ul/li[1]/a")).click();

            ArrayList<Employee> staff = new ArrayList<>();
            staff.add(new Employee("不知道", 1, "002", "17507153713", "2017-01-04"));
            staff.add(new Employee("你猜", 0, "", "15307153715", ""));
            staff.add(new Employee("你再猜", 2, "005", "17607153713", "2018-10-29"));

            driver.findElement(By.id("bgcreate")).click();
            for (Employee e : staff) {
                Thread.sleep(500);

                driver.findElement(By.id("RealName")).sendKeys(e.getRealName());
                Thread.sleep(500);

                WebElement ele = driver.findElement(By.id("CompanyEmployeesList_ddlOrganization"));
                Select downList = new Select(ele);
                downList.selectByIndex(e.getDepartmentID());
                Thread.sleep(500);

                driver.findElement(By.id("ExternalUserId")).sendKeys(e.getUserID());
                Thread.sleep(500);

                driver.findElement(By.id("UserName")).sendKeys(e.getPhoneNum());
                Thread.sleep(500);

                //操作日期下拉框
                ((JavascriptExecutor) driver).executeScript("document.getElementById('CompanyEmployeesList_addJoinDate').removeAttribute('readonly')");
                driver.findElement(By.id("CompanyEmployeesList_addJoinDate")).sendKeys(e.getDate());
                driver.findElement(By.id("CompanyEmployeesList_addJoinDate")).click();

                driver.findElement(By.xpath("//*[@id=\"createcontent\"]/div/div[9]/label/input")).click();
                Thread.sleep(500);

                if(driver.findElement(By.className("zeromodal-title1")).getText().equals("已经存在相同的账号")){
                    driver.findElement(By.className("zeromodal-close")).click();
                    driver.findElement(By.id("RealName")).clear();
                    downList.selectByIndex(0);
                    driver.findElement(By.id("ExternalUserId")).clear();
                    driver.findElement(By.id("UserName")).clear();
                    driver.findElement(By.id("CompanyEmployeesList_addJoinDate")).clear();
                    continue;
                }
                driver.findElement(By.className("zeromodal-close")).click();
                driver.findElement(By.id("bgcreate")).click();
                boolean flag = isExist(driver);
                if(flag){
                    driver.findElement(By.xpath("//*[@id=\"xubox_layer1\"]/div[1]/a")).click();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
        }
    }

    public static boolean isExist(WebDriver driver) {
        try {
            driver.findElement(By.id("xubox_layer1"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


