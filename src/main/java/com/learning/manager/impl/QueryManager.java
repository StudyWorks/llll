package com.learning.manager.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.learning.components.query.IQuery;
import com.learning.components.query.criteria.CriteriaQuery;
import com.learning.components.query.hsql.HsqlQuery;
import com.learning.components.query.hsql.HsqlQueryExecutor;
import com.learning.components.query.hsql.IQueryInfo;
import com.learning.dao.SessionSupport;
import com.learning.manager.IQueryManager;
@Service
public class QueryManager extends SessionSupport implements IQueryManager{
	final static private String COUNTER_HEADER = "select count(*) ";
	@Override
	public CriteriaQuery newCriteriaQuery(Class<?> entityClass) {
		return new CriteriaQuery(entityClass, this);
	}

	@Override
	public <T> T uniqueResult(CriteriaQuery query, Class<T> resultType){
		DetachedCriteria criteria = query.createCriteriaForItems();
		Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
		if(query.getFirstResult() >= 0)
			executableCriteria.setFirstResult(query.getFirstResult());
		executableCriteria.setMaxResults(1);
		return (T)executableCriteria.uniqueResult();
	}
	
	@Override
	public List findListByCriteriaQuery(CriteriaQuery query) {
		DetachedCriteria criteria = query.createCriteriaForItems();
		Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
		if(query.getFirstResult() >= 0)
			executableCriteria.setFirstResult(query.getFirstResult());
		int maxResults = query.getMaxResults();
		if(maxResults > 0)
			executableCriteria.setMaxResults(maxResults);
		return executableCriteria.list();
	}

	@Override
	public long findCountByCriteriaQuery(CriteriaQuery query) {
		DetachedCriteria criteria = query.createCommonCriteria();
		criteria.setProjection(query.getCountProjection());
		 Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
		Object result = executableCriteria.uniqueResult();
		if(result == null)
			return 0;
		return (Long)result;
	}

	void findByCriteriaQuery(CriteriaQuery query, IQueryInfo queryInfo) {
		Long count = this.findCountByCriteriaQuery(query);
		queryInfo.setCount(count);
		query.setFirstResult(queryInfo.getFirstResult()).setMaxResults(queryInfo.getMaxResults());
		List<?> items = this.findListByCriteriaQuery(query);
		queryInfo.setItems(items);
	}
	
	void findByHsqlQuery(HsqlQuery query, IQueryInfo queryInfo){
		HsqlQueryExecutor hsqlQueryExecutor = new HsqlQueryExecutor(query, queryInfo);
		queryInfo.setCount(hsqlQueryExecutor.getCount(getSession()));
		queryInfo.setItems(hsqlQueryExecutor.getItems(getSession()));
	}

	@Override
	public List findListByHsqlQuery(HsqlQuery query, IQueryInfo queryInfo) {
		return new HsqlQueryExecutor(query, queryInfo).getItems(getSession());
	}

	@Override
	public long findCountByQuery(HsqlQuery query, IQueryInfo queryInfo) {
		return new HsqlQueryExecutor(query, queryInfo).getCount(getSession());
	}
	
	public void findByQuery(IQuery query, IQueryInfo queryInfo){
		if(query instanceof HsqlQuery)
			this.findByHsqlQuery((HsqlQuery)query, queryInfo);
		else if(query instanceof CriteriaQuery)
			this.findByCriteriaQuery((CriteriaQuery)query, queryInfo);
		else
			throw new IllegalArgumentException("query type not support! " + query);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		return (List<T>)this.newCriteriaQuery(entityClass).list();
	}

	@Override
	public <T> List<T> findByIds(Class<T> entityClass,
			Collection<? extends Serializable> ids) {
		if(ids.isEmpty())
			return Collections.EMPTY_LIST;
		return (List<T>)this.newCriteriaQuery(entityClass).in(super.getIdName(entityClass), ids).list();
	}

	public List<?> findByHql(String hql, Object... args) {
		Query query = createQuery(hql, args);
		return query.list();
	}
	
    public boolean isExist(String hsql, Object... args) {
    	if(!hsql.startsWith(COUNTER_HEADER))
    		hsql = COUNTER_HEADER + hsql;
    	Query query = createQuery(hsql, args);
    	return !Long.valueOf(0).equals(query.uniqueResult());
    }
    
	@Override
	public Long count(Class<?> entityClass) {
		return this.newCriteriaQuery(entityClass).count();
	}

}
