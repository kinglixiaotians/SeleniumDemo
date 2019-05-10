package com.selenium.flx.systemManagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import static com.selenium.flx.flx.journal;
import static com.selenium.flx.flxPublicMethod.switchIframe;
import static com.selenium.flx.flxPublicMethod.taskScreenShot;

public class customerIPSettings {

    public boolean customerIPSettings(WebDriver driver) {
        try {

            Thread.sleep(500);
            switchIframe(driver,"/FlxServer/other/customIp/customIpQueryList.jsp",0);










            if (journal) {
                Reporter.log("系统管理--客户IP设置--测试完成 <br/>");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (journal) {
                taskScreenShot(driver);
                Reporter.log("系统管理--客户IP设置--测试失败。错误：" + e.toString() + "<br/>");
            }
            return false;
        }
    }

}
