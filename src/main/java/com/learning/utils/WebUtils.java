package com.learning.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.google.common.base.Strings;

public class WebUtils {
	final public static String ACTION_SUFFIX = "Action";
	static Log logger = LogFactory.getLog(WebUtils.class);

	public static boolean isGetRequest(){
		return "GET".equalsIgnoreCase(ServletActionContext.getRequest().getMethod());
	}
	
	public static String escapeJS(String value) {
        return StringEscapeUtils.escapeEcmaScript(value);
    }

	public static boolean isAjaxRequest(HttpServletRequest request ) {
		return "XMLHttpRequest".equals(request.getHeader("x-requested-with"));
	}
	
	public static HttpServletRequest getRequest(JspContext context) {
		return (HttpServletRequest) ((PageContext) context).getRequest();
	}
	
	public static String buildActionName(String className) {
		String actionName = className;
		if (actionName.endsWith(ACTION_SUFFIX)) {
			actionName = actionName.substring(0, actionName.length()
					- ACTION_SUFFIX.length());
		}
		return StringUtils.uncapitalize(actionName);
	}
	
	public static String encode(String path){
		try {
			return URLEncoder.encode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return path;
		}
	}
	
	public static boolean contains(String objs, Object obj){
		if(Strings.isNullOrEmpty(objs) || null == obj || Strings.isNullOrEmpty(obj.toString()))
			return false;
		return Arrays.asList(objs.split(",\\s*")).contains(obj.toString());
	}
	
}
