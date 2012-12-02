package com.learning.components.query.hsql;

import java.util.List;

public interface IQueryInfo<T> {
	void setCount(long count);
	
	void setItems(List<T> items);
	
	int getFirstResult();
	
	int getMaxResults();
	
	Object getContext();
}
