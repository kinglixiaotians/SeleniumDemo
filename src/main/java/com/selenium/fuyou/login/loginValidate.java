package com.selenium.fuyou.login;

import com.selenium.test.baidu.OcrTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class loginValidate {
    //验证是否有弹窗
    public static boolean isAlertPersent(WebDriver driver){
        try {
            driver.switchTo().alert().accept();
            return true;
        }catch (NoAlertPresentException e){
            return false;
        }
    }

    //读取验证码
    public static String validateCoding(WebDriver driver){
        String result = "";
        try {
            WebElement ele = driver.findElement(By.id("imgVerifyCode"));
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImage = ImageIO.read(screenshot);
            Point point = ele.getLocation();
            int width = ele.getSize().getWidth();
            int height = ele.getSize().getHeight();
            BufferedImage eleScreenshot = fullImage.getSubimage(point.x,point.y,width,height);
            ImageIO.write(eleScreenshot,"png",screenshot);
            File screenshotLocation = new File("E:\\2019\\validCoding.png");
            FileUtils.copyFile(screenshot, screenshotLocation);
            result = OcrTest.orcImage(screenshotLocation.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
}
