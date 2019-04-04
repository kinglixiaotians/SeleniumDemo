package com.selenium.fuyou.enterpriseProcurement;

import com.selenium.utils.JdbcUtil;
import com.selenium.utils.PhoneUtil;
import jdk.nashorn.internal.ir.WhileNode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.selenium.fuyou.fuYouMethod.getNavList;

public class enterpriseProcurement {

    //region 地址

    //region 用户是否有地址以及添加地址

    @Test
    public void isHaveAddress(WebDriver driver){
        try{
            String s = driver.findElement(By.className("chooseAddressHeader")).getText();
            if(s.equals("请选择收货地址")){
                Thread.sleep(500);
                driver.findElement(By.className("imgCloseLogin")).click();
                Thread.sleep(500);
                boolean flag = isAlertPresent(driver);
                Thread.sleep(500);
                if(flag){
                    driver.switchTo().alert().accept();
                    Thread.sleep(500);
                    driver.findElement(By.id("CompanyFinishOrder_btnAddAddress")).click();
                    isClearData(driver);
                    addAddress(driver,"张三",31,0,0,"","","","",false);
                    isClearData(driver);
                    addAddress(driver,"张三",1,17,0,"","","","",false);
                    isClearData(driver);
                    addAddress(driver,"张三",1,6,0,"1133号","","","",false);
                    isClearData(driver);
                    addAddress(driver,"张三",1,5,0,"东大名路888弄","","", PhoneUtil.getTelephone(),false);
                    isClearData(driver);
                    addAddress(driver,"李三",1,6,0,"国康路47号","","", PhoneUtil.getTelephone(),true);

                    driver.findElement(By.className("chooseAddressHeader")).click();
                    Thread.sleep(500);
                    driver.findElement(By.id("CompanyFinishOrder_list_CompanyCommon_Consignee_ConsigneeList___repeaterRegionsSelectm_ctl01_btnSetDefault")).click();
                }
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //添加收货地址
    private void addAddress(WebDriver driver,String name,int provinceNum,int cityNum,int areaNum,String address,String zipCode,String telPhone,String cellPhone,boolean isClick){
        try{
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_txtShipTo")).sendKeys(name);
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"province_top\"]/span[2]")).click();

            List<WebElement> list = getNavList(driver,"province_select","li",0);
            String s = list.get(provinceNum).findElement(By.tagName("a")).getText();
            Thread.sleep(500);
            list.get(provinceNum).findElement(By.tagName("a")).click();
            if(s.equals("[清空]")){
                driver.findElement(By.id("CompanyFinishOrder_btnAddAddress")).click();
                return;
            }
            Thread.sleep(500);
            list = getNavList(driver,"city_select","li",0);
            s = list.get(provinceNum).findElement(By.tagName("a")).getText();
            list.get(cityNum).findElement(By.tagName("a")).click();
            if(s.equals("[清空]")){
                driver.findElement(By.id("CompanyFinishOrder_btnAddAddress")).click();
                return;
            }
            Thread.sleep(500);
            list = getNavList(driver,"area_select","li",0);
            if(list.size() == 0){
                driver.findElement(By.id("CompanyFinishOrder_btnAddAddress")).click();
                return;
            }
            list.get(areaNum).findElement(By.tagName("a")).click();
            Thread.sleep(500);

            driver.findElement(By.id("CompanyFinishOrder_txtAddress")).sendKeys(address);
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_txtZipcode")).sendKeys(zipCode);
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_txtTelPhone")).sendKeys(telPhone);
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_txtCellPhone")).sendKeys(cellPhone);
            Thread.sleep(500);
            if(isClick){
                driver.findElement(By.id("chkIsDefault")).click();
            }
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_btnAddAddress")).click();
            Thread.sleep(500);
            isClearData(driver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //判断是否要清空数据
    private void isClearData(WebDriver driver){
        boolean flag = isAlertPresent(driver);
        if(flag){
            driver.switchTo().alert().accept();
        }
        String s = driver.findElement(By.className("chooseAddressHeader")).getText();
        if(s.equals("请选择收货地址")){
            driver.findElement(By.className("qingkong")).click();
        }
    }

    //endregion

    //region 编辑地址

    @Test
    public void updateAddress(WebDriver driver){
        try{
            driver.findElement(By.className("chooseAddressHeader")).click();
            Thread.sleep(500);
            addressData(driver,"CompanyFinishOrder_list_CompanyCommon_Consignee_ConsigneeList___repeaterRegionsSelectm_ctl01_btnEdit",null,null,null,null,null,null,null,PhoneUtil.getTelephone(),true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addressData(WebDriver driver,String updatePath,String name,Integer provinceNum,Integer cityNum,Integer areaNum,String address,String zipCode,String telPhone,String cellPhone,boolean isClick){
        try{
            driver.findElement(By.id(updatePath)).click();
            Thread.sleep(500);
            if(name != null && name.length() != 0){
                driver.findElement(By.id("CompanyFinishOrder_txtShipTo")).sendKeys(name);
            }
            Thread.sleep(500);
            if(provinceNum != null){
                List<WebElement> list = getNavList(driver,"province_select","li",0);
                list.get(provinceNum).findElement(By.tagName("a")).click();
            }
            Thread.sleep(500);
            if(cityNum != null){
                List<WebElement> list = getNavList(driver,"city_select","li",0);
                list.get(cityNum).findElement(By.tagName("a")).click();
            }
            Thread.sleep(500);
            if(areaNum != null){
                List<WebElement> list = getNavList(driver,"area_select","li",0);
                list.get(areaNum).findElement(By.tagName("a")).click();
            }
            Thread.sleep(500);
            if(address != null && address.length() != 0){
                driver.findElement(By.id("CompanyFinishOrder_txtAddress")).clear();
                driver.findElement(By.id("CompanyFinishOrder_txtAddress")).sendKeys(address);
            }
            Thread.sleep(500);
            if(zipCode != null && zipCode.length() != 0){
                driver.findElement(By.id("CompanyFinishOrder_txtZipcode")).clear();
                driver.findElement(By.id("CompanyFinishOrder_txtZipcode")).sendKeys(zipCode);
            }
            Thread.sleep(500);
            if(telPhone != null && telPhone.length() != 0){
                driver.findElement(By.id("CompanyFinishOrder_txtTelPhone")).clear();
                driver.findElement(By.id("CompanyFinishOrder_txtTelPhone")).sendKeys(telPhone);
            }
            Thread.sleep(500);
            if(cellPhone != null && cellPhone.length() != 0){
                driver.findElement(By.id("CompanyFinishOrder_txtCellPhone")).clear();
                driver.findElement(By.id("CompanyFinishOrder_txtCellPhone")).sendKeys(cellPhone);
            }
            Thread.sleep(500);
            if(isClick){
                driver.findElement(By.id("chkIsDefault")).click();
                Thread.sleep(500);
            }
            driver.findElement(By.id("CompanyFinishOrder_btnAddAddress")).click();
            Thread.sleep(500);
            boolean flag = isAlertPresent(driver);
            if(flag){
                driver.switchTo().alert().accept();
            }
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

    //region 删除地址

    @Test
    public void deleteAddress(WebDriver driver){
        try{
            boolean flag = isExistDelButton(driver);
            if(!flag){
                return;
            }
            Thread.sleep(500);
            driver.findElement(By.className("chooseAddressHeader")).click();
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_list_CompanyCommon_Consignee_ConsigneeList___repeaterRegionsSelectm_ctl00_btnDelete")).click();
            flag = isAlertPresent(driver);
            if(flag){
                Thread.sleep(500);
                driver.switchTo().alert().accept();
            }
            Thread.sleep(500);
            driver.findElement(By.className("imgCloseLogin")).click();
            flag = isAlertPresent(driver);
            if(flag){
                addAddress(driver,"李三",1,6,0,"国康路47号","","", PhoneUtil.getTelephone(),true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //判断是否有删除按钮
    private boolean isExistDelButton(WebDriver driver){
        try{
            driver.findElement(By.id("CompanyFinishOrder_list_CompanyCommon_Consignee_ConsigneeList___repeaterRegionsSelectm_ctl00_btnDelete"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //endregion

    //endregion

    //region 导航栏

    @Test
    public void navMenu(WebDriver driver){
        try{
            List<WebElement> list = getNavList(driver,"h_chooselist","a",1);
            int num = list.size();
            for (int i = 0; i < num; i++) {
                list = getNavList(driver,"h_chooselist","a",1);
                Thread.sleep(500);
                list.get(i).click();
                Thread.sleep(500);
            }

            list = getNavList(driver,"//*[@id=\"aspnetForm\"]/div[4]/div[2]/div/div[1]","a",3);
            num = list.size();
            for (int i = 0; i < num; i++) {
                list = getNavList(driver,"//*[@id=\"aspnetForm\"]/div[4]/div[2]/div/div[1]","a",3);
                Thread.sleep(500);
                list.get(i).click();
                Thread.sleep(500);
            }
            driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[2]/div/h2[1]/a")).click();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

    //region 商品搜索

    @Test
    public void searchProduct(WebDriver driver){
        try{
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___txtKeywords")).sendKeys("套装");
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___btnSearch")).click();
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___txtStartPrice")).sendKeys("100");
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___btnSearch")).click();
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___txtEndPrice")).sendKeys("120");
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___btnSearch")).click();
            Thread.sleep(500);
            List<WebElement> list = getNavList(driver,"CompanyFinishOrder_search_Common_CutdownSearch___ckbListproductSearchType","input",0);
            list.get(0).click();
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___btnSearch")).click();
            Thread.sleep(500);
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___txtKeywords")).clear();
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___txtStartPrice")).clear();
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___txtEndPrice")).clear();
            list = getNavList(driver,"CompanyFinishOrder_search_Common_CutdownSearch___ckbListproductSearchType","input",0);
            list.get(0).click();
            driver.findElement(By.id("CompanyFinishOrder_search_Common_CutdownSearch___btnSearch")).click();
            Thread.sleep(500);
            list = getNavList(driver,"//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[4]/div[2]/ul","a",3);
            for (int i = 0; i < list.size(); i++) {
                list = getNavList(driver,"//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[4]/div[2]/ul","a",3);
                Thread.sleep(500);
                list.get(i).click();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

    //region 商品购买

    @Test
    public void purchaseGoods(WebDriver driver,String userName) {
        try {
            Thread.sleep(500);
            List<WebElement> productList = getNavList(driver, "//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[5]/ul", "li", 3);
            productList.get(0).click();
            Thread.sleep(500);
            List<String> url = getUrl(driver);
            driver.switchTo().window(url.get(1));

            //查看图片
            Actions mouse = new Actions(driver);
            Thread.sleep(500);
            mouse.moveToElement(driver.findElement(By.className("mousetrap"))).perform();
            Thread.sleep(500);
            mouse.moveToElement(driver.findElement(By.className("qy_bdbg"))).perform();

            //数量选择
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".shopcart-minus.shopcart1")).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(".shopcart-add.shopcart1")).click();
            Thread.sleep(500);
            driver.findElement(By.id("buyAmount")).clear();
            driver.findElement(By.id("buyAmount")).sendKeys("3");

            //商品详情评价
            List<WebElement> navList = getNavList(driver,"product_nav_wrap","a",1);
            for (int i = 0; i < navList.size(); i++) {
                Thread.sleep(1000);
                navList.get(i).click();
            }
            Thread.sleep(500);

            //添加购物车后继续购物
            addShoppingCart(driver,0);
            List<WebElement> smallProductList = getNavList(driver,"products_16","li",0);
            smallProductList.get(0).click();
            Thread.sleep(500);

            //关闭第一次打开的商品页面
            url = getUrl(driver);
            driver.switchTo().window(url.get(1));
            driver.close();
            Thread.sleep(500);

            //切换商品页面
            url = getUrl(driver);
            driver.switchTo().window(url.get(1));
            //添加购物车并跳转到购物车页面
            addShoppingCart(driver,1);
            //购物车
            shoppingCart(driver);
            //购买
            Buy(driver,0);
            //付款
            payment(driver,userName);
            url = getUrl(driver);
            driver.switchTo().window(url.get(0));

            //创建不付款订单
            doNotPayment(driver,5);

            doNotPayment(driver,2);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //添加购物车
    private void addShoppingCart(WebDriver driver,int num){
        try{
            Thread.sleep(500);
            driver.findElement(By.id("addcartButton")).click();
            Thread.sleep(500);
            boolean flag = isExistSuccessToShoppingCartBox(driver);
            if(flag && num == 0){
                driver.findElement(By.className("btn-continue")).click();
            }else if(flag && num != 0){
                driver.findElement(By.className("btn-viewcart")).click();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //购物车结算
    private void shoppingCart(WebDriver driver){
        try{
            //取消全选
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"CompanyShoppingCart_CompanyCommon_ShoppingCart_ProductList___pnlShopProductCart\"]/div[1]/div[1]/div/ins")).click();

            //购物车选择操作
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"CompanyShoppingCart_CompanyCommon_ShoppingCart_ProductList___rptSupplier_ctl00_dataListShoppingCart_ctl00_divCheck\"]/div/ins")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"CompanyShoppingCart_CompanyCommon_ShoppingCart_ProductList___rptSupplier_ctl00_dataListShoppingCart_ctl00_divAmount\"]/span[1]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"CompanyShoppingCart_CompanyCommon_ShoppingCart_ProductList___rptSupplier_ctl00_dataListShoppingCart_ctl00_divAmount\"]/span[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"CompanyShoppingCart_CompanyCommon_ShoppingCart_ProductList___rptSupplier_ctl00_dataListShoppingCart_ctl00_txtBuyNum\"]")).clear();
            driver.findElement(By.xpath("//*[@id=\"CompanyShoppingCart_CompanyCommon_ShoppingCart_ProductList___rptSupplier_ctl00_dataListShoppingCart_ctl00_txtBuyNum\"]")).sendKeys("1");
            Thread.sleep(500);

            //删除商品
            List<WebElement> aList = getNavList(driver,"cart_commodit_settle","a",1);
            aList.get(0).click();
            Thread.sleep(500);
            boolean flag = isAlertPresent(driver);
            if(flag){
                driver.switchTo().alert().accept();
                Thread.sleep(500);
            }

            driver.findElement(By.xpath("//*[@id=\"CompanyShoppingCart_CompanyCommon_ShoppingCart_ProductList___pnlShopProductCart\"]/div[1]/div[1]/div/ins")).click();
            Thread.sleep(500);
            driver.findElement(By.id("CompanyShoppingCart_btnCheckout")).click();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //购买页面操作
    private void Buy(WebDriver driver,int num){
        try{
            Thread.sleep(500);
            List<WebElement> addressList = getNavList(driver,"address_tab","div",1);
            if(addressList.size() != 0){
                addressList.get(1).click();
            }
            driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[3]/div[2]/div/div[5]/div[2]/div[2]/span/span/span")).click();
            boolean flag = isExistdeliveryTime(driver);
            if(flag){
                List<WebElement> timeList = getNavList(driver,".combo-panel.panel-body.panel-body-noheader","div",2);
                if(timeList.size() > num){
                    timeList.get(num).click();
                }else{
                    timeList.get(0).click();
                }
            }
            driver.findElement(By.id("CompanySubmmitOrder_txtMessage")).sendKeys("测试");
            driver.findElement(By.id("CompanySubmmitOrder_btnCreateOrder")).click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //付款页面
    public void payment(WebDriver driver,String userName){
        try{
            Thread.sleep(500);
            driver.findElement(By.id("aOrderPay")).click();
            Thread.sleep(500);
            boolean flag = isExistLoginForPay(driver);
            if(flag){
                driver.findElement(By.id("btnyzm")).click();
                Thread.sleep(500);
                flag = isExistSuccessOrErrorBox(driver);
                if(flag){
                    Thread.sleep(500);
                    String s = driver.findElement(By.className("zeromodal-title1")).getText();
                    Thread.sleep(500);
                    driver.findElement(By.className("zeromodal-close")).click();
                    while(!s.equals("发送成功!")){
                        Thread.sleep(60000);
                        driver.findElement(By.id("btnyzm")).click();
                        Thread.sleep(500);
                        s = driver.findElement(By.className("zeromodal-title1")).getText();
                        Thread.sleep(500);
                        driver.findElement(By.className("zeromodal-close")).click();
                    }
                    Thread.sleep(500);
                    JdbcUtil j = new JdbcUtil();
                    driver.findElement(By.id("textfieldpassword")).sendKeys(j.querySmsCode(j.queryCellPhone(userName)));
                    Thread.sleep(500);
                    driver.findElement(By.id("btnLoginAndBuy")).click();
                    Thread.sleep(500);
                    flag = isExistSuccessOrErrorBox(driver);
                    if(flag){
                        s = driver.findElement(By.className("zeromodal-title1")).getText();
                        Thread.sleep(500);
                        driver.findElement(By.className("zeromodal-close")).click();
                        if(s.equals("参数错误！")){
                            driver.findElement(By.className("dialog_title_r")).click();
                            Thread.sleep(500);
                        }
                    }
                }
            }
            driver.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取窗体中打开的网站
    private List<String> getUrl(WebDriver driver) {
        Set<String> winHandles = driver.getWindowHandles();
        List<String> it = new ArrayList<>(winHandles);
        return it;
    }

    //创建不付款订单
    private void doNotPayment(WebDriver driver,int num){
        try{
            Thread.sleep(500);
            List<WebElement> productList = getNavList(driver, "//*[@id=\"aspnetForm\"]/div[4]/div[3]/div[5]/ul", "li", 3);
            while (productList.size() < num){
                num = num - 1;
            }
            productList.get(num).click();
            Thread.sleep(500);
            List<String> url = getUrl(driver);
            driver.switchTo().window(url.get(1));
            url = getUrl(driver);
            driver.switchTo().window(url.get(1));
            driver.findElement(By.id("buyButton")).click();
            Thread.sleep(500);
            Buy(driver,1);
            Thread.sleep(500);
            driver.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //判断是否有支付验证窗体
    private boolean isExistLoginForPay(WebDriver driver){
        try{
            driver.findElement(By.id("loginForBuy"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //判断是否有成功或失败窗体
    private boolean isExistSuccessOrErrorBox(WebDriver driver){
        try{
            driver.findElement(By.className("zeromodal-container"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //判断是否弹出成功添加购物车窗体
    private boolean isExistSuccessToShoppingCartBox(WebDriver driver){
        try {
            driver.findElement(By.id("divshow"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //判断是否有送货时间选择盒子
    private boolean isExistdeliveryTime(WebDriver driver){
        try{
            driver.findElement(By.cssSelector("combo-panel panel-body panel-body-noheader"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //endregion

    //验证是否有弹窗
    private static boolean isAlertPresent(WebDriver driver){
        try {
            driver.switchTo().alert();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}