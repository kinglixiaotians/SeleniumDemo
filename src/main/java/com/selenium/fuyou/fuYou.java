package com.selenium.fuyou;

import com.selenium.base.DriverBase;
import com.selenium.fuyou.accountStatement.accountStatement;
import com.selenium.fuyou.announcementManager.announcementList;
import com.selenium.fuyou.emploeeManager.departmentList;
import com.selenium.fuyou.emploeeManager.employeeList;
import com.selenium.fuyou.enterpriseProcurement.enterpriseProcurement;
import com.selenium.fuyou.login.firstLogin;
import com.selenium.fuyou.login.loginValidate;
import com.selenium.fuyou.welfareManager.welfareManager;
import com.selenium.fuyou.transactionManager.electronicInvoice;
import com.selenium.fuyou.transactionManager.transactionRecord;
import com.selenium.utils.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.util.List;

import static com.selenium.fuyou.fuYouMethod.getNavList;

@Slf4j
public class fuYou extends DriverBase {
    DesiredCapabilities caps = setDownloadsPath("E:\\SeleniumDemo1\\src\\main\\resources\\downloadFile");
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
            String s = null;

            //region 员工管理

            if (false) {
                aList = getNavList(driver,"//*[@id=\"fbgg_menu\"]/li[3]","li",3);
                num = aList.size();
                for (int i = num - 1; i >= 0; i--) {
                    Thread.sleep(500);
                    aList = getNavList(driver,"//*[@id=\"fbgg_menu\"]/li[3]","li",3);
                    mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/a"))).perform();
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    aList.get(i).findElement(By.tagName("a")).click();
                    Thread.sleep(500);
                    switch (s) {
                        case "员工列表":
                            employeeListInterface();
                            break;
                        case "部门列表":
                            departmentListInterface();
                            break;
                    }
                }
            }

            //endregion

            //region 福利管理
            if(false) {
                aList = getNavList(driver,"//*[@id=\"fbgg_menu\"]/li[4]","li",3);
                num = aList.size();
                welfareManager w = new welfareManager();
                for (int i = 0; i < num; i++) {
                    aList = getNavList(driver,"//*[@id=\"fbgg_menu\"]/li[4]","li",3);
                    mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[4]/a"))).perform();
                    Thread.sleep(500);
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    Thread.sleep(500);
                    aList.get(i).findElement(By.tagName("a")).click();
                    Thread.sleep(500);
                    switch (s) {
                        case "福利发放":
                            provideWelfare();
                            break;
                        case "团体险":
                            break;
                        case "优分订单管理":
                            break;
                        case "企业收款管理":
                            companyGatheringQrcode();
                            break;
                        case "一卡通兑换":
                            w.companyCardPassExchange(driver, username);
                            break;
                    }
                }
            }
            //endregion

            //region 公告管理

            if(false) {
                Thread.sleep(500);
                aList = getNavList(driver,"//*[@id=\"fbgg_menu\"]/li[5]","li",3);
                announcementList notice = new announcementList();
                mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[5]/a"))).perform();
                Thread.sleep(500);
                aList.get(0).findElement(By.tagName("a")).click();
                notice.announcement(driver);
                notice.deleteAnnouncement(driver);
            }

            //endregion

            //region 交易管理

            if(true){
                Thread.sleep(500);
                aList = getNavList(driver,"//*[@id=\"fbgg_menu\"]/li[6]","li",3);
                num = aList.size();
                for (int i = 0;i < num;i++){
                    Thread.sleep(500);
                    aList = getNavList(driver,"//*[@id=\"fbgg_menu\"]/li[6]","li",3);
                    mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[6]/a"))).perform();
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    Thread.sleep(500);
                    aList.get(i).findElement(By.tagName("a")).click();
                    switch (s){
                        case "交易记录":
                            transactionRecord tr = new transactionRecord();
                            tr.transactionRecordSearch(driver);
                            break;
                        case "电子发票":
                            electronicInvoice ei = new electronicInvoice();
                            ei.electronicInvoiceSearch(driver);
                            break;
                    }
                }
            }

            //endregion

            //region 对账单

            if(true){
                Thread.sleep(500);
                driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[7]/a")).click();
                accountStatement as = new accountStatement();
                as.searchStatement(driver);
            }

            //endregion

            //region 企业采购

            if (true) {
                Thread.sleep(500);
                aList = getNavList(driver,"//*[@id=\"fbgg_menu\"]/li[8]","li",3);
                num = aList.size();
                for (int i = 0; i < num; i++) {
                    Thread.sleep(500);
                    aList = getNavList(driver,"//*[@id=\"fbgg_menu\"]/li[8]","li",3);
                    mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[8]/a"))).perform();
                    s = aList.get(i).findElement(By.tagName("a")).getText();
                    aList.get(i).findElement(By.tagName("a")).click();
                    Thread.sleep(500);
                    switch (s){
                        case "企业采购":
                            enterpriseProcurementInterface();
                            break;
                        case "采购订单":
                            break;
                    }
                }
            }

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

    @Test
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

    //region 员工管理接口

    //部门列表的测试
    public void departmentListInterface() {
        departmentList dep = new departmentList();
        //部门添加
        dep.addDep(driver);
        //部门删除
        dep.deleteDep(driver);
        //部门编辑
        dep.updateDep(driver);
    }

    //员工列表的测试
    public void employeeListInterface() {
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
     * 福利发放
     */
    @Test
    private boolean provideWelfare() {
        try {
            welfareManager w = new welfareManager();
            //单个福利发放
            w.singleProvideWelfare(driver, username);
            Thread.sleep(1000);
            //批量福利发放
            w.multipleprovideWelfare(driver, username);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 企业优分订单回复
     */
    @Test
    public boolean scoreOrderManager(String customNo) {
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
            log.info("回复企业订单--企业号：{}--成功", customNo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("回复企业订单--企业号：{}--失败", customNo);
            return false;
        }

    }

    /**
     * 企业收款管理
     */
    @Test
    public boolean companyGatheringQrcode() {
        try {
            welfareManager w = new welfareManager();
            //添加固定与动态金额各一个
            Thread.sleep(500);
            w.addCompanyGatheringQrcode(driver, true);
            Thread.sleep(500);
            w.addCompanyGatheringQrcode(driver, false);
            Thread.sleep(500);
            //修改
            Thread.sleep(500);
            w.updateCompanyGatheringQrcode(driver, true);
            Thread.sleep(500);
            w.updateCompanyGatheringQrcode(driver, false);
            Thread.sleep(500);
            //给固定金额设置时间段
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[3]/table/tbody/tr[1]/td[7]/div/button[3]")).click();
            Thread.sleep(500);
            w.editFixedCompanyGatheringQrcode(driver);
            //删除
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[3]/table/tbody/tr[1]/td[7]/div/button[1]/span")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("/html/body/div[5]/div/div[3]/button[2]/span")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[3]/table/tbody/tr/td[7]/div/button[1]/span")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("/html/body/div[5]/div/div[3]/button[2]")).click();
            Thread.sleep(2000);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //endregion

    //region 企业采购接口

    //企业采购
    public void enterpriseProcurementInterface(){
        enterpriseProcurement ep = new enterpriseProcurement();
        ep.isHaveAddress(driver);
        ep.updateAddress(driver);
        ep.deleteAddress(driver);
    }

    //endregion

}
