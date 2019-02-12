package com.hzb.procedure;
/**
* @author 作者：houzhibo
* @version 创建时间：2019年1月28日 下午2:06:37
* 类说明：
*/
public class User {

	private Integer id;
	private String name;
	private String pass;
	private String note;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", pass=" + pass + ", note=" + note + "]";
	}
	
}
