package com.selenium.utils;

import com.selenium.base.DriverBase;

import java.sql.*;

/**
 * Created by 李啸天 on 2019/3/27.
 */
public class JdbcUtil {

    //jdbcUrl
    public String jdbcUrl = PropertiesConfig.getInstance().getProperty("jdbc.url");

    //userName
    public String userName = PropertiesConfig.getInstance().getProperty("jdbc.userName");

    //password
    public String password = PropertiesConfig.getInstance().getProperty("jdbc.password");

    //driverName
    public String driverName = PropertiesConfig.getInstance().getProperty("jdbc.driverName");


    /**
     * 获取短信验证码
     * @param user
     */
    public void querySmsCode(String user){
        try {
            //加载驱动
            Class.forName(driverName);
            //获取数据库链接
            Connection conn = DriverManager.getConnection(jdbcUrl,userName,password);
            //操作数据库
            String sql="select top 1 Code from aspnet_CodeInfo where UserName=? order by CreateTime desc";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,user);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("Code"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JdbcUtil jdbcUtil = new JdbcUtil();
        jdbcUtil.querySmsCode("15000364728");
    }

}
