package com.selenium.fuyou.emploeeManager;

import com.selenium.fuyou.baseDB.employee;
import com.selenium.utils.PropertiesConfig;
import com.selenium.utils.ResourcesUrlUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class employeeList {

    //region 新增员工

    @Test
    public void addEmp(WebDriver driver) {
        try {
            Thread.sleep(500);
            //分页测试
            paging(driver);

            ArrayList<employee> staff = new ArrayList<>();
            staff.add(new employee("李四", 1, "382", "15884574764", "2017-01-04"));
            staff.add(new employee("你猜", 2, "93", "13384750276", ""));
            staff.add(new employee("张三", 2, "3492", "13134790377", "2018-10-29"));
            //循环插入员工
            driver.findElement(By.id("bgcreate")).click();
            for (employee e : staff) {
                //输入员工姓名
                driver.findElement(By.id("RealName")).sendKeys(e.getRealName());
                Thread.sleep(500);
                //员工部门
                WebElement ele = driver.findElement(By.id("CompanyEmployeesList_ddlOrganization"));
                Select downList = new Select(ele);
                downList.selectByIndex(e.getDepartmentID());
                Thread.sleep(500);
                //员工工号
                driver.findElement(By.id("ExternalUserId")).sendKeys(e.getUserID());
                Thread.sleep(500);
                //员工电话
                driver.findElement(By.id("UserName")).sendKeys(e.getPhoneNum());
                Thread.sleep(500);

                //入职日期
                ((JavascriptExecutor) driver).executeScript("document.getElementById('CompanyEmployeesList_addJoinDate').removeAttribute('readonly')");
                driver.findElement(By.id("CompanyEmployeesList_addJoinDate")).sendKeys(e.getDate());
                driver.findElement(By.id("CompanyEmployeesList_addJoinDate")).click();
                //保存
                driver.findElement(By.xpath("//*[@id=\"createcontent\"]/div/div[9]/label/input")).click();
                Thread.sleep(500);

                //新增失败后操作
                String s = driver.findElement(By.className("zeromodal-title1")).getText();
                Thread.sleep(500);
                if(s.equals("员工工号已存在") || s.equals("已经存在相同的账号") || s.equals("手机号有误")){
                    addEmpErrorOperation(driver,downList);
                    continue;
                }
                driver.findElement(By.className("zeromodal-close")).click();
                Thread.sleep(500);
                driver.findElement(By.id("bgcreate")).click();
                Thread.sleep(500);
            }
            boolean flag = isExistAddBox(driver);
            if(flag){
                driver.findElement(By.xpath("//*[@id=\"xubox_layer1\"]/div[1]/a")).click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //员工添加错误操作
    public void addEmpErrorOperation(WebDriver driver,Select downList){
        driver.findElement(By.className("zeromodal-close")).click();
        driver.findElement(By.id("RealName")).clear();
        downList.selectByIndex(0);
        driver.findElement(By.id("ExternalUserId")).clear();
        driver.findElement(By.id("UserName")).clear();
        driver.findElement(By.id("CompanyEmployeesList_addJoinDate")).clear();
    }

    //判断是否有新增弹窗
    public boolean isExistAddBox(WebDriver driver){
        try {
            driver.findElement(By.cssSelector(".xubox_layer.xubox_layer_0"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //region 分页
    public void paging(WebDriver driver){
        try{
            //分页
            boolean pageNextFlag = isExistPageNext(driver);
            if (pageNextFlag){
                driver.findElement(By.className("page-next")).click();
            }
            Thread.sleep(500);
            boolean pagePrevFlag = isExistPagePrev(driver);
            if(pagePrevFlag){
                driver.findElement(By.className("page-prev")).click();
            }
            Thread.sleep(500);
            if(pageNextFlag || pagePrevFlag){
                pageJump(driver);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //判断是否有下一页按钮
    public boolean isExistPageNext(WebDriver driver){
        try {
            driver.findElement(By.className("page-next"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //判断是否有上一页按钮
    public boolean isExistPagePrev(WebDriver driver){
        try {
            driver.findElement(By.className("page-prev"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //分页跳转
    public void pageJump(WebDriver driver){
        try{
            driver.findElement(By.id("txtGoto")).clear();
            driver.findElement(By.id("txtGoto")).sendKeys("12");
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[7]/div/span/input[2]")).click();
            Thread.sleep(500);
            driver.findElement(By.id("txtGoto")).clear();
            Thread.sleep(500);
            driver.findElement(By.id("txtGoto")).sendKeys("1");
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[4]/div[7]/div/span/input[2]")).click();
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

    //endregion

    //region 修改员工信息

    @Test
    public void updateEmp(WebDriver driver){
        try {
            Thread.sleep(500);
            boolean flag = isExistEditButton(driver);
            if(flag){
                //点击编辑
                driver.findElement(By.xpath("//*[@id=\"qyzx_plist\"]/table/tbody/tr[2]/td[6]/a[1]")).click();
                Thread.sleep(500);
                //清空数据
                clearEmpData(driver);
                //修改
                String s = updateEmpData(driver,new employee("笑岁",2,"1115","","2019-01-28"));
                if(s.equals("员工工号已存在") || s.equals(null)){
                    clearEmpData(driver);
                }else{
                    driver.findElement(By.id("bgcreate")).click();
                }
                updateEmpData(driver,new employee("dnf",2,"1115","","2019-03-23"));
                Thread.sleep(500);
                //关闭多余编辑窗体
                flag = isExistUpdateBox(driver);
                if(flag){
                    driver.findElement(By.cssSelector(".xubox_close.xulayer_png32.xubox_close0_0")).click();
                }
                Thread.sleep(500);
                //部门分类跳转
                WebElement ele = driver.findElement(By.cssSelector(".qyzx_bm.bm_menu"));
                List<WebElement> eleList = ele.findElements(By.tagName("a"));
                eleList.get(3).click();
                Thread.sleep(1000);
            }
            else{
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //清空修改员工数据操作
    public void clearEmpData(WebDriver driver){
        try {
            //清空员工名字
            driver.findElement(By.id("updateRealName")).clear();
            //初始化部门
            WebElement element = driver.findElement(By.id("CompanyEmployeesList_ddlUpdateOrganization"));
            Select downList = new Select(element);
            downList.selectByIndex(0);
            //清空员工工号
            driver.findElement(By.id("updateExternalUserId")).clear();
            //清空入职日期
            ((JavascriptExecutor) driver).executeScript("document.getElementById('CompanyEmployeesList_updateJoinDate').removeAttribute('readonly')");
            driver.findElement(By.id("CompanyEmployeesList_updateJoinDate")).clear();
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //修改员工数据
    public String updateEmpData(WebDriver driver,employee emp){
        try {
            //员工名字
            driver.findElement(By.id("updateRealName")).sendKeys(emp.getRealName());
            //选择部门
            WebElement element = driver.findElement(By.id("CompanyEmployeesList_ddlUpdateOrganization"));
            Select downList = new Select(element);
            downList.selectByIndex(emp.getDepartmentID());
            //员工工号
            driver.findElement(By.id("updateExternalUserId")).sendKeys(emp.getUserID());
            //入职日期
            driver.findElement(By.id("CompanyEmployeesList_updateJoinDate")).sendKeys(emp.getDate());
            //取消日历显示
            driver.findElement(By.id("CompanyEmployeesList_updateJoinDate")).click();
            Thread.sleep(500);
            //保存
            driver.findElement(By.id("btnupdateUser")).click();
            Thread.sleep(500);
            //获取显示成功或失败文本
            String result = driver.findElement(By.className("zeromodal-title1")).getText();
            Thread.sleep(500);
            //关闭成功或失败窗体
            driver.findElement(By.className("zeromodal-close")).click();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    //判断是否有编辑按钮
    public boolean isExistEditButton(WebDriver driver){
        try{
            driver.findElement(By.xpath("//*[@id=\"qyzx_plist\"]/table/tbody/tr[2]/td[6]/a[1]"));
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    //判断是否有员工编辑窗口
    public boolean isExistUpdateBox(WebDriver driver){
        try{
            driver.findElement(By.cssSelector(".xubox_main.xubox_main_0"));
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    //endregion

    //region 删除员工

    @Test
    public void deleteEmp(WebDriver driver){
        try{
            boolean flag = isExistDelButtopn(driver);
            if(flag){
                Thread.sleep(500);
                driver.findElement(By.xpath("//*[@id=\"qyzx_plist\"]/table/tbody/tr[2]/td[6]/a[2]")).click();
                Thread.sleep(500);
                driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-primary.anblock")).click();
                Thread.sleep(500);

                //部门分类全部
                WebElement ele = driver.findElement(By.cssSelector(".qyzx_bm.bm_menu"));
                List<WebElement> eleList = ele.findElements(By.tagName("a"));
                eleList.get(0).click();
                Thread.sleep(1000);
            }else{
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //判断是否有删除按钮
    public boolean isExistDelButtopn(WebDriver driver){
        try{
            driver.findElement(By.xpath("//*[@id=\"qyzx_plist\"]/table/tbody/tr[2]/td[6]/a[2]"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //endregion

    //region 搜索员工

    @Test
    public void searchEmp(WebDriver driver){
        try{
            Thread.sleep(500);
            driver.findElement(By.className("qyzx_key_input")).sendKeys("酷冷");
            driver.findElement(By.className("yqzx_key_search")).click();
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

    //region 批量导入员工

    @Test
    public void batchImportEmp(WebDriver driver){
        try{
            Thread.sleep(500);
            driver.findElement(By.className("yqzx_pl")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[7]/a")).click();
            Thread.sleep(1000);
            batchImprotEmpData(driver,null,false);//空模板错误示例
            Thread.sleep(500);
            batchImprotEmpData(driver, PropertiesConfig.getInstance().getProperty("fuYou.employeeImportUrl3"),false);//格式错误示例
            Thread.sleep(500);
            batchImprotEmpData(driver,PropertiesConfig.getInstance().getProperty("fuYou.employeeImportUrl2"),false);//模板数据未通过示例
            Thread.sleep(500);
            batchImprotEmpData(driver,PropertiesConfig.getInstance().getProperty("fuYou.employeeImportUrl1"),true);//模板数据为空示例
            Thread.sleep(500);
            batchImprotEmpData(driver,PropertiesConfig.getInstance().getProperty("fuYou.employeeImportUrl"),true);//成功示例，需模板内数据可以通过
            Thread.sleep(500);
            //分类跳转操作
            WebElement ele = driver.findElement(By.cssSelector(".qyzx_bm.bm_menu"));
            List<WebElement> eleList = ele.findElements(By.tagName("a"));
            eleList.get(2).click();
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //上传文件选择
    public void batchImprotEmpData(WebDriver driver,String fileName,boolean isClick){
        try{
            if (fileName == null){
                driver.findElement(By.id("btnImport")).click();
                Thread.sleep(500);
            }else{
                if(isClick){
                    String path = ResourcesUrlUtil.pathUrl(fileName);
                    driver.findElement(By.id("select_btn_1")).sendKeys(path);
                    Thread.sleep(500);
                    if(fileName.equals("fuyou\\员工导入模板.xls")){
                        boolean flag = isExistErrorOrSuccessBox(driver);
                        if(flag){
                            driver.findElement(By.className("zeromodal-close")).click();
                            Thread.sleep(500);
                            Actions mouse = new Actions(driver);
                            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/a"))).perform();
                            driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/ul/li[1]/a")).click();
                            return;
                        }
                    }
                    driver.findElement(By.id("btnImport")).click();
                }else{
                    String path = ResourcesUrlUtil.pathUrl(fileName);
                    driver.findElement(By.id("select_btn_1")).sendKeys(path);
                }
            }
            boxClose(driver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void boxClose(WebDriver driver){
        try{
            boolean flag = isAlertPersent(driver);
            if(flag){
                driver.switchTo().alert().accept();
            }
            Thread.sleep(500);
            flag = isExistErrorOrSuccessBox(driver);
            if(flag){
                driver.findElement(By.className("zeromodal-close")).click();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isAlertPersent(WebDriver driver){
        try {
            driver.switchTo().alert();
            return true;
        }catch (NoAlertPresentException e){
            return false;
        }
    }

    public boolean isExistErrorOrSuccessBox(WebDriver driver){
        try {
            driver.findElement(By.className("zeromodal-container"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //endregion
}
