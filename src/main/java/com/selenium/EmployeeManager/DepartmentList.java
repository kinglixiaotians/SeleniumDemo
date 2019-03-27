package com.selenium.EmployeeManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class DepartmentList {

    public static void add(WebDriver driver){
        try {
            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/ul/li[2]/a")).click();
            Thread.sleep(1000);
            String [] dep = new String[] { "运营","研发","随便","测试" };
            for(int i = 0;i < dep.length;i++){
                driver.findElement(By.id("bumen")).click();
                driver.findElement(By.id("orgName")).sendKeys(dep[i]);
                driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[3]/label/input")).click();
                Thread.sleep(500);
                if(driver.findElement(By.className("zeromodal-title1")).getText().equals("已存在此部门不能重复添加")){
                    driver.findElement(By.xpath("/html/body/div[9]/div[2]/div")).click();
                    driver.findElement(By.xpath("/html/body/div[7]/div[2]/div")).click();
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            driver.quit();
        }

    }
}
