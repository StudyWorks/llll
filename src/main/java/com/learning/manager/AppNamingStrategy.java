package com.learning.manager;

import org.hibernate.cfg.DefaultNamingStrategy;
import org.springframework.stereotype.Component;
@SuppressWarnings("serial")
@Component
public class AppNamingStrategy extends DefaultNamingStrategy{

	@Override
	public String foreignKeyColumnName(String propertyName,
			String propertyEntityName, String propertyTableName,
			String referencedColumnName) {
		String name = super.foreignKeyColumnName(propertyName, propertyEntityName,
				propertyTableName, referencedColumnName);
		return name + "Id";
	}
	@Override
	public String columnName(String columnName) {
		return columnName;
	}
	
	@Override
	public String classToTableName(String className) {
		return "ssp_" + super.classToTableName(className);
	}
}
