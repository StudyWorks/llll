package com.learning.manager;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.learning.components.query.IQuery;
import com.learning.components.query.criteria.CriteriaQuery;
import com.learning.components.query.criteria.ICriteriaQueryExecutor;
import com.learning.components.query.hsql.HsqlQuery;
import com.learning.components.query.hsql.IQueryInfo;

public interface IQueryManager extends ICriteriaQueryExecutor {
	
	CriteriaQuery newCriteriaQuery(Class<?> entityClass);
	
	void findByQuery(IQuery query, IQueryInfo queryInfo);
	
	List findListByHsqlQuery(HsqlQuery query, IQueryInfo queryInfo);
	
	long findCountByQuery(HsqlQuery query, IQueryInfo queryInfo);
	
	<T> List<T> findAll(Class<T> entityClass);

	<T> List<T> findByIds(Class<T> entityClass,
			Collection<? extends Serializable> ids);

	List<?> findByHql(String hql, Object... args);
	
	boolean isExist(String hsql, Object... args);

	Long count(Class<?> entityClass);
}
