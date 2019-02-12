package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.example.demo.logs.ServiceLogs;

/**
 * @author HouZhiBo
 * @version 创建时间：2018年10月30日 下午4:33:14
 * @ClassName 类名称
 * @Description 类描述
 */
@Service("testService")
public class TestService {
	@ServiceLogs(description = "商品同步")
	public Map<String, Object> goodsSync() {
		System.out.println("/***********我是service***********/");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "商品同步");
//		String s=null;
//		System.out.println(s.length());
		return map;
	}
}
