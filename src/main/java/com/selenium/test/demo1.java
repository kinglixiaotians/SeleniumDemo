package com.selenium.test;

import com.selenium.test.baidu.OcrTest;
import com.selenium.test.util.TestListenerScreenShot;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by 李啸天 on 2019/3/21.
 */
@Listeners({TestListenerScreenShot.class})
public class demo1  {

    private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";

    WebDriver driver;
    public static String instanFile(String fileUrl){
        String result = null;
        try {
            File imageFile = new File(fileUrl);
            Tesseract instance = new Tesseract();
            instance.setLanguage("eng");
            instance.setDatapath("E:\\2019\\Tess4J");
            //将验证码图片的内容识别为字符串
            result = instance.doOCR(imageFile);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void taskScreenShot(){
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

        Reporter.log("<a href=" + screenPath + ">失败原因图片</a>", true);
        //Reporter.log("<img src=" + screenPath +">", true);
        //Reporter.log("失败图片地址为"+screenPath);
        //Reporter.log("<img src=\"../../" + screenPath + "\"/>");
        //Reporter.log("<a src=\"../../" + screenPath + "\"/>");
    }


    public static void saveImage(WebDriver driver,String imageUrl){
        try {
            WebElement ele = driver.findElement(By.id("imgVerifyCode"));
            //读取图片id下面的图片
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            // Get entire page screenshot
            BufferedImage fullImg= ImageIO.read(screenshot);
            // Get the location of element on the page
            org.openqa.selenium.Point point= ele.getLocation();
            // Get width and height of the element
            int eleWidth= ele.getSize().getWidth();
            int eleHeight= ele.getSize().getHeight();
            // Crop the entire page screenshot to get only element screenshot
            BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
            //eleScreenshot = ImageHelper.convertImageToBinary(eleScreenshot);
            //放大五倍
            eleScreenshot = ImageHelper.getScaledInstance(eleScreenshot, eleWidth * 3, eleHeight * 2);
            ImageIO.write(eleScreenshot, "png", screenshot);
            // Copy the element screenshot to disk

            File screenshotLocation= new File(imageUrl);
            FileUtils.copyFile(screenshot, screenshotLocation);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean isAlertPersent(WebDriver driver){
        try {
            driver.switchTo().alert();
            return true;
        }catch (NoAlertPresentException e){return false;}
    }

    @Test
    public boolean runs(){
        //chrom插件路径
        System.setProperty("webdriver.chrome.driver", "E:\\2019\\driver\\chromedriver.exe");
        System.setProperty(ESCAPE_PROPERTY, "false");
        driver = new ChromeDriver();
        driver.get("https://www.you-fuli.com");
        driver.manage().window().maximize();
        driver.findElement(By.className("notice_close")).click();
        driver.findElement(By.className("openlogin")).click();
        try {
            Thread.sleep(1000);
            driver.findElement(By.id("username")).sendKeys("15000364728");
            driver.findElement(By.id("password")).sendKeys("lxt84737895gk");
            String imageUrl = "E:\\2019\\test.png";
            demo1.saveImage(driver,imageUrl);
            String result = OcrTest.orcImage(imageUrl);
            driver.findElement(By.id("verifyCode")).sendKeys(result);
            driver.findElement(By.className("login_Btn")).click();
            Thread.sleep(500);
            if(demo1.isAlertPersent(driver)){
                driver.switchTo().alert().accept();
                this.taskScreenShot();
                //tab页新开
                driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
                return false;
            }else{
                driver.findElement(By.id("J-subnav-wealth")).click();
            }
            //System.out.println(driver.switchTo().alert().getText());
           /* String title = driver.getTitle();
            System.out.println(title);*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Test(description = "福优网测试登录案例")
    public void testrun(){
        demo1 de = new demo1();
        boolean flag = de.runs();
       /* for(int i=0;i<10;i++){
            boolean flag = de.runs();
            if(flag){
                break;
            }
        }*/
    }
}
