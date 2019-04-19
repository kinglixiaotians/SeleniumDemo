package com.selenium.fuyou.welfareManager;

import com.selenium.utils.JdbcUtil;
import com.selenium.utils.POIUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.util.ResourceUtils;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static com.selenium.flx.flx.journal;
import static com.selenium.flx.flxPublicMethod.taskScreenShot;
import static com.selenium.fuyou.fuYouMethod.isExistBoxOrExistButton;
import static com.selenium.fuyou.fuYouMethod.nowDate;

@Slf4j
public class welfareManager {

    //region 福利发放

    /**
     * 单个福利发放
     */
    public boolean singleProvideWelfare(WebDriver driver, String customNo) {
        try {
            driver.findElement(By.id("welfareName")).clear();
            Thread.sleep(500);
            driver.findElement(By.id("Score")).sendKeys("100");
            //精确查找
            Thread.sleep(500);
            driver.findElement(By.id("txtkey")).sendKeys("四");
            Thread.sleep(500);
            driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[4]/div")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"fbgg_choice\"]/ul/li[1]")).click();
            Thread.sleep(500);
            driver.findElement(By.id("txtkey")).clear();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"fbgg_choice\"]/ul/li[3]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"fbgg_choice\"]/ul/li[5]")).click();
            //删除已选成员
            Thread.sleep(1000);
            listDelete(driver);
            //全选
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"fbgg_choice\"]/ul/li[1]")).click();
            //获取验证码
            driver.findElement(By.id("btnyzm")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("zeromodal-close")).click();
            Thread.sleep(1000);
            String cord = getCord(customNo);
            driver.findElement(By.id("mobileCode")).sendKeys("asdf342");
            Thread.sleep(1000);
            driver.findElement(By.id("btnSubmit")).click();
            //福利名目不能为空
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[7]/div[2]/div")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("welfareName")).sendKeys("测试");
            Thread.sleep(1000);
            driver.findElement(By.id("btnSubmit")).click();
            //验证码有误
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[7]/div[2]/div")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mobileCode")).sendKeys(cord);
            Thread.sleep(1000);
            driver.findElement(By.id("btnSubmit")).click();
            //判断是否发放完成或出现优分不足

            while (isExistBoxOrExistButton(driver,"xubox_shade2",0))
                Thread.sleep(1000);

            if (isElementPresent(driver)) {
                driver.findElement(By.className("zeromodal-close")).click();
            }
            Thread.sleep(1000);
            log.info("单个福利发放成功--企业号：{}", customNo);
            Reporter.log("单个福利发放成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            taskScreenShot(driver);
            Reporter.log("单个福利发放失败。错误：" + e.toString());
            return false;
        }
    }

    /**
     * 批量福利发放
     */
    public boolean multipleprovideWelfare(WebDriver driver, String customNo) {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("/html/body/div[4]/ul/li[2]/a")).click();
            //点击确认提交  提示   请上传分配表格
            Thread.sleep(1000);
            driver.findElement(By.id("binImport")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[7]/div[2]/div")).click();
            //下载分配模板(员工账户)
            driver.findElement(By.id("Button1")).click();
            Thread.sleep(3000);
            //直接上传优分为0的模版
            testFiles(driver);
            Thread.sleep(2000);
            //点击确认提交  提示   福利名不能为空
            driver.findElement(By.id("binImport")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[7]/div[2]/div")).click();
            //输入福利名目
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"welfareName\"]")).sendKeys("测试" + nowDate());
            Thread.sleep(500);
            //点击确认提交  提示  验证码不能为空
            Thread.sleep(1000);
            driver.findElement(By.id("binImport")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[7]/div[2]/div")).click();
            //获取验证码
            Thread.sleep(1000);
            driver.findElement(By.id("btnyzm")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[7]/div[2]/div")).click();
            //填入错误验证码  点击确认提交  提示  验证码有误
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[14]/input[1]")).sendKeys("asdf");
            Thread.sleep(1000);
            driver.findElement(By.id("binImport")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[7]/div[2]/div")).click();
            //输入正确验证码  提示  优分不能为0
            String cord = getCord(customNo);
            updateInput(driver, "/html/body/div[4]/div[3]/div[14]/input[1]", cord);
            Thread.sleep(1000);
            driver.findElement(By.id("binImport")).click();
            //刷新页面（可以重新获取验证码）
            driver.navigate().refresh();
            //修改并上传
            driver.findElement(By.xpath("//*[@id=\"welfareName\"]")).sendKeys("测试" + nowDate());
            uploadFiles(driver, customNo);
            Thread.sleep(1000);
            //是否优分不足
            if (isElementPresent(driver)) {
                driver.findElement(By.className("zeromodal-close")).click();
            } else {
                Thread.sleep(1000);
                driver.findElement(By.className("btn-again")).click();
                //输入福利名目
                driver.findElement(By.xpath("//*[@id=\"welfareName\"]")).sendKeys("测试" + nowDate());
                //下载分配模板(员工工号)
                driver.findElement(By.id("Button2")).click();
                Thread.sleep(1000);
                //修改并上传
                uploadFiles(driver, customNo);
                if (isElementPresent(driver)) {
                    Thread.sleep(1000);
                    waitClick(driver, "zeromodal-close", 1);
                }
            }
            log.info("批量福利发放成功--企业号：{}", customNo);
            Reporter.log("批量福利发放成功。");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            taskScreenShot(driver);
            Reporter.log("批量福利发放失败。错误：" + e.toString());
            return false;
        }
    }

    //删除已选成员
    public void listDelete(WebDriver driver) {
        List ls = driver.findElements(By.xpath("//*[@id=\"fbgg_con_bg\"]/div"));
        //遍历每个权限
        for (int i = 0; i < ls.size(); i++) {
            WebElement element = (WebElement) ls.get(i);
            element.findElement(By.cssSelector("div>i")).click();
        }
    }

    //判断是否出现优分不足
    public boolean isElementPresent(WebDriver driver) {
        try {
            driver.findElement(By.className("zeromodal-close"));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    //直接上传优分为0的模版
    public void testFiles(WebDriver driver) throws Exception {
        //定义文件夹
        String path = "E:\\SeleniumDemo1\\src\\main\\resources\\downloadFile";
        //查询文件夹下以xls结尾的文件
        Thread.sleep(1000);
        String fileName = POIUtil.xlsUrl(path);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"select_btn_1\"]")).sendKeys(fileName);
    }

    //修改下载的模版优分并上传
    public void uploadFiles(WebDriver driver, String customNo) throws Exception {
        //定义文件夹
        String path = "E:\\SeleniumDemo1\\src\\main\\resources\\downloadFile";
        //查询文件夹下以xls结尾的文件
        Thread.sleep(1000);
        String fileName = POIUtil.xlsUrl(path);
        //读取
        Thread.sleep(1000);
        File file = ResourceUtils.getFile(fileName);
        //修改优分余额
        Thread.sleep(1000);
        POIUtil.readExcel(file);
        //上传
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"select_btn_1\"]")).sendKeys(fileName);
        //获取验证码
        Thread.sleep(3000);
        driver.findElement(By.id("btnyzm")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[7]/div[2]/div")).click();
        Thread.sleep(1000);
        String cord = getCord(customNo);
        driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[14]/input[1]")).sendKeys(cord);
        Thread.sleep(1000);
        driver.findElement(By.id("binImport")).click();
        Thread.sleep(1000);
        //上传操作完成后删除下载的员工模版
        file.delete();
    }
    //endregion

    /**
     * 回复企业订单
     */
    public boolean replyOrder(WebDriver driver, String customNo) {
        try {
            //回复订单
            Thread.sleep(1000);
            driver.findElement(By.xpath("//tbody/tr[@id='spqingdan_title']/following-sibling::tr/td[9]/a")).click();
            //获取验证码
            driver.findElement(By.id("btnyzm")).click();
            Thread.sleep(1000);
            driver.findElement(By.className("zeromodal-close")).click();
            //通过customNo获取验证码
            Thread.sleep(2000);
            String cord = getCord(customNo);
            driver.findElement(By.id("mobileCode")).sendKeys(cord);
            Thread.sleep(1000);
            driver.findElement(By.name("btnReply")).click();
            if (journal) {
                log.info("回复企业订单--企业号：{}--成功", customNo);
                Reporter.log("回复企业订单成功，企业号：" + customNo);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (journal) {
                taskScreenShot(driver);
                Reporter.log("回复企业订单失败。错误：" + e.toString());
            }
            return false;
        }
    }

    //region 企业收款管理

    /**
     * 企业收款管理
     */
    public boolean companyGatheringQrcode(WebDriver driver, String customNo) {
        try {
            //添加固定与动态金额各一个
            if (!addCompanyGatheringQrcode(driver, true) || !addCompanyGatheringQrcode(driver, false))
                return false;
            //修改
            if (!updateCompanyGatheringQrcode(driver, true) || !updateCompanyGatheringQrcode(driver, false))
                return false;
            //给固定金额设置时间段
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[3]/table/tbody/tr[1]/td[7]/div/button[3]")).click();
            Thread.sleep(500);
            if (!editFixedCompanyGatheringQrcode(driver))
                return false;
            //删除
            Thread.sleep(1000);
            waitClick(driver, "//*[@id=\"app\"]/div[1]/div[2]/div[3]/table/tbody/tr[1]/td[7]/div/button[1]/span", 3);
            Thread.sleep(1000);
            waitClick(driver, "/html/body/div[5]/div/div[3]/button[2]/span", 3);
            Thread.sleep(1000);
            waitClick(driver, "//*[@id=\"app\"]/div[1]/div[2]/div[3]/table/tbody/tr/td[7]/div/button[1]/span", 3);
            Thread.sleep(1000);
            waitClick(driver, "/html/body/div[5]/div/div[3]/button[2]", 3);
            Thread.sleep(1000);
            log.info("企业收款管理测试成功--企业号：{}", customNo);
            Reporter.log("企业收款管理测试成功，企业号：" + customNo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            taskScreenShot(driver);
            Reporter.log("企业收款管理测试失败。错误：" + e.toString());
            return false;
        }
    }

    //出现此元素就点击若不出现则一直等（每一秒判断一次）
    public void waitClick(WebDriver driver, String url, int num) throws InterruptedException {
        while (!isExistBoxOrExistButton(driver, url, num)) {
            Thread.sleep(1000);
        }
        switch (num) {
            case 0:
                driver.findElement(By.id(url)).click();
                break;
            case 1:
                driver.findElement(By.className(url)).click();
                break;
            case 2:
                driver.findElement(By.cssSelector(url)).click();
                break;
            case 3:
                driver.findElement(By.xpath(url)).click();
                break;
        }
    }

    //设置固定金额的收款
    public boolean editFixedCompanyGatheringQrcode(WebDriver driver) {
        try {
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[1]/div/button")).click();
            //公司账号（必须为正确已有）
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr/td[2]/div/div/input", "01510181");
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr/td[3]/div/div/div/input", "10");
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr/td[5]/div/div/input", "11:59:59");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[1]/div/h3")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr/td[6]/div/button[2]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[1]/div/button")).click();
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[2]/div/div/input", "1543213");
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[3]/div/div/div/input", "-243");
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[4]/div/div/input", "11:00:00");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[1]/div/h3")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[6]/div/button[2]")).click();
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[3]/div/div/div/input", "15");
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[6]/div/button[2]")).click();
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[2]/div/div/input", "01510181");
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[6]/div/button[2]")).click();
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[4]/div/div/input", "12:00:00");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[1]/div/h3")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[2]/td[6]/div/button[2]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[1]/div/button/span")).click();
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr/td[2]/div/div/input", "01510182");
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr/td[3]/div/div/div/input", "20");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[3]/td[6]/div/button[2]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[3]/td[6]/div/button[2]")).click();
            Thread.sleep(1000);
            updateInput(driver, "//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr/td[3]/div/div/div/input", "18");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[3]/td[6]/div/button[2]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div[3]/table/tbody/tr[3]/td[6]/div/button[1]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[5]/div/div[3]/button[2]/span")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[3]/div/div[1]/button/i")).click();
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //清除框内数据并重新赋值
    public void updateInput(WebDriver driver, String xpath, String str) {
        driver.findElement(By.xpath(xpath)).clear();
        driver.findElement(By.xpath(xpath)).sendKeys(str);
    }

    //根据企业号返回验证码
    public String getCord(String customNo) {
        JdbcUtil j = new JdbcUtil();
        return j.querySmsCode(j.queryCellPhone(customNo));
    }

    //修改企业收款管理(b为是否固定金额)
    //固定金额修改为动态金额，动态金额改为固定金额
    public boolean updateCompanyGatheringQrcode(WebDriver driver, boolean b) {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[3]/table/tbody/tr[1]/td[7]/div/button[2]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[2]/div/div/div/span")).click();
            Thread.sleep(500);
            if (!b) {
                updateInput(driver, "//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[3]/div/div/div/div/input", "5");
            }
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[3]/div/button[2]/span")).click();
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //增加企业收款管理(b为是否固定金额)
    public boolean addCompanyGatheringQrcode(WebDriver driver, boolean b) {
        try {
            //新增
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/div/button[1]")).click();
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[1]/div/div/div/input")).sendKeys("测试收款" + nowDate());
            //是否固定金额
            if (b) {
                updateInput(driver, "//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[3]/div/div/div/div/input", "10");
            } else {
                driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[2]/form/div/div[2]/div/div/div/span")).click();
            }
            // 确定
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[3]/div/button[2]/span")).click();
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //endregion

    /**
     * 一卡通兑换
     */
    public boolean companyCardPassExchange(WebDriver driver, String customNo) {
        try {
            //兑换金额   金额格式：大于0，可保留两位小数；例如：500 或 450.25
            driver.findElement(By.id("exchangeAmount")).sendKeys("100");
            //获取验证码
            driver.findElement(By.id("btnyzm")).click();
            Thread.sleep(1000);
            waitClick(driver, "zeromodal-close", 1);
            Thread.sleep(1000);
            String cord = getCord(customNo);
            driver.findElement(By.id("mobileCode")).sendKeys(cord);
            //确认兑换
            driver.findElement(By.id("btnSubmit")).click();
            Thread.sleep(2000);
            driver.findElement(By.className("zeromodal-close")).click();
            Thread.sleep(1000);
            if (isExistBoxOrExistButton(driver, "zeromodal-title1", 1)) {
                if ("企业余额不足".equals(driver.findElement(By.className("zeromodal-title1")).getText())) {
                    log.info("一卡通兑换失败，企业余额不足");
                    Reporter.log("一卡通兑换失败，企业余额不足");
                    return false;
                }
            }
            Reporter.log("一卡通兑换成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("一卡通兑换失败");
            taskScreenShot(driver);
            Reporter.log("一卡通兑换失败。错误：" + e.toString());
            return false;
        }
    }

}
