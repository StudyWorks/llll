package com.learning.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;

import com.learning.dao.SessionSupport;
import com.learning.manager.ISimpleManager;


@Service
public class SimpleManager extends SessionSupport implements ISimpleManager {
	
	public <T> T find(Class<T> entityClass, Serializable id) {
		return (T) this.getSession().get(entityClass, id);
	}

	public <T> T load(Class<T> entityClass, Serializable id) {
		return (T) this.getSession().load(entityClass, id);
	}

	public void update(Object t) {
		getSession().update(t);
	}

	public void save(Object t) {
		getSession().save(t);
	}

	public void saveOrUpdate(Object t) {
		this.getSession().saveOrUpdate(t);
	}

	public void merge(Object t) {
		this.getSession().merge(t);
	}

	public boolean delete(Object obj) {
		getSession().delete(obj);
		return true;
	}

	public <T> boolean deleteById(Class<T> entityClass, Serializable id) {
		T obj = this.load(entityClass, id);
		return this.delete(obj);
	}

	public int executeUpdate(String hsql, Object... args) {
		Query query = this.getSession().createQuery(hsql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.executeUpdate();
	}
	

	public <T> List<T> findAll(Class<T> entityClass, Criterion...criterions) {
		Criteria criteria = super.getSession().createCriteria(entityClass);
		for(Criterion criterion : criterions){
			criteria.add(criterion);
		}
		return (List<T>)criteria.list();
	}
	
	public <T> T findFirst(Class<T> entityClass, Criterion...criterions){
		Criteria criteria = super.getSession().createCriteria(entityClass);
		for(Criterion criterion : criterions){
			criteria.add(criterion);
		}
		criteria.setMaxResults(1);
		return (T)criteria.uniqueResult();
	}
	

}
