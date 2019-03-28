package com.selenium.fuyou;

import com.selenium.base.DriverBase;
import com.selenium.fuyou.emploeeManager.departmentList;
import com.selenium.fuyou.emploeeManager.employeeList;
import com.selenium.fuyou.login.firstLogin;
import com.selenium.fuyou.login.loginValidate;
import com.selenium.fuyou.welfareManager.scoreOrder;
import com.selenium.utils.PropertiesConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class fuYou extends DriverBase {
    DesiredCapabilities caps = setDownloadsPath("E:\\2019");
    public WebDriver driver = driverName(caps);
    //key
    public String fuYouUrl = PropertiesConfig.getInstance().getProperty("driver.fuYou.url");
    public String username = PropertiesConfig.getInstance().getProperty("fuYou.username");
    public String password = PropertiesConfig.getInstance().getProperty("fuYou.password");

    @Test
    public void fuYouTest() {
        fuYouLogin(username,password);
    }

    public boolean fuYouLogin(String username,String password) {
        driver.get(fuYouUrl);
        driver.manage().window().maximize();
        try {
        loginValidate log = new loginValidate();
        boolean flag = log.isExistNotice(driver);
        if (flag){
            driver.findElement(By.className("notice_close")).click();
            Thread.sleep(500);
        }
        flag = log.isExistTips(driver);
        if(flag){
            driver.findElement(By.className("layui-layer-setwin")).click();
            Thread.sleep(500);
        }

        //
        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/label[1]/a")).click();
        Thread.sleep(500);
        driver.findElement(By.id("username")).sendKeys(username);
        Thread.sleep(500);
        driver.findElement(By.id("password")).sendKeys(password);
        String result = log.validateCoding(driver);
        driver.findElement(By.id("verifyCode")).sendKeys(result);
        Thread.sleep(500);
        driver.findElement(By.className("login_Btn")).click();
        Thread.sleep(1000);
        //循环输入验证码登陆
        while (log.isAlertPersent(driver)) {
            driver.switchTo().alert().accept();
            driver.findElement(By.id("verifyCode")).clear();
            Thread.sleep(500);
            result = log.validateCoding(driver);
            driver.findElement(By.id("verifyCode")).sendKeys(result);
            Thread.sleep(500);
            driver.findElement(By.className("login_Btn")).click();
            Thread.sleep(500);
        }

            Thread.sleep(500);
            flag = log.isExistFistUse(driver);
            if(flag){
                driver.findElement(By.xpath("//*[@id=\"dowebok\"]/div[1]/div/a[2]")).click();
            }

            //判断是否为企业账号首次登录
            //首次登录企业账号需要进行账号验证并激活！
            Thread.sleep(1000);
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals(fuYouUrl + "Company/CompanyFirstLoad")) {
                Thread.sleep(1000);
                firstLogin fl = new firstLogin();
                if (!"true".equals(fl.verificationCustom(driver, username))) {
                    driver.close();
                }
                fuYouLogin(username, password);
                driver.close();
            }

            //企业登陆管理
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"dowebok\"]/div[1]/div/a[2]")).click();
            //创建模拟鼠标
            Actions mouse = new Actions(driver);

            //region 员工管理
            //部门列表
            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/a"))).perform();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/ul/li[2]/a")).click();
            departmentList dep = new departmentList();
            //部门添加
            dep.addDep(driver);
            //部门删除
            dep.deleteDep(driver);
            //部门编辑
            dep.updataDep(driver);
            //员工列表
            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/a"))).perform();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/ul/li[1]/a")).click();
            employeeList emp = new employeeList();
            //添加员工
            emp.addEmp(driver);
            //编辑员工
            emp.updateEmp(driver);
            //删除员工
            emp.deleteEmp(driver);
            //搜索员工
            emp.searchEmp(driver);
            //批量导入员工
            emp.batchImportEmp(driver);
            //endregion

            //福利管理 优分订单管理
            Thread.sleep(1000);
            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]/a"))).perform();
            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]/ul/li[3]/a")).click();
            scoreOrder so = new scoreOrder();
            if (!"true".equals(so.replyOrder(driver, username))) {
                driver.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  true;
    }
}
