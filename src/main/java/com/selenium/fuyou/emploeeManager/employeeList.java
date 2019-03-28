package com.selenium.fuyou.emploeeManager;

import com.selenium.fuyou.baseDB.employee;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class employeeList {
    //新增员工
    @Test
    public static void addEmp(WebDriver driver) {
        try {
            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/ul/li[1]/a")).click();
            Thread.sleep(500);

            //分页
            boolean pageNextFlag = isExistPageNext(driver);
            boolean pagePrevFlag = isExistPagePrev(driver);
            if (pageNextFlag){
                driver.findElement(By.className("page-next")).click();
            }
            Thread.sleep(500);
            if(pagePrevFlag){
                driver.findElement(By.className("page-prev")).click();
            }
            Thread.sleep(500);
            if(pageNextFlag || pagePrevFlag){
                pageJump(driver);
            }

            ArrayList<employee> staff = new ArrayList<>();
            staff.add(new employee("不知道", 1, "112", "15829834764", "2017-01-04"));
            staff.add(new employee("你猜", 2, "103", "13384750276", ""));
            staff.add(new employee("你再猜", 2, "1223344", "13188001077", "2018-10-29"));

            //循环插入员工
            driver.findElement(By.id("bgcreate")).click();
            for (employee e : staff) {
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
                //新增员工
                driver.findElement(By.xpath("//*[@id=\"createcontent\"]/div/div[9]/label/input")).click();
                Thread.sleep(500);
                String s = driver.findElement(By.className("zeromodal-title1")).getText();
                Thread.sleep(500);
                if(s.equals("员工工号已存在") || s.equals("已经存在相同的账号")){
                    driver.findElement(By.className("zeromodal-close")).click();
                    driver.findElement(By.id("RealName")).clear();
                    downList.selectByIndex(0);
                    driver.findElement(By.id("ExternalUserId")).clear();
                    driver.findElement(By.id("UserName")).clear();
                    driver.findElement(By.id("CompanyEmployeesList_addJoinDate")).clear();
                    continue;
                }
                driver.findElement(By.className("zeromodal-close")).click();
                Thread.sleep(500);
                driver.findElement(By.id("bgcreate")).click();
                Thread.sleep(500);
            }
            boolean flag = isExistAddBox(driver);
            if(flag){
                driver.findElement(By.xpath("//*[@id=\"xubox_layer1\"]/div[1]/a")).click();
            }

            WebElement ele = driver.findElement(By.cssSelector(".qyzx_bm.bm_menu"));
            List<WebElement> eleList = ele.findElements(By.tagName("a"));
            eleList.get(2).click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断是否有下一页按钮
    public static boolean isExistPageNext(WebDriver driver){
        try {
            driver.findElement(By.className("page-next"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //判断是否有上一页按钮
    public static boolean isExistPagePrev(WebDriver driver){
        try {
            driver.findElement(By.className("page-prev"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //分页跳转
    public static void pageJump(WebDriver driver){
        try{
            driver.findElement(By.id("txtGoto")).clear();
            driver.findElement(By.id("txtGoto")).sendKeys("12");
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[7]/div/span/input[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.id("txtGoto")).clear();
            Thread.sleep(500);
            driver.findElement(By.id("txtGoto")).sendKeys("1");
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[7]/div/span/input[2]")).click();
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isExistAddBox(WebDriver driver){
        try {
            driver.findElement(By.cssSelector(".xubox_layer.xubox_layer_0"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //修改员工信息
    @Test
    public static void updataEmp(WebDriver driver){
        try {
            driver.findElement(By.xpath("//*[@id=\"qyzx_plist\"]/table/tbody/tr[2]/td[6]/a[1]")).click();
            Thread.sleep(500);

            //清空修改项
            driver.findElement(By.id("updateRealName")).clear();
            WebElement element = driver.findElement(By.id("CompanyEmployeesList_ddlUpdateOrganization"));
            Select downList = new Select(element);
            downList.selectByIndex(0);
            driver.findElement(By.id("updateExternalUserId")).clear();
            ((JavascriptExecutor) driver).executeScript("document.getElementById('CompanyEmployeesList_updateJoinDate').removeAttribute('readonly')");
            driver.findElement(By.id("CompanyEmployeesList_updateJoinDate")).clear();
            Thread.sleep(500);

            //修改的内容失败
            driver.findElement(By.id("updateRealName")).sendKeys("不知道");
            downList.selectByIndex(3);
            driver.findElement(By.id("updateExternalUserId")).sendKeys("115");
            driver.findElement(By.id("CompanyEmployeesList_updateJoinDate")).sendKeys("2019-01-28");
            driver.findElement(By.id("CompanyEmployeesList_updateJoinDate")).click();
            Thread.sleep(500);
            driver.findElement(By.id("btnupdateUser")).click();
            Thread.sleep(500);
            String s = driver.findElement(By.className("zeromodal-title1")).getText();
            driver.findElement(By.className("zeromodal-close")).click();
            if(s.equals("员工工号已存在")) {
                driver.findElement(By.cssSelector(".xubox_close.xulayer_png32.xubox_close0_0")).click();
            }

            //第二次修改
            driver.findElement(By.xpath("//*[@id=\"qyzx_plist\"]/table/tbody/tr[2]/td[6]/a[1]")).click();
            Thread.sleep(500);
            driver.findElement(By.id("updateRealName")).clear();
            downList.selectByIndex(0);
            driver.findElement(By.id("updateExternalUserId")).clear();
            driver.findElement(By.id("CompanyEmployeesList_updateJoinDate")).clear();
            Thread.sleep(500);
            //修改内容成功
            driver.findElement(By.id("updateRealName")).sendKeys("鬼知道");
            downList.selectByIndex(3);
            driver.findElement(By.id("updateExternalUserId")).sendKeys("77777777777777");
            driver.findElement(By.id("CompanyEmployeesList_updateJoinDate")).sendKeys("2019-02-28");
            driver.findElement(By.id("CompanyEmployeesList_updateJoinDate")).click();
            Thread.sleep(500);
            driver.findElement(By.id("btnupdateUser")).click();
            Thread.sleep(500);
            driver.findElement(By.className("zeromodal-close")).click();
            Thread.sleep(500);

            //部门分类跳转
            WebElement ele = driver.findElement(By.cssSelector(".qyzx_bm.bm_menu"));
            List<WebElement> eleList = ele.findElements(By.tagName("a"));
            eleList.get(3).click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除员工
    @Test
    public static void deleteEmp(WebDriver driver){
        try{
            driver.findElement(By.xpath("//*[@id=\"qyzx_plist\"]/table/tbody/tr[2]/td[6]/a[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-primary.anblock")).click();
            Thread.sleep(500);

            //部门分类全部
            WebElement ele = driver.findElement(By.cssSelector(".qyzx_bm.bm_menu"));
            List<WebElement> eleList = ele.findElements(By.tagName("a"));
            eleList.get(0).click();
            Thread.sleep(1000);

            //删除取消
            driver.findElement(By.xpath("//*[@id=\"qyzx_plist\"]/table/tbody/tr[2]/td[6]/a[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-default")).click();
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //搜索员工
    @Test
    public static void searchEmp(WebDriver driver){
        try{
            driver.findElement(By.className("qyzx_key_input")).sendKeys("酷冷");
            driver.findElement(By.className("yqzx_key_search")).click();
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
