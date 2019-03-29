package com.selenium.flx.custom;

import com.selenium.utils.PhoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 开户
 */
@Slf4j
public class sepecEditCustom {
    public String customNo;

    /**
     * 开户账户01:
     * 企业客户开户信用卡还款固定手续费:
     * 协议信息除了企业批量还信用卡之外全部勾上;
     * 信用卡协议信息:
     * 信用卡单笔收费为3.00;不存在阶梯费率
     * 单笔还款手续费上限(元):为默认值, 单笔还款手续费下限(元): 3.00
     * 个人账户还信用卡单日上限(元)为:30000.00;   信用卡单日接收还款上限(元):10000.00
     * <p>
     * 积分兑换协议信息
     * 积分兑换百分比收费费率:3.00
     *
     * @param driver
     */
    @Test
    public boolean custom01(WebDriver driver) {
        try {

            this.saveCustomTop(driver);
            //进入协议信息
            driver.findElement(By.xpath("//*[@id=\"mini-2$3\"]/span")).click();
            //勾选业务权限，除去企业批量还信用卡
            ArrayList except = new ArrayList();
            except.add("mini-62$17");
            list(driver, except);

            //更改信用卡协议信息
            //输入框存在默认值时，若需要修改其值。使用sendKeys(Keys.chord(Keys.CONTROL,"a"),"value") value为需要更改的值
            driver.findElement(By.id("contract.creditSingleFee$text")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "3.00");
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-remove")).click();
            driver.findElement(By.id("contract.individualCountToCreditUp$text")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "30000.00");

            //更改积分兑换协议信息
            driver.findElement(By.id("contract.exchangeServiceFee$text")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "3.00");

            this.saveCustomBottom(driver);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 开户账户02:
     * 企业客户开户信用卡还款阶梯手续费:
     * 协议信息除了企业批量还信用卡之外全部勾上;
     * 信用卡协议信息:
     * 信用卡单笔收费为0.00;信用卡阶梯费率金额(起)为0, 金额(止)为空, 费率为3
     * 单笔还款手续费上限(元):100.00, 单笔还款手续费下限(元): 3.00
     * 个人账户还信用卡单日上限(元)为:30000.00;   信用卡单日接收还款上限(元):10000.00
     * <p>
     * 积分兑换协议信息
     * 积分兑换百分比收费费率:3.00
     *
     * @param driver
     */
    @Test
    public boolean custom02(WebDriver driver) {
        try {


            this.saveCustomTop(driver);
            //进入协议信息
            driver.findElement(By.xpath("//*[@id=\"mini-2$3\"]/span")).click();
            //勾选业务权限，除去企业批量还信用卡
            ArrayList except = new ArrayList();
            except.add("mini-62$17");
            list(driver, except);

            //更改信用卡协议信息
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-remove")).click();
            driver.findElement(By.id("stepRate.rate1")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "3");
            driver.findElement(By.id("contract.creditSingleUp$text")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "100.00");
            driver.findElement(By.id("contract.individualCountToCreditUp$text")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "30000.00");

            //更改积分兑换协议信息
            driver.findElement(By.id("contract.exchangeServiceFee$text")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "3.00");

            this.saveCustomBottom(driver);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 正常流程开户
     *
     * @param driver
     */
    @Test
    public void normalCustom(WebDriver driver) {
        this.saveCustomTop(driver);
        //进入协议信息
        driver.findElement(By.xpath("//*[@id=\"mini-2$3\"]/span")).click();
        //勾选业务权限，除去企业批量还信用卡
        ArrayList except = new ArrayList();
        except.add("mini-62$17");
        list(driver, except);
        this.saveCustomBottom(driver);
    }

    /**
     * 添加客户信息
     *
     * @param driver
     */
    @Test
    public void saveCustomTop(WebDriver driver) {
        try {
            //获取当前时间并进行格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = sdf.format(new Date());
            //点击添加按钮，弹出添加界面
            driver.switchTo().frame("mainframe");
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-add")).click();
            //返回主窗体
            driver.switchTo().defaultContent();
            Thread.sleep(1000);
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/custom/cusprofile/editCusProfileNew.jsp')]")));
            System.out.println(driver.getTitle());
            customNo = driver.findElement(By.name("entity.customNo")).getAttribute("value");
            driver.findElement(By.id("entity.company$text")).sendKeys("ceshi" + date);
            //点击选择业务员
            driver.findElement(By.xpath("//*[@id=\"entity.salesmanid\"]/span/span/span[2]/span")).click();
            //返回主窗体切换业务员iframe
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/pub/selectcontrol/choice.jsp')]")));
            //获取第一个checkbox---业务员姓名
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-ok")).click();
            //返回主窗体切换业务员iframe---进入添加页面
            driver.switchTo().defaultContent();
            Thread.sleep(1000);
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'/FlxServer/custom/cusprofile/editCusProfileNew.jsp')]")));
            //正确的授权人姓名，错误的身份证和电话
            driver.findElement(By.id("entity.contactPerson$text")).click();
            driver.findElement(By.id("entity.contactPerson$text")).sendKeys("test" + date);
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("saefea");
            driver.findElement(By.id("entity.cellPhone$text")).sendKeys("2345s423");
            //主站需要进行实名认证,点击后提示输入的身份证号码有误---点击alert继续
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@class='mini-messagebox-buttons']//a")).click();
            //输入正确的身份证号
            driver.findElement(By.id("entity.contactPersonIdcard$text")).clear();
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("420984199701051755");
            //继续实名认证,点击后提示输入的手机号码有误---点击alert继续
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@class='mini-messagebox-buttons']//a")).click();
            //输入正确的电话号码
            driver.findElement(By.id("entity.cellPhone$text")).clear();
            driver.findElement(By.id("entity.cellPhone$text")).sendKeys(PhoneUtil.getTelephone());
            //继续实名认证
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(3000);
            //认证失败---点击alert继续
            driver.findElement(By.xpath("//*[@class='mini-messagebox-buttons']//a")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存信息
     *
     * @param driver
     */
    @Test
    public void saveCustomBottom(WebDriver driver) {
        try {
            //保存
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-123")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@class='mini-messagebox-buttons']/a[2]")).click();
            driver.switchTo().defaultContent();
            log.info("客户管理--开户:{}--成功",customNo);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("客户管理--开户:{}--失败",customNo);
        }
    }

    /**
     * 勾选业务权限
     * except为需要取消勾选的id集合
     *
     * @param driver
     * @param except
     */
    @Test
    public void list(WebDriver driver, List<String> except) {
        //勾选业务权限，商城业务与蜘蛛网默认勾选，先取消掉
        driver.findElement(By.id("mini-62$ck$5")).click();
        driver.findElement(By.id("mini-62$ck$4")).click();
        //获取全部的权限集合
        List ls = driver.findElements(By.xpath("//div[starts-with(@class,'mini-checkboxlist-item')]"));
        //遍历每个权限
        for (int i = 0; i < ls.size(); i++) {
            //获取当前权限的id
            WebElement element = (WebElement) ls.get(i);
            String str = element.getAttribute("id");
            //判断是否需要取消勾选
            if (except.indexOf(str) == -1) {
                element.findElement(By.cssSelector("div>input")).click();
            }
        }
    }
}
