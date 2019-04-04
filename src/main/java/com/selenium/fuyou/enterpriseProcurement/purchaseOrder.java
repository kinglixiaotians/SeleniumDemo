package com.selenium.fuyou.enterpriseProcurement;

import com.selenium.supplier.supplier;
import org.openqa.selenium.*;
import org.testng.annotations.Test;

import java.util.Set;

import static com.selenium.fuyou.fuYouMethod.clearDate;
import static com.selenium.fuyou.fuYouMethod.inputSearchDate;

public class purchaseOrder {

    /**
     * 查询订单（参数均为查询条件）
     *
     * @param driver
     * @param orderId   订单编号
     * @param goodsName 商品名称
     * @param startTime 下单开始时间（格式：2016-06-06）
     * @param endTime   下单结束时间（格式：2016-06-06）
     */
    @Test
    public void getOrder(WebDriver driver, String orderId, String goodsName, String startTime, String endTime) throws InterruptedException {
        //订单编号
        driver.findElement(By.id("CompanyOrderInformation_txtOrderId")).clear();
        driver.findElement(By.id("CompanyOrderInformation_txtOrderId")).sendKeys(orderId);
        //商品名称
        driver.findElement(By.id("CompanyOrderInformation_txtProductName")).clear();
        driver.findElement(By.id("CompanyOrderInformation_txtProductName")).sendKeys(goodsName);
        //清空下单时间
        clearDate(driver, "CompanyOrderInformation_calendarStartDate", "CompanyOrderInformation_calendarEndDate");
        //输入下单时间
        inputSearchDate(driver, startTime, endTime, "CompanyOrderInformation_calendarStartDate", "CompanyOrderInformation_calendarEndDate");
        //查询
        driver.findElement(By.id("CompanyOrderInformation_imgbtnSearch")).click();

    }

    /**
     * 订单流程-1：（订单已付款）
     * 申请退款 查看订单
     */
    @Test
    public boolean orderProcessRefund(WebDriver driver, String orderId) {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//a[contains(@orderid,'" + orderId + "')]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"xubox_layer1\"]/div[1]/span/a[1]")).click();
            //查看 打开一个新页面
            Thread.sleep(1000);
            driver.findElement(By.id("CompanyOrderInformation_Common_OrderManage_OrderList___listOrders_ctl00_hlinkOrderDetails")).click();

            //region 进入查看页面3秒后关闭查看页面

            //获取当前浏览器打开的页面Handle集合
            Set<String> set = driver.getWindowHandles();
            //转为Object集合用于操作
            Object[] o = set.toArray();
            //定位到查看页面
            driver.switchTo().window(o[1].toString());
            driver.get("http://localhost/Company/CompanyOrderDetails?OrderId=" + orderId);
            Thread.sleep(3000);
            //关闭查看页面
            driver.close();
            //重新定位到原页面
            driver.switchTo().window(o[0].toString());
            driver.get("http://localhost/Company/CompanyOrderInformation");

            //endregion
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 订单流程2：（订单未付款）
     * 查询订单并取消付款
     */
    @Test
    public boolean orderProcessCancelPayment(WebDriver driver, String orderId) {
        try {
            //查询订单
            getOrder(driver, orderId, "", "", "");
            Thread.sleep(500);
            //取消付款，弹出对话框
            driver.findElement(By.id("CompanyOrderInformation_Common_OrderManage_OrderList___listOrders_ctl00_lkbtnCloseOrder")).click();
            //获取弹出的对话框
            Alert alt = driver.switchTo().alert();
            //点击确定
            alt.accept();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //region 测试一下是否能用
    public static Set<String> set;
    public static String newhandler;

    public void goNewTabPage(WebDriver driver, String url) {
        //保存当前浏览器全部页面的句柄
        set = driver.getWindowHandles();
        //新开tab页
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open(" + url + ");");
        //定位到新打开的tab页
        newhandler=driver.getWindowHandle();
        driver.switchTo().window(newhandler);
    }
    //endregion

    /**
     * 订单流程3：（订单未付款）
     * 查询订单-完成付款-供应商发货-查看物流-确认订单
     *
     * @param driver
     * @param orderId
     * @return
     */
    @Test
    public boolean orderProcess(WebDriver driver, String orderId) {
        try {
            getOrder(driver, orderId, "", "", "");
            Thread.sleep(500);
            driver.findElement(By.id("CompanyOrderInformation_Common_OrderManage_OrderList___listOrders_ctl00_hlinkPay")).click();
            //点击付款弹到付款页面
            Thread.sleep(500);
            driver.findElement(By.xpath("/html/body/div[1]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[3]/td/div/button[1]")).click();
            //获取当前浏览器打开的页面Handle集合
            Set<String> set = driver.getWindowHandles();
            //转为Object集合用于操作
            Object[] o = set.toArray();
            //定位到付款页面
            driver.switchTo().window(o[1].toString());
            driver.get("http://localhost/Company/CompanyFinishOrder?orderId=" + orderId);

            //进行付款操作


            //关闭付款页面
            driver.close();
            //重新定位到原页面
            driver.switchTo().window(o[0].toString());
            driver.get("http://localhost/Company/CompanyOrderInformation");

            //查询一次当前订单（刷新）
            getOrder(driver, orderId, "", "", "");

            //进入供应商页面进行发货操作
            Supplier(driver, orderId);
            //发完货关闭发货页面
            driver.close();
            //回到原页面查看物流
            driver.switchTo().window(o[0].toString());
            driver.get("http://localhost/Company/CompanyOrderInformation");
            //确认订单


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 供应商发货
     *
     * @param driver
     * @param orderId
     * @return
     */
    @Test
    public boolean Supplier(WebDriver driver, String orderId) {
        try {
            //浏览器新开一个tab页面
            Thread.sleep(500);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.open();");
            Thread.sleep(500);
            //登录供应商发货
            supplier s = new supplier();
            s.giveExpress(driver, orderId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}