package com.selenium.fuyou.enterpriseProcurement;

import com.selenium.utils.PhoneUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

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
    public void addAddress(WebDriver driver,String name,int provinceNum,int cityNum,int areaNum,String address,String zipCode,String telPhone,String cellPhone,boolean isClick){
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
    public void isClearData(WebDriver driver){
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

    public void addressData(WebDriver driver,String updatePath,String name,Integer provinceNum,Integer cityNum,Integer areaNum,String address,String zipCode,String telPhone,String cellPhone,boolean isClick){
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
            Thread.sleep(500);
            driver.findElement(By.id("chooseAddressHeader")).click();
            driver.findElement(By.id("CompanyFinishOrder_list_CompanyCommon_Consignee_ConsigneeList___repeaterRegionsSelectm_ctl00_btnDelete")).clear();
            boolean flag = isAlertPresent(driver);
            if(flag){
                driver.switchTo().alert().accept();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

    //验证是否有弹窗
    public static boolean isAlertPresent(WebDriver driver){
        try {
            driver.switchTo().alert();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //endregion
}
