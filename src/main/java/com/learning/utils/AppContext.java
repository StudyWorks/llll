package com.learning.utils;

import org.apache.struts2.config.DefaultSettings;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContext implements ApplicationContextAware{
    static ApplicationContext ac;

    public static <T> T getBean(Class<T> klass){
        return ac.getBean(klass);
    }
    
    public static <T> T getBean(String name, Class<T> type){
	    return ac.getBean(name, type);
    }
    
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        AppContext.ac = ac;
    }
    
    public static boolean isDev() {
		return "true".equals(DefaultSettings.get("struts.devMode"));
	}
    
    public static String getExtension(){
    	return DefaultSettings.get("struts.action.extension");
    }
    
}
