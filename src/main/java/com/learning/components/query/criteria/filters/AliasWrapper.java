package com.learning.components.query.criteria.filters;
import com.learning.components.query.criteria.CriteriaHelper;
import com.learning.components.query.criteria.Filter;




public class AliasWrapper implements Filter{
	private String alias;
	public AliasWrapper(String alias) {
		this.alias = alias;
	}
	public void addTo(CriteriaHelper cHelper) {
		cHelper.createAlias(alias);
	}
	
}
