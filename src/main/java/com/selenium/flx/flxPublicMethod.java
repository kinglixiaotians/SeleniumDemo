package com.selenium.flx;

import com.selenium.utils.JdbcUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.selenium.fuyou.fuYouMethod.isExistBoxOrExistButton;

public class flxPublicMethod {

    //返回当前时间  格式为：yyyyMMddHHmmss（例：20190401160321）
    public static String nowDate() {
        //获取当前时间并进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }


    /**
     * 清除框内数据并重新赋值
     * @param driver
     * @param byState id , xpath , className , cssSelector
     * @param url
     * @param value
     */
    public static void updateInput(WebDriver driver, String byState, String url, String value) {
        switch (byState){
            case "id":
                driver.findElement(By.id(url)).clear();
                driver.findElement(By.id(url)).sendKeys(value);
                break;
            case "xpath":
                driver.findElement(By.xpath(url)).clear();
                driver.findElement(By.xpath(url)).sendKeys(value);
                break;
            case "className":
                driver.findElement(By.className(url)).clear();
                driver.findElement(By.className(url)).sendKeys(value);
                break;
            case "cssSelector":
                driver.findElement(By.cssSelector(url)).clear();
                driver.findElement(By.cssSelector(url)).sendKeys(value);
                break;
        }

    }

    //根据企业号返回验证码
    public static String getCord(String custom) {
        JdbcUtil j = new JdbcUtil();
        return j.querySmsCode(j.queryCellPhone(custom));
    }

    //出现此元素就点击若不出现则一直等（每一秒判断一次）
    public static void waitClick(WebDriver driver,String url,int num) throws InterruptedException {
        while (!isExistBoxOrExistButton(driver, url, num)) {
            Thread.sleep(1000);
        }
        switch (num){
            case 0 :
                driver.findElement(By.id(url)).click();
                break;
            case 1 :
                driver.findElement(By.className(url)).click();
                break;
            case 2 :
                driver.findElement(By.cssSelector(url)).click();
                break;
            case 3 :
                driver.findElement(By.xpath(url)).click();
                break;
        }
    }

    //失败原因图片截取并加入日志
    public static void taskScreenShot(WebDriver driver){
        long date = System.currentTimeMillis();
        String path = String.valueOf(date);
        String cusPath = System.getProperty("user.dir");
        path = path+".png";
        String screenPath = cusPath+"/"+path;
        System.out.println(screenPath);
        //实现截图
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,new File(screenPath));
        }catch (IOException e){
            e.printStackTrace();
        }
        Reporter.log("< a href= " + screenPath + " target=_blank>失败原因图片</ a>", true);
//        Reporter.log("<a href=\"" + screenPath + "\">失败原因图片</a>", true);
//        Reporter.log("<img src=" + screenPath +">", true);
//        Reporter.log("失败图片地址为"+screenPath);
//        Reporter.log("<img src=\"../../" + screenPath + "\"/>");
//        Reporter.log("<a src=\"../../" + screenPath + "\"/>");
    }
}
