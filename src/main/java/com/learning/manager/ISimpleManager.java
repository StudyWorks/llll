package com.learning.manager;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface ISimpleManager {

	<T> T find(Class<T> entityClass, Serializable id);

	<T> T load(Class<T> entityClass, Serializable id);

	void save(Object t);

	void update(Object t);

	void saveOrUpdate(Object t);

	void merge(Object t);

	boolean delete(Object obj);

	<T> boolean deleteById(Class<T> entityClass, Serializable id);
	
	int executeUpdate(String hql, Object... args);

	<T> List<T> findAll(Class<T> entityClass, Criterion...criterions) ;
	
	<T> T findFirst(Class<T> entityClass, Criterion...criterions); 
}