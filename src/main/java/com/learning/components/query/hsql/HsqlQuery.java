package com.learning.components.query.hsql;

import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

import com.learning.components.query.IQuery;

public class HsqlQuery  implements IQuery{
	private String itemsSql;
	private String countSql;
	private boolean isNative;
	private boolean needPreprocess = true;
	private String countHeader = "select count(*) ";
	private ResultTransformer resultTransformer;

	public HsqlQuery setItemsSql(String itemsSql) {
		this.itemsSql = itemsSql;
		return this;
	}

	public HsqlQuery setCountSql(String countSql) {
		this.countSql = countSql;
		return this;
	}

	public HsqlQuery setNative(boolean isNative) {
		this.isNative = isNative;
		return this;
	}

	public HsqlQuery setCountHeader(String countHeader) {
		this.countHeader = countHeader;
		return this;
	}

	public HsqlQuery setResultTransformer(ResultTransformer resultTransformer) {
		this.resultTransformer = resultTransformer;
		return this;
	}

	public HsqlQuery setAliasToEntityMap(boolean aliasToEntityMap) {
		if (aliasToEntityMap)
			return this.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return this;
	}

	public HsqlQuery setAliasToBeanClass(Class<?> beanClass) {
		return this.setResultTransformer(Transformers.aliasToBean(beanClass));
	}

	public HsqlQuery setFuzzyAliasToBeanClass(Class<?> beanClass) {
		return this.setResultTransformer(new FuzzyAliasToBeanResultTransformer(
				beanClass));
	}

	public HsqlQuery setNeedPreprocess(boolean preprocess) {
		this.needPreprocess = preprocess;
		return this;
	}

	public ResultTransformer getResultTransformer() {
		return resultTransformer;
	}

	public String getCountHeader() {
		return countHeader;
	}

	public String getItemsSql() {
		return itemsSql;
	}

	public String getCountSql() {
		return countSql;
	}

	public boolean isNative() {
		return isNative;
	}
	

	public boolean needPreprocess() {
		return this.needPreprocess;
	}
}
