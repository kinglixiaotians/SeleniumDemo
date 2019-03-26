package com.selenium.test.util;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Created by 李啸天 on 2019/3/22.
 */
public class TestListenerScreenShot extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        baseDriver bs = (baseDriver)tr.getInstance();
        bs.taskScreenShot();
    }


}
