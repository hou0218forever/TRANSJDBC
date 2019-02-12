package com.hzb.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hzb.exception.MyException;

/**
 * @author:
 * @date：2019年1月30日 上午9:01:55
 * @descripe:
 */
@RestController
public class TestController {

	/**
	 * 通过map获取
	 */
	@RequestMapping("/home-map")
	public String home(ModelMap modelMap) {
		System.out.println(modelMap.get("author"));
		return modelMap.get("author").toString();
	}

	/**
	 * 通过@ModelAttribute获取
	 */
	@RequestMapping("/home-attribute")
	public String home(@ModelAttribute("author") String author) {
		System.out.println(author);
		return author;
	}

	/**
	 * 自定义异常
	 */
	@RequestMapping("/home-myex")
	public String home() throws Exception {
//	        throw new Exception("Sam 错误");
		throw new MyException("101", "自定义 错误");

	}
}
