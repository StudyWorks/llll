package com.learning.components.query.hsql;

import com.learning.components.query.hsql.sqlPreprocessor.EmptySqlPreprocessor;

public interface ISqlPreprocessor {
	public static final ISqlPreprocessor DEFAULT = new EmptySqlPreprocessor();
	static class SqlPreprocessException extends RuntimeException{
		public SqlPreprocessException(Throwable cause) {
			super(cause);
		}
	}
	String preprocess(String sql);
}
