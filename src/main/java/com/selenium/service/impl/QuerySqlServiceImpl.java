package com.selenium.service.impl;

import com.selenium.service.QuerySqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 李啸天 on 2019/3/26.
 */
@Service
@Slf4j
public class QuerySqlServiceImpl implements QuerySqlService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map getMapById(String userName) {
        log.info("获取短信code码，传入企业号为{}",userName);
        String sql = "select top 1 Code from aspnet_CodeInfo where UserName=? order by CreateTime desc";
        Map map= jdbcTemplate.queryForMap(sql,userName);
        return map;
    }

    @Override
    public Integer updateOrderByNo(String orderNo) {
        log.info("修改订单回复状态,传入订单号为{}",orderNo);
        String sql = "update flx_order_info set BACK_STATUS='1' where ORDER_NO=?";
        int count = jdbcTemplate.update(sql,orderNo);
        log.info("成功修改订单回复状态共{}条数据",count);
        return count;
    }
}
