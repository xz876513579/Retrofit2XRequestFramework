package com.xz.retrofit2xrequest.dao.request.responseBean;

/**
 * 所有的response继承
 * 与服务器约定response格式为{int code,String msg,(任意类型，除JsonObject外) result}
 */
public class BaseResqonseBean {
	public int code = -1;
	public String msg;
}
