package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HouZhiBo
 * @version 创建时间：2018年10月30日 下午5:46:39
 * @ClassName 类名称
 * @Description 类描述
 */
public class OperateLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1759342366625051701L;

	private Long id;

	/**
	 * 操作人id
	 */
	private Long accountId;

	private Long companyId;

	private Long topicId;

	/**
	 * 操作人 名称
	 */
	private String accountName;

	/**
	 * 当前请求url
	 */
	private String url;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 操作模块
	 */
	private String module;

	/**
	 * 执行方法
	 */
	private String methods;

	/**
	 * 操作内容
	 */
	private String content;

	/**
	 * ip 地址
	 */
	private String ip;

	/**
	 * 执行结果 1:执行成功、2:执行失败
	 */
	private Integer result;

	/**
	 * 操作时间
	 */
	private Date gmtCreate;

	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getMethods() {
		return methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
}
