package com.selenium.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 李啸天 on 2018/11/26.
 * */
public class PropertiesConfig extends Properties{

    private static final long serialVersionUID = 1L;

    private InputStream inputStream = this.getClass().getResourceAsStream("/sms.properties");

    private static PropertiesConfig propertiesUtil;

    private PropertiesConfig()
    {
        try
        {
            this.load(inputStream);
            inputStream.close();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static PropertiesConfig getInstance()
    {
        if (propertiesUtil == null)
        {
            return new PropertiesConfig();
        }
        else
        {
            return propertiesUtil;
        }
    }
}
