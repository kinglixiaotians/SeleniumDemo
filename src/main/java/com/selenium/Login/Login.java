package com.selenium.Login;

import com.selenium.EmployeeManager.DepartmentList;
import com.selenium.EmployeeManager.EmployeeList;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Login {
    public static WebDriver driver;

    public static void main(String [] args){

        System.setProperty("webdriver.chrome.driver", "E:\\SeleniumDemo\\src\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost/");
        driver.manage().window().maximize();
        driver.findElement(By.className("notice_close")).click();
        try {
            Thread.sleep(500);
            driver.findElement(By.className("layui-layer-setwin")).click();
            driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/label[1]/a")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("username")).sendKeys("01510183");
            driver.findElement(By.id("password")).sendKeys("123456");
            String result = validateCoding();
            driver.findElement(By.id("verifyCode")).sendKeys(result);
            Thread.sleep(1000);
            driver.findElement(By.className("login_Btn")).click();
            while(isAlertPersent(driver)){
                driver.findElement(By.id("verifyCode")).clear();
                Thread.sleep(500);
                result = validateCoding();
                driver.findElement(By.id("verifyCode")).sendKeys(result);
                Thread.sleep(1000);
                driver.findElement(By.className("login_Btn")).click();
            }

            ///企业登陆管理
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"dowebok\"]/div[1]/div/a[2]")).click();
            Actions mouse = new Actions(driver);
            Thread.sleep(1000);

            //员工管理
            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/a"))).perform();
            DepartmentList.add(driver);
            mouse.moveToElement(driver.findElement(By.xpath("//*[@id=\"fbgg_menu\"]/li[3]/a"))).perform();
            EmployeeList.add(driver);

        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
        }
    }

    public static boolean isAlertPersent(WebDriver driver){
        try {
            driver.switchTo().alert().accept();
            return true;
        }catch (NoAlertPresentException e){
            return false;
        }
    }

    public static String validateCoding(){
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
            result = LoginValidate.orcImage(screenshotLocation.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
}
