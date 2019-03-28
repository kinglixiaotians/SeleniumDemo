package com.selenium.fuyou;

import com.selenium.base.DriverBase;
import com.selenium.fuyou.emploeeManager.departmentList;
import com.selenium.fuyou.emploeeManager.employeeList;
import com.selenium.fuyou.login.firstLogin;
import com.selenium.fuyou.login.loginValidate;
import com.selenium.fuyou.welfareManager.scoreOrder;
import com.selenium.utils.PropertiesConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;


public class fuyou extends DriverBase {
    public WebDriver driver = driverName();

    //key
    public String fuyouUrl = PropertiesConfig.getInstance().getProperty("driver.fuyou.url");
    public String username = PropertiesConfig.getInstance().getProperty("fuyou.username");
    public String password = PropertiesConfig.getInstance().getProperty("fuyou.password");

    @Test
    public void fuyouTest() {
        fuyouLogin(username, password);
    }

    @Test
    public boolean fuyouLogin(String username, String password) {
        driver.get(fuyouUrl);
        driver.manage().window().maximize();
        try {
            driver.findElement(By.className("notice_close")).click();
            Thread.sleep(500);
            driver.findElement(By.className("layui-layer-setwin")).click();
            driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/label[1]/a")).click();
            Thread.sleep(500);
            driver.findElement(By.id("username")).sendKeys(username);
            Thread.sleep(500);
            driver.findElement(By.id("password")).sendKeys(password);
            loginValidate log = new loginValidate();
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

            //判断是否为企业账号首次登录
            //首次登录企业账号需要进行账号验证并激活！
            Thread.sleep(1000);
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals(fuyouUrl + "Company/CompanyFirstLoad")) {
                Thread.sleep(1000);
                firstLogin fl = new firstLogin();
                if (!"true".equals(fl.verificationCustom(driver, username))) {
                    driver.close();
                }
                fuyouLogin(username, password);
                driver.close();
                return true;
            }

            //企业登陆管理
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"dowebok\"]/div[1]/div/a[2]")).click();
            //创建模拟鼠标
            Actions mouse = new Actions(driver);
            //福利管理 优分订单管理
            Thread.sleep(1000);
            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]/a"))).perform();
            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]/ul/li[3]/a")).click();
            scoreOrder so = new scoreOrder();
            if (!"true".equals(so.replyOrder(driver, username))) {
                driver.close();
            }
//            //员工管理
//            Thread.sleep(1000);
//            //部门列表
//            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/a"))).perform();
//            //部门添加
//            departmentList.addDep(driver);
//            //部门删除
//            departmentList.deleteDep(driver);
//            //部门编辑
//            departmentList.updataDep(driver);
//            Thread.sleep(1000);
//            //员工列表
//            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/a"))).perform();
//            //添加员工
//            employeeList.addEmp(driver);
//            //编辑员工
//            employeeList.updataEmp(driver);
//            //删除员工
//            employeeList.deleteEmp(driver);
//            //搜索员工
//            employeeList.searchEmp(driver);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
            return false;
        }
    }
}
