package com.selenium.controller;

import com.selenium.service.QuerySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 李啸天 on 2019/3/27.
 */
@RestController
@RequestMapping("/test")
public class testController {

    @Autowired
    QuerySqlService sqlService;

    @RequestMapping("/updateOrder")
    public Integer updateOrder(String orderNo){
        return sqlService.updateOrderByNo(orderNo);
    }
}
