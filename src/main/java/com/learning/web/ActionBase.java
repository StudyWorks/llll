package com.learning.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.learning.manager.ISimpleManager;
import com.opensymphony.xwork2.ActionSupport;
/**
 * Action基类
 * @author pengtao
 *
 */
public class ActionBase extends ActionSupport implements SessionAware{
	private static final String DEFAULT_JSON_RESULT = "json";
	final protected Logger  logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected ISimpleManager simpleManager;
	protected Map<String, Object> session;
	private String result = SUCCESS;
	private String format;
	
	@Override
	public String execute() throws Exception {
		if(DEFAULT_JSON_RESULT.equals(format))
			result = DEFAULT_JSON_RESULT;
		try{
			preExecute();
			doExecute();
			postExecute();
		}catch(Exception e){
			this.result = handleException(e);
		}
		return this.result;
	}

	protected void preExecute()throws Exception {
	}
	
	protected void doExecute()throws Exception {
	}
	
	protected void postExecute() throws Exception{
		
	}
	
	protected String handleException(Exception e)throws Exception {
		e.printStackTrace();
		throw e;
	}

	protected String getIndexUrl(){
		return "";
	}

	protected void setResult(String result) {
		this.result = result;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setMessage(String message) {
		super.addActionMessage(getText(message));
	}
	
	public void setWarn(String warn){
		super.addActionError(warn);
	}
	
	public void setNotice(String message){
		this.session.put("notice", getText(message));
	}
	
	public String getNotice(){
		HttpSession rawSession = ServletActionContext.getRequest().getSession();
		String notice = (String)rawSession.getAttribute("notice");
		rawSession.removeAttribute("notice");
		return notice;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
