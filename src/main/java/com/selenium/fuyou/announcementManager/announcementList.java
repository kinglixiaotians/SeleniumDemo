package com.selenium.fuyou.announcementManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class announcementList {

    //region 发布公告

    @Test
    public void announcement(WebDriver driver){
        try{
            Thread.sleep(500);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/label/a")).click();
            Thread.sleep(1000);
            searchEmp(driver,new String[]{"随便","不知道","17607136666",""});
            Thread.sleep(500);
            checkEmp(driver,new int[]{2,5,7},new int[]{0,0,0});
            Thread.sleep(500);
            release(driver,"不知道要发什么","就随便试试");
            Thread.sleep(500);
            checkEmp(driver,new int[]{1,2,3},new int[0]);
            Thread.sleep(500);
            release(driver,"随便发点","文本不能为空11111。。。");
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //搜索
    public void searchEmp(WebDriver driver,String [] search){
        try{

            for (int i = 0;i < search.length;i++){
                driver.findElement(By.id("txtkey")).sendKeys(search[i]);
                Thread.sleep(500);
                driver.findElement(By.className("iconSearch")).click();
                Thread.sleep(1000);
                boolean flag = isExistErrorBox(driver);
                if(flag){
                    driver.findElement(By.className("zeromodal-close")).click();
                }
                driver.findElement(By.id("txtkey")).clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //选择发送成员
    public void checkEmp(WebDriver driver,int [] check,int [] uncheck){
        try{
            if(check.length != 0){
                WebElement element = driver.findElement(By.id("fbgg_choice"));
                List<WebElement> checkList = element.findElements(By.tagName("li"));
                for (int i = 0; i < check.length; i++) {
                    if(check[i] < checkList.size()) {
                        Thread.sleep(500);
                        checkList.get(check[i]).click();
                    }
                }
                if(uncheck.length != 0){
                    for (int i = 0; i < uncheck.length; i++) {
                        element = driver.findElement(By.id("fbgg_con_bg"));
                        checkList = element.findElements(By.tagName("span"));
                        if(uncheck[i] < checkList.size()){
                            Thread.sleep(500);
                            checkList.get(uncheck[i]).findElement(By.tagName("i")).click();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //发布
    public void release(WebDriver driver,String title,String content){
        try{
            driver.findElement(By.id("AfficheTitle")).sendKeys(title);
            Thread.sleep(500);
            driver.findElement(By.id("AfficheContent")).sendKeys(content);
            Thread.sleep(500);
            driver.findElement(By.className("jnk_send")).click();
            Thread.sleep(500);
            boolean flag = isExistFailTitleBox(driver);
            if(flag){
                releaseFail(driver);
            }
            flag = isExistSuccessBox(driver);
            if(flag){
                releaseSuccess(driver);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //发布成功后操作
    public void releaseSuccess(WebDriver driver){
        try{
            WebElement element = driver.findElement(By.id("fbgg_con_bg"));
            List<WebElement> checkList = element.findElements(By.tagName("span"));
            if(checkList.size() == 0){
                driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-default")) .click();
                return;
            }
            driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-primary.anblock")).click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //发布失败后操作
    public void releaseFail(WebDriver driver){
        try{
            Thread.sleep(500);
            driver.findElement(By.className("zeromodal-close")).click();
            Thread.sleep(500);
            driver.findElement(By.id("AfficheTitle")).clear();
            driver.findElement(By.id("AfficheContent")).clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //判断是否有弹窗出现
    public boolean isExistErrorBox(WebDriver driver){
        try{
            driver.findElement(By.className("zeromodal-container"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //判断是否有成功窗体出现
    public boolean isExistSuccessBox(WebDriver driver){
        try{
            driver.findElement(By.cssSelector(".zeromodal-body.zeromodal-overflow-y"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //发布失败提示是否是title
    public boolean isExistFailTitleBox(WebDriver driver){
        try{
            driver.findElement(By.className("zeromodal-title1"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //endregion

    //region 删除公告

    @Test
    public void deleteAnnouncement(WebDriver driver){
        try{
            boolean flag = isExistDelButton(driver);
            if(flag){
                Thread.sleep(500);
                driver.findElement(By.xpath("/html/body/div[4]/div[3]/table/tbody/tr[2]/td[7]/a")).click();
                flag = isExistDelBox(driver);
                if(flag){
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector(".zeromodal-btn.zeromodal-btn-primary.anblock")).click();
                }else{
                    return;
                }
            }else{
                return;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断是否有删除按钮
    public boolean isExistDelButton(WebDriver driver){
        try{
            driver.findElement(By.xpath("/html/body/div[4]/div[3]/table/tbody/tr[2]/td[7]/a"));
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    //判断是否有删除弹窗
    public boolean isExistDelBox(WebDriver driver){
        try{
            driver.findElement(By.className("zeromodal-container"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //endregion
}
