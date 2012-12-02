package com.learning.components.query.criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

public class CriteriaHelper {
	private Set<String> aliases = new HashSet<String>();
	private DetachedCriteria criteria;
	public CriteriaHelper(DetachedCriteria criteria) {
		this.criteria = criteria;
	}
	
	public DetachedCriteria getCriteria() {
		return this.criteria;
	}
	
	public void add(Criterion c){
		this.criteria.add(c);
	}
	public String createAliasFromProperty(String property){
		if(!property.contains(".")) return property;
		String[] paths = property.split("\\.");
		int aliasLength = paths.length - 1;
		List<String> alias = new ArrayList<String>();
		for(int i = 0; i < aliasLength; i++){
			alias.add(paths[i]);
			this.createAlias(StringUtils.join(alias, "."));
		}
		String fieldName = paths[aliasLength];
		return StringUtils.join(alias, "_") + "." + fieldName;
	}
	public void createAlias(String path){
		this.createAlias(path, path);
	}
	public void createAlias(String path, String alias){
		alias = alias.replace(".", "_");
		if(!this.containsAlias(alias)){
			criteria.createAlias(path,  alias);
			this.aliases.add(alias);
		}
	}
	public boolean containsAlias(String alias){
		return this.aliases.contains(alias);
	}
	
}
