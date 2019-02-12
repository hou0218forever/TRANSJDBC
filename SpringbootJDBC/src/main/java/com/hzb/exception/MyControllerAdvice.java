package com.hzb.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:
 * @date：2019年1月30日 上午8:59:39
 * @descripe:controller 增强器
 *                      启动应用后，被 @ExceptionHandler、@InitBinder、@ModelAttribute
 *                      注解的方法，都会作用在 被 @RequestMapping 注解的方法上。
 */
//@ControllerAdvice
@RestControllerAdvice
public class MyControllerAdvice {

	/**
	 * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		System.out.println(binder);
	}

	/**
	 * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
	 */
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("author", "hzb");
	}

	/**
	 * 全局异常捕捉处理 Exception.class
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Map<String, Object> errorHandler(Exception ex) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 100);
		map.put("msg", ex.getMessage());
		return map;
	}

	/**
	 * 拦截捕捉自定义异常 MyException.class
	 */
	@ResponseBody
	@ExceptionHandler(value = MyException.class)
	public Map<String, String> myErrorHandler(MyException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", ex.getCode());
		map.put("msg", ex.getMsg());
		return map;
	}

	/**
	 * 渲染某个页面模板返回给浏览器
	 */
//	@ExceptionHandler(value = MyException.class)
//	public ModelAndView myErrorHandler2(MyException ex) {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("error");
//		modelAndView.addObject("code", ex.getCode());
//		modelAndView.addObject("msg", ex.getMsg());
//		return modelAndView;
//	}

}
