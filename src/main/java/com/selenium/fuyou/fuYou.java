package com.selenium.fuyou;

import com.selenium.base.DriverBase;
import com.selenium.fuyou.accountStatement.accountStatement;
import com.selenium.fuyou.announcementManager.announcementList;
import com.selenium.fuyou.employeeManager.departmentList;
import com.selenium.fuyou.employeeManager.employeeList;
import com.selenium.fuyou.enterpriseProcurement.enterpriseProcurement;
import com.selenium.fuyou.enterpriseProcurement.purchaseOrder;
import com.selenium.fuyou.login.firstLogin;
import com.selenium.fuyou.login.loginValidate;
import com.selenium.fuyou.welfareManager.welfareManager;
import com.selenium.fuyou.transactionManager.electronicInvoice;
import com.selenium.fuyou.transactionManager.transactionRecord;
import com.selenium.utils.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.selenium.flx.flxPublicMethod.taskScreenShot;
import static com.selenium.fuyou.fuYouMethod.*;

@Slf4j
public class fuYou extends DriverBase {
    DesiredCapabilities caps = setDownloadsPath("E:\\SeleniumDemo1\\src\\main\\resources\\downloadFile");
    public WebDriver driver = driverName(caps);
    //key
    public String fuYouUrl = PropertiesConfig.getInstance().getProperty("driver.fuYou.url");
    public String username = PropertiesConfig.getInstance().getProperty("fuYou.username");
    public String password = PropertiesConfig.getInstance().getProperty("fuYou.password");

    //创建鼠标
    Actions mouse = new Actions(driver);
    //顶部导航栏列表
    List<WebElement> list = null;

    List<WebElement> aList = null;
    //对应顶部导航栏的index
    int navIndex = 0;

    int num = 0;

    String s = null;

    departmentList dep = new departmentList();
    employeeList emp = new employeeList();
    enterpriseProcurement ep = new enterpriseProcurement();

    @Test
    public void fuYouTest() {
        driver.get(fuYouUrl);
        driver.manage().window().maximize();
    }

    //region 登陆
    @Test(dependsOnMethods = "fuYouTest", description = "登陆")
    public boolean login() {
        try {
            loginValidate log = new loginValidate();
            //判断是否存在广告
            boolean flag = isExistBoxOrExistButton(driver, "notice", 1);
            if (flag) {
                driver.findElement(By.className("notice_close")).click();
                Thread.sleep(500);
            }

            //判断是否存在提示窗体
            flag = isExistBoxOrExistButton(driver, "layui-layer-shade1", 0);
            if (flag) {
                driver.findElement(By.className("layui-layer-setwin")).click();
                Thread.sleep(500);
            }


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
            while (isAlertPresent(driver)) {
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
            flag = isExistBoxOrExistButton(driver, "qyBeginIcon", 1);
            if (flag) {
                Thread.sleep(500);
                driver.findElement(By.className("qyCloseIcon")).click();
            }

            //判断是否为企业账号首次登录
            //首次登录企业账号需要进行账号验证并激活！
            Thread.sleep(1000);
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals(fuYouUrl + "Company/CompanyFirstLoad")) {
                firstLogin fl = new firstLogin();
                if (!fl.verificationCustom(driver, username)) {
                    driver.close();
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            driver.get(fuYouUrl);
            driver.manage().window().maximize();
            loginValidate log = new loginValidate();
            boolean flag = isExistBoxOrExistButton(driver, "notice", 1);
            if (flag) {
                driver.findElement(By.className("notice_close")).click();
                Thread.sleep(500);
            }
            flag = isExistBoxOrExistButton(driver, "layui-layer-shade1", 0);
            if (flag) {
                driver.findElement(By.className("layui-layer-setwin")).click();
                Thread.sleep(500);
            }
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
            while (isAlertPresent(driver)) {
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
            flag = isExistBoxOrExistButton(driver, "qyBeginIcon", 1);
            if (flag) {
                Thread.sleep(500);
                driver.findElement(By.className("qyCloseIcon")).click();
            }
            //判断是否为企业账号首次登录
            //首次登录企业账号需要进行账号验证并激活！
            Thread.sleep(1000);
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals(fuYouUrl + "Company/CompanyFirstLoad")) {
                firstLogin fl = new firstLogin();
                if (!fl.verificationCustom(driver, username)) {
                    driver.close();
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            taskScreenShot(driver);
            Reporter.log("fuyou登录失败。错误：" + e.toString());
            return false;
        }
    }

    //endregion

    //region 员工管理
    @Test(dependsOnMethods = "login", description = "部门管理")
    public void departmentList() {
        try {
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "员工管理");
            if (navIndex != 0) {
                aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                num = aList.size();
                for (int i = num - 1; i >= 0; i--) {
                    Thread.sleep(500);
                    list = getNavList(driver, null, "fbgg_menu", "li", 0);
                    navIndex = getNavListId(driver, "员工管理");
                    aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                    mouse.moveToElement(list.get(navIndex)).perform();
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    Thread.sleep(500);
                    if (s.equals("部门列表")) {
                        aList.get(i).findElement(By.tagName("a")).click();
                        //部门添加
                        dep.addDep(driver);
                        //部门删除
                        dep.deleteDep(driver);
                        //部门编辑
                        dep.updateDep(driver);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //员工列表
    @Test(dependsOnMethods = "departmentList", description = "员工列表")
    public void employeeList() {

        try {
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "员工管理");
            if (navIndex != 0) {
                aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                num = aList.size();
                for (int i = num - 1; i >= 0; i--) {
                    Thread.sleep(500);
                    list = getNavList(driver, null, "fbgg_menu", "li", 0);
                    navIndex = getNavListId(driver, "员工管理");
                    aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                    mouse.moveToElement(list.get(navIndex)).perform();
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    Thread.sleep(500);
                    if (s.equals("员工列表")) {
                        aList.get(i).findElement(By.tagName("a")).click();
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
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region 福利管理

    @Test(dependsOnMethods = "employeeList", description = "福利发放")
    public void welfarePayment() {
        try {
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "福利管理");
            if (navIndex != 0) {
                aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                num = aList.size();
                welfareManager w = new welfareManager();
                for (int i = 0; i < num; i++) {
                    list = getNavList(driver, null, "fbgg_menu", "li", 0);
                    navIndex = getNavListId(driver, "福利管理");
                    aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                    mouse.moveToElement(list.get(navIndex)).perform();
                    Thread.sleep(500);
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    Thread.sleep(500);
                    aList.get(i).findElement(By.tagName("a")).click();
                    Thread.sleep(500);
                    if (s.equals("福利发放")) {
                        if(!provideWelfare())
                            driver.findElement(By.id("asdf")).click();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "welfarePayment", description = "企业收款管理")
    public void enterpriseReceiptManager() {
        try {
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "福利管理");
            if (navIndex != 0) {
                aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                num = aList.size();
                welfareManager w = new welfareManager();
                for (int i = 0; i < num; i++) {
                    list = getNavList(driver, null, "fbgg_menu", "li", 0);
                    navIndex = getNavListId(driver, "福利管理");
                    aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                    mouse.moveToElement(list.get(navIndex)).perform();
                    Thread.sleep(500);
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    Thread.sleep(500);
                    aList.get(i).findElement(By.tagName("a")).click();
                    Thread.sleep(500);
                    if (s.equals("企业收款管理")) {
                        if(!w.companyGatheringQrcode(driver, username))
                            driver.findElement(By.id("asdf")).click();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "enterpriseReceiptManager", description = "一卡通兑换")
    public void cardExchange() {
        try {
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "福利管理");
            if (navIndex != 0) {
                aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                num = aList.size();
                welfareManager w = new welfareManager();
                for (int i = 0; i < num; i++) {
                    list = getNavList(driver, null, "fbgg_menu", "li", 0);
                    navIndex = getNavListId(driver, "福利管理");
                    aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                    mouse.moveToElement(list.get(navIndex)).perform();
                    Thread.sleep(500);
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    Thread.sleep(500);
                    aList.get(i).findElement(By.tagName("a")).click();
                    Thread.sleep(500);
                    if (s.equals("一卡通兑换")) {
                        if(!w.companyCardPassExchange(driver, username))
                            driver.findElement(By.id("asdf")).click();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 福利发放
     */
    private boolean provideWelfare() {
        try {
            welfareManager w = new welfareManager();
            //单个福利发放 批量福利发放
            return w.singleProvideWelfare(driver, username) && w.multipleprovideWelfare(driver, username);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 企业优分订单回复
     */
    //@Test
    public boolean replyCustomOrder(String customNo) {
        try {
            //福利管理 优分订单管理
            Thread.sleep(1000);
            Actions mouse = new Actions(driver);
            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]"))).perform();
            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]/ul/li[3]/a")).click();
            welfareManager w = new welfareManager();
            if (!w.replyOrder(driver, customNo)) {
                driver.close();
            }
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            taskScreenShot(driver);
            Reporter.log("回复企业订单--企业号：" + customNo + "--失败。错误：" + e.toString());
            return false;
        }

    }


    //endregion

    //region 公告管理
    @Test(dependsOnMethods = "cardExchange", description = "公告管理")
    public void announcementManager() {
        try {
            Thread.sleep(500);
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "公告管理");
            if (navIndex != 0) {
                aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                announcementList notice = new announcementList();
                mouse.moveToElement(list.get(navIndex)).perform();
                Thread.sleep(500);
                aList.get(0).findElement(By.tagName("a")).click();
                notice.announcement(driver);
                notice.deleteAnnouncement(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region 交易管理
    @Test(dependsOnMethods = "announcementManager", description = "交易记录")
    public void electronicInvoiceManager() {
        try {
            Thread.sleep(500);
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "交易管理");
            if (navIndex != 0) {
                aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                num = aList.size();
                for (int i = 0; i < num; i++) {
                    Thread.sleep(500);
                    list = getNavList(driver, null, "fbgg_menu", "li", 0);
                    navIndex = getNavListId(driver, "交易管理");
                    aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                    Thread.sleep(500);
                    mouse.moveToElement(list.get(navIndex)).perform();
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    Thread.sleep(500);

                    if (s.equals("交易记录")) {
                        aList.get(i).findElement(By.tagName("a")).click();
                        transactionRecord tr = new transactionRecord();
                        tr.transactionRecordSearch(driver);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "electronicInvoiceManager", description = "电子发票")
    public void transactionRecordManager() {
        try {
            Thread.sleep(500);
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "交易管理");
            if (navIndex != 0) {
                aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                num = aList.size();
                for (int i = 0; i < num; i++) {
                    Thread.sleep(500);
                    list = getNavList(driver, null, "fbgg_menu", "li", 0);
                    navIndex = getNavListId(driver, "交易管理");
                    aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                    Thread.sleep(500);
                    mouse.moveToElement(list.get(navIndex)).perform();
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    Thread.sleep(500);
                    if (s.equals("电子发票")) {
                        aList.get(i).findElement(By.tagName("a")).click();
                        electronicInvoice ei = new electronicInvoice();
                        ei.electronicInvoiceSearch(driver);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region 对账单
    @Test(dependsOnMethods = "transactionRecordManager", description = "对账单")
    public void accountStatementManager() {
        try {
            Thread.sleep(500);
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "对账单");
            if (navIndex != 0) {
                list.get(navIndex).click();
                accountStatement as = new accountStatement();
                as.searchStatement(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region 企业采购

    //企业采购
    @Test(dependsOnMethods = "accountStatementManager", description = "企业采购")
    public void enterpriseProcurementInterface() {
        try {
            Thread.sleep(500);
            list = getNavList(driver, null, "fbgg_menu", "li", 0);
            navIndex = getNavListId(driver, "企业采购");
            if (navIndex != 0) {
                aList = getNavList(driver, list.get(navIndex), "", "li", 0);
                mouse.moveToElement(list.get(navIndex)).perform();
                Thread.sleep(500);
                aList.get(0).findElement(By.tagName("a")).click();

                ep.isHaveAddress(driver);
                ep.updateAddress(driver);
                ep.deleteAddress(driver);
                ep.navMenu(driver);
                ep.searchProduct(driver);
                ep.purchaseGoods(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion

}
