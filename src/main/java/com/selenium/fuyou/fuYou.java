package com.selenium.fuyou;

import com.selenium.base.DriverBase;
import com.selenium.fuyou.announcementManager.announcementList;
import com.selenium.fuyou.emploeeManager.departmentList;
import com.selenium.fuyou.emploeeManager.employeeList;
import com.selenium.fuyou.login.firstLogin;
import com.selenium.fuyou.login.loginValidate;
import com.selenium.fuyou.welfareManager.scoreOrder;
import com.selenium.utils.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.util.List;

@Slf4j
public class fuYou extends DriverBase {
    DesiredCapabilities caps = setDownloadsPath("E:\\2019");
    public WebDriver driver = driverName(caps);
    //key
    public String fuYouUrl = PropertiesConfig.getInstance().getProperty("driver.fuYou.url");
    public String username = PropertiesConfig.getInstance().getProperty("fuYou.username");
    public String password = PropertiesConfig.getInstance().getProperty("fuYou.password");

    @Test
    public void fuYouTest() {
        fuYouLogin(username, password);
    }

    public boolean fuYouLogin(String username, String password) {
        try {
            //登陆
            login(username, password);

            //创建鼠标
            Actions mouse = new Actions(driver);
            List<WebElement> aList = null;
            int num = 0;

            //region 员工管理

            if(false) {
                aList = navContent("//*[@id=\"fbgg_menu\"]/li[3]");
                num = aList.size();
                for (int i = num - 1; i >= 0; i--) {
                    Thread.sleep(500);
                    aList = navContent("//*[@id=\"fbgg_menu\"]/li[3]");
                    mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/a"))).perform();
                    String s = aList.get(i).getText();
                    aList.get(i).click();
                    Thread.sleep(500);
                    switch (s) {
                        case "员工列表":
                            employeeList(driver);
                            break;
                        case "部门列表":
                            departmentList(driver);
                            break;
                    }
                }
            }

            //endregion

            //region 福利管理
            if(false) {
                aList = navContent("//*[@id=\"fbgg_menu\"]/li[4]");
                num = aList.size();
                for (int i = 0; i < num; i++) {
                    aList = navContent("//*[@id=\"fbgg_menu\"]/li[4]");
                    String s = aList.get(i).getText();
                    if (s.equals("团体险")) {
                        continue;
                    }
                    mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]/a"))).perform();
                    aList.get(i).click();
                    Thread.sleep(500);
                    switch (s) {
                        case "福利发放":

                            break;
                        case "优分订单管理":

                            break;
                        case "企业收款管理":

                            break;
                        case "一卡通兑换":

                            break;
                    }
                }
            }
            //endregion

            //region 公告管理
            if(true) {
                aList = navContent("//*[@id=\"fbgg_menu\"]/li[5]");
                announcementList notice = new announcementList();
                mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[5]/a"))).perform();
                Thread.sleep(500);
                aList.get(0).click();
                notice.announcement(driver);
                notice.deleteAnnouncement(driver);
            }
            //endregion

            //region 交易管理
            //endregion

            //region 对账单
            //endregion

            //region 企业采购
            //endregion

            //region 扫码机收入
            //endregion

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //region 登陆

    public boolean login(String username, String password) {
        driver.get(fuYouUrl);
        driver.manage().window().maximize();
        try {
            loginValidate log = new loginValidate();
            boolean flag = log.isExistNotice(driver);
            if (flag) {
                driver.findElement(By.className("notice_close")).click();
                Thread.sleep(500);
            }
            flag = log.isExistTips(driver);
            if (flag) {
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
            if (flag) {
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

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //endregion

    //region 导航选择

    public List<WebElement> navContent(String navPath) {
        WebElement ele = driver.findElement(By.xpath(navPath));
        WebElement foundUl = ele.findElement(By.tagName("ul"));
        List<WebElement> result = foundUl.findElements(By.tagName("a"));
        return result;
    }

    //endregion

    //region 员工管理接口

    //部门列表的测试
    public void departmentList(WebDriver driver) {
        departmentList dep = new departmentList();
        //部门添加
        dep.addDep(driver);
        //部门删除
        dep.deleteDep(driver);
        //部门编辑
        dep.updataDep(driver);
    }

    //员工列表的测试
    public void employeeList(WebDriver driver) {
        employeeList emp = new employeeList();
        //添加员工
        emp.addEmp(driver);
        //批量导入员工
        emp.batchImportEmp(driver);
        //编辑员工
        emp.updateEmp(driver);
        //删除员工
        emp.deleteEmp(driver);
        //搜索员工
        emp.searchEmp(driver);
    }

    //endregion

    //region 福利管理接口

    /**
     * 企业优分订单回复
     *
     * @param customNo
     * @return
     */
    @Test
    public boolean scoreOrderManager(String customNo) {
        try {
            //福利管理 优分订单管理
            Thread.sleep(1000);
            Actions mouse = new Actions(driver);
            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]"))).perform();
            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]/ul/li[3]/a")).click();
            scoreOrder so = new scoreOrder();
            if (!so.replyOrder(driver, customNo)) {
                driver.close();
            }
            log.info("回复企业订单--企业号：{}--成功", customNo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("回复企业订单--企业号：{}--失败", customNo);
            return false;
        }

    }

    //endregion

    //region 交易管理接口
    //endregion

    //region 企业采购接口
    //endregion
}