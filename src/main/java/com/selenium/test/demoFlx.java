package com.selenium.test;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by 李啸天 on 2019/3/22.
 */
public class demoFlx {
    WebDriver driver ;
    public String windowsHandle;
    public String customNo;
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

    //@Test
    public boolean runs(){
        //chrom插件路径
        System.setProperty("webdriver.chrome.driver", "E:\\2019\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://192.168.2.100:28080/FlxServer/coframe/auth/login/login.jsp");
        driver.manage().window().maximize();
        try {
            Thread.sleep(1000);
            //登录
            windowsHandle = driver.getWindowHandle();
            driver.findElement(By.id("userId")).sendKeys("100003");
            driver.findElement(By.id("password")).sendKeys("000000");
            driver.findElement(By.className("log")).click();
            Thread.sleep(500);
            //客户管理
            driver.findElement(By.id("1061")).click();
            //客户开户档案
            driver.findElement(By.id("1062")).click();
            Thread.sleep(2000);
            this.saveCustom();
            Thread.sleep(1000);
            this.queryCustom();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    //@Test
    public void queryCustom(){
        driver.switchTo().frame("mainframe");
        driver.findElement(By.id("customNo$text")).click();
        driver.findElement(By.id("customNo$text")).sendKeys(customNo);
        driver.findElement(By.className("search-condition"));
        driver.findElement(By.id("queryForm"));
        driver.findElement(By.className("mini-button-text")).click();
        driver.switchTo().defaultContent();
    }
    //@Test
    public void saveCustom(){
        try {
            driver.switchTo().frame("mainframe");
            //点击添加按钮，弹出添加界面
            //driver.get("http://192.168.2.100:28080/FlxServer/custom/cusprofile/editCusProfileNew.jsp?type=add&_winid=w3689&_t=473420");
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-add")).click();
            //返回主窗体
            driver.switchTo().defaultContent();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-17"));
            driver.findElement(By.className("mini-panel-body"));



            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-17\"]/div/div[2]/div[2]/iframe")));
            //driver.switchTo().frame(0);
            System.out.println(driver.getTitle());
            customNo = driver.findElement(By.name("entity.customNo")).getAttribute("value");
            driver.findElement(By.id("entity.company$text")).click();
            driver.findElement(By.id("entity.company$text")).sendKeys("ceshi0322");
            //点击选择业务员
            driver.findElement(By.xpath("//*[@id=\"entity.salesmanid\"]/span/span/span[2]/span")).click();
            //返回主窗体切换业务员iframe
            driver.switchTo().defaultContent();
            driver.findElement(By.id("mini-18"));
            driver.findElement(By.className("mini-panel-body"));
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-18\"]/div/div[2]/div[2]/iframe")));
            //获取第一个checkbox---业务员姓名
            driver.findElement(By.className("mini-grid-radio-mask")).click();
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-ok")).click();
            //返回主窗体切换业务员iframe---进入添加页面
            driver.switchTo().defaultContent();
            Thread.sleep(1000);
            driver.findElement(By.id("mini-17"));
            driver.findElement(By.className("mini-panel-body"));
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mini-17\"]/div/div[2]/div[2]/iframe")));
            //授权人姓名，身份证，电话
            driver.findElement(By.id("entity.contactPerson$text")).click();
            driver.findElement(By.id("entity.contactPerson$text")).sendKeys("lxt");
            driver.findElement(By.id("entity.contactPersonIdcard$text")).click();
            driver.findElement(By.id("entity.contactPersonIdcard$text")).sendKeys("42102319960722521X");
            driver.findElement(By.id("entity.cellPhone$text")).click();
            driver.findElement(By.id("entity.cellPhone$text")).sendKeys("12345678912");
            //主站需要进行实名认证
            driver.findElement(By.id("certbtn")).click();
            Thread.sleep(1000);
            //认证失败---点击alert继续
            driver.findElement(By.id("mini-114")).click();
            //保存
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".mini-button-text.mini-button-icon.icon-save")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-119")).click();
            Thread.sleep(500);
            driver.findElement(By.id("mini-117")).click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //@Test
    public void testrun(){
        demoFlx de = new demoFlx();
        de.runs();
    }

    public boolean isAlert(WebDriver driver){
        try {
            driver.switchTo().alert();
            System.out.println("当前有弹窗！！");
            return true;
        }catch (NoAlertPresentException e){

            return false;}
    }

}
