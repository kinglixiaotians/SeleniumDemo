package com.selenium.flx;

import com.selenium.utils.JdbcUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}
