package com.selenium.fuyou.emploeeManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class departmentList {
    //新增部门
    @Test
    public void addDep(WebDriver driver){
        try {
            Thread.sleep(500);
            String [] dep = new String[] { "运营","研发","随便","测试" };
            driver.findElement(By.id("bumen")).click();
            //循环添加部门
            for(int i = 0;i < dep.length;i++){
                Thread.sleep(500);
                driver.findElement(By.id("orgName")).sendKeys(dep[i]);
                driver.findElement(By.name("btnadd")).click();
                boolean flag = isExistErrorModel(driver);
                if(flag){
                    Thread.sleep(500);
                    driver.findElement(By.xpath("/html/body/div[9]/div[2]/div")).click();
                    Thread.sleep(500);
                    driver.findElement(By.id("orgName")).clear();
                    continue;
                }
                driver.findElement(By.id("bumen")).click();
                Thread.sleep(500);
            }
            boolean flag = isExistAddBox(driver);
            if(flag){
                driver.findElement(By.className("zeromodal-close")).click();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isExistAddBox(WebDriver driver){
        try {
            driver.findElement(By.className("zeromodal-container"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isExistErrorModel(WebDriver driver) {
        try {
            driver.findElement(By.className("zeromodal-overlay"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //删除部门
    @Test
    public void deleteDep(WebDriver driver){
        try{
            Thread.sleep(500);
            driver.findElement(By.xpath("/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-primary.anblock")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-default")).click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //编辑部门
    @Test
    public void updataDep(WebDriver driver){
        try{
            Thread.sleep(500);
            driver.findElement(By.xpath("/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[1]")).click();
            Thread.sleep(500);
            driver.findElement(By.className("dgygtj_in")).clear();
            Thread.sleep(500);
            driver.findElement(By.className("dgygtj_in")).sendKeys("研发");
            Thread.sleep(500);
            driver.findElement(By.name("btnupdate")).click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
