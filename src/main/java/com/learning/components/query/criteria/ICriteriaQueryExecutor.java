package com.learning.components.query.criteria;

import java.util.List;

public interface ICriteriaQueryExecutor {
	List findListByCriteriaQuery(CriteriaQuery query);
	
	long findCountByCriteriaQuery(CriteriaQuery query);
	
	<T> T uniqueResult(CriteriaQuery query, Class<T> resultType);
}
