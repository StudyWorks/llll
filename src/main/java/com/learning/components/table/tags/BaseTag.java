package com.learning.components.table.tags;

import java.io.StringWriter;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTag extends SimpleTagSupport {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public boolean hasBody() {
		return null != getJspBody();
	}

	public String getRawBody() {
		if (!hasBody())
			return "";
		StringWriter value = new StringWriter();
		try {
			getJspBody().invoke(value);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return value.toString();
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) ((PageContext) this.getJspContext())
				.getRequest();
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) getPageContext().getResponse();
	}

	public PageContext getPageContext() {
		return (PageContext) this.getJspContext();
	}
	
	public String evalStringExpression(String expression){
		return evalExpression(expression, String.class);
	}

	@SuppressWarnings("unchecked")
	public <T> T evalExpression(String expression, Class<T> returnType) {
		if(StringUtils.isBlank(expression))
			return null;
		ServletContext servletContext = this.getRequest()
				.getSession()
				.getServletContext();
		ExpressionFactory ef = JspFactory
				.getDefaultFactory()
				.getJspApplicationContext(servletContext)
				.getExpressionFactory();
		ELContext ec = this.getJspContext().getELContext();
		return (T)ef.createValueExpression(ec, expression,
				returnType).getValue(ec);
	}

}
