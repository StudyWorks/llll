package com.learning.components.query.criteria.filters;
import org.apache.commons.lang3.StringUtils;

import com.learning.components.query.criteria.CriteriaHelper;
import com.learning.components.query.criteria.Filter;
import com.learning.components.query.criteria.FilterType;

public class DefaultFilter implements Filter{
	private String property;
	private FilterType type;
	private Object value;

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}

	public void addTo(CriteriaHelper cHelper){
		if(invalid()) return;
		String alias = cHelper.createAliasFromProperty(this.property);
		cHelper.add(type.toCriterion(alias, value));
	}

	boolean hasBlank(Object...objs){
		for(Object obj : objs){
			if(null == obj || StringUtils.isBlank(obj.toString())) return true;
		}
		return false;  
	}
	boolean invalid(){
		return this.hasBlank(this.value, this.type, this.property);
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
		if(this.value instanceof String)
			this.value = ((String)value).trim();

	}
	public String getType() {
		return type.name();
	}
	public void setType(FilterType type) {
		this.type = type;
	}
}
