package com.learning.web;

import org.apache.struts2.convention.ActionNameBuilder;

import com.learning.utils.WebUtils;

public class AppActionNameBuilder implements ActionNameBuilder {
	
	@Override
	public String build(String className) {
		return WebUtils.buildActionName(className);
	}

}
