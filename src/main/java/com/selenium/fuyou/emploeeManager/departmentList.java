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
                Thread.sleep(500);
                if(flag){
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

    //判断是否有新增窗体
    public boolean isExistAddBox(WebDriver driver){
        try {
            driver.findElement(By.className("zeromodal-container"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //判断是否有错误窗体
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
            boolean flag = isExistDelButton(driver);
            if(flag){
            Thread.sleep(500);
            driver.findElement(By.xpath("/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-primary.anblock")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-default")).click();
            }else {
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //判断是否有删除按钮
    public boolean isExistDelButton(WebDriver driver){
        try{
            driver.findElement(By.xpath("/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[2]"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //编辑部门
    @Test
    public void updataDep(WebDriver driver){
        try{
            boolean flag = isExistEditButton(driver);
            if(flag) {
                Thread.sleep(500);
                driver.findElement(By.xpath("/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[1]")).click();
                Thread.sleep(500);
                driver.findElement(By.className("dgygtj_in")).clear();
                Thread.sleep(500);
                driver.findElement(By.className("dgygtj_in")).sendKeys("研发");
                Thread.sleep(500);
                driver.findElement(By.name("btnupdate")).click();
            }else {
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //判断是否有编辑按钮
    public boolean isExistEditButton(WebDriver driver){
        try{
            driver.findElement(By.xpath("/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[1]"));
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
