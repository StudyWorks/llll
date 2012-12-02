package com.learning.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.learning.components.query.IQuery;
import com.learning.components.query.criteria.CriteriaQuery;
import com.learning.manager.IQueryManager;
import com.learning.utils.Utils;
/**
 * 封装了Hibernate查询的Action基类，支持两种查询
 * 	1. CriteriaQuery， 封装了Criteria的查询， 子类override populateCriteriaQuery来添加查询条件
 *  2. HsqlQuery，封装了Query（Hql或sql）的查询，子类override getQuery方法返回Hsql对象，
 *     		  Hsql为普通的java bean，可以配置为spring的bean
 *  
 * @author pengtao
 */
public class QueryActionSupport<T>  extends PaginationActionSupport<T>{
	/**
	 * 选中的用户类型
	 */
	protected Short  selectedUserType;
	@Autowired
	private IQueryManager queryManager;
	
	@Override
	protected void doExecute() throws Exception {
		queryManager.findByQuery(getQuery(), this);
	}
	
	protected IQuery getQuery(){
		CriteriaQuery query = new CriteriaQuery(getEntityClass());
		query.orderBy(getSortColumn(), isSortAsc());
		populateCriteriaQuery(query);
		return query;
	}

	protected Class<?> getEntityClass() {
		return Utils.getGenericType(getClass());
	}
	
	protected void populateCriteriaQuery(CriteriaQuery query){
	}

}
