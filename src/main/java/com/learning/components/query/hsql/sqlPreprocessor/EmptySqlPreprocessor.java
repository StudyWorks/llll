package com.learning.components.query.hsql.sqlPreprocessor;

import org.springframework.stereotype.Component;

import com.learning.components.query.hsql.ISqlPreprocessor;

@Component
public class EmptySqlPreprocessor implements ISqlPreprocessor{

	@Override
	public String preprocess(String sql) {
		return sql;
	}

}
