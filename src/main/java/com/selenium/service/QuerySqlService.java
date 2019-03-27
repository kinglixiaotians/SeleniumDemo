package com.selenium.service;

import java.util.Map;

/**
 * Created by 李啸天 on 2019/3/26.
 */
public interface QuerySqlService {

    /**
     * 查询企业号的最新短信code码
     * @param userName
     * @return
     */
    public Map getMapById(String userName);

    /**
     * 根据订单号修改回复状态
     * @param orderNo
     * @return
     */
    public Integer updateOrderByNo(String orderNo);

}
