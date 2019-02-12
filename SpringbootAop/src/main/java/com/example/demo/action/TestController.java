package com.example.demo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.logs.ControllerLogs;
import com.example.demo.logs.OptionalLog;
import com.example.demo.service.TestService;
import com.example.demo.util.JSONResult;

/**
 * @author HouZhiBo
 * @version 创建时间：2018年10月30日 下午3:44:32
 * @ClassName 类名称
 * @Description 类描述
 */
@RequestMapping("/test")
@RestController
public class TestController {

	@Autowired
	private TestService testService;

	@PostMapping("/createURL")
//	@ControllerLogs(description = "创建机器二维码图片URL")
	@OptionalLog(module = "创建机器二维码图片URL", methods = "创建机器二维码图片Method", type = "3")
	public String createURL(@NotEmpty(message = "mno is required") String mno,
			@NotEmpty(message = "deviceid is required") String deviceid) {
		System.out.println("/*******************************************/");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "创建机器二维码图片URL");
		Map<String, Object> goodsSync = testService.goodsSync();
		System.out.println(goodsSync);
		return JSONResult.create(map).toJSON();
	}

	@RequestMapping("/list_WX")
//	@ControllerLogs(description = "创建机器二维码图片URL")
	@OptionalLog(module = "计划操作日志", methods = "微信创建计划", type = "3")
	public String searchAll_WX() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "计划操作日志");
		Map<String, Object> goodsSync = testService.goodsSync();
		System.out.println(goodsSync);
		return JSONResult.create(map).toJSON();
	}
}
