package com.selenium.fuyou.employeeManager;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.selenium.fuyou.fuYouMethod.isExistBoxOrExistButton;

public class departmentList {

    //region 新增部门

    @Test
    public void addDep(WebDriver driver){
        try {
            Thread.sleep(500);
            String [] dep = new String[] { "运营","研发","测试","后勤" };
            driver.findElement(By.id("bumen")).click();
            //循环添加部门
            for(int i = 0;i < dep.length;i++){
                Thread.sleep(500);
                driver.findElement(By.id("orgName")).sendKeys(dep[i]);
                Thread.sleep(500);
                driver.findElement(By.name("btnadd")).click();
                Thread.sleep(500);
                boolean flag = isExistBoxOrExistButton(driver,"zeromodal-overlay",1);
                Thread.sleep(500);
                if(flag){
                    driver.findElement(By.xpath("/html/body/div[9]/div[2]/div")).click();
                    Thread.sleep(500);
                    driver.findElement(By.id("orgName")).clear();
                    continue;
                }
                if(i != dep.length - 1){
                    Thread.sleep(500);
                    driver.findElement(By.id("bumen")).click();
                    Thread.sleep(500);
                }
            }
            boolean flag = isExistBoxOrExistButton(driver,"zeromodal-container",1);
            if(flag){
                driver.findElement(By.className("zeromodal-close")).click();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

    //region 删除部门

//    @Test
    public void deleteDep(WebDriver driver){
        try{
            boolean flag = isExistBoxOrExistButton(driver,"/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[2]",3);
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

    //endregion

    //region 编辑部门

//    @Test
    public void updateDep(WebDriver driver){
        try{
            boolean flag = isExistBoxOrExistButton(driver,"/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[1]",3);
            if(flag) {
                Thread.sleep(500);
                driver.findElement(By.xpath("/html/body/div[4]/div[4]/table/tbody/tr[2]/td[5]/a[1]")).click();
                Thread.sleep(500);
                driver.findElement(By.className("dgygtj_in")).clear();
                Thread.sleep(500);
                driver.findElement(By.className("dgygtj_in")).sendKeys("测试");
                Thread.sleep(500);
                driver.findElement(By.name("btnupdate")).click();
            }else {
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

}
