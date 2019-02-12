package com.hzb.controller;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hzb.exception.BuyStockException;
import com.hzb.service.impl.BuyStockServiceImpl;

/**
 * @author: houzhibo
 * @date: 2019年1月30日 下午12:14:50
 * @describe:
 */
@RestController
public class TestController {

	@Resource
	BuyStockServiceImpl buyStockServiceImpl;
	
	@RequestMapping("/test")
	public void test() {
		buyStockServiceImpl.buyStock("张三", 2000, "乐高", 3);
	}
}
