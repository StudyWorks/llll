package com.learning.components.query.hsql;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlParser {
	static private Logger logger = LoggerFactory.getLogger(SqlParser.class);
	private HsqlQuery queryInfo;
	private String itemsSql;
	private String countSql;
	private ISqlPreprocessor sqlPreprocessor;

	public SqlParser(HsqlQuery queryInfo, ISqlPreprocessor sqlPreprocessor) {
		this.queryInfo = queryInfo;
		this.sqlPreprocessor = sqlPreprocessor;
		this.itemsSql = preprocess(queryInfo.getItemsSql());// 解析sql语句
		this.initCountSql();
		logger.debug("itemsSql is: {}", this.itemsSql);
		logger.debug("countsql is: {}", this.countSql);
	}

	public void initCountSql() {
		if (StringUtils.isNotBlank(queryInfo.getCountSql())) {
			this.countSql = preprocess(queryInfo.getCountSql());
		} else {
			int fromPosition = 0;
			if (this.itemsSql.startsWith("select")) {
				fromPosition = itemsSql.indexOf(" from ");
			}
			String sql = this.itemsSql.substring(fromPosition);
			sql = sql.replaceAll(
					"(left)?\\s+join\\s+fetch\\s+\\S+", " ");
			this.countSql = queryInfo.getCountHeader() + sql;
		}
	}

	private String preprocess(String sql) {
		sql = sql.replaceAll("\\n|\\t", " ").trim();
		return this.sqlPreprocessor.preprocess(sql);
	}

	public String getCountSql() {
		return countSql;
	}

	public String getItemsSql() {
		return itemsSql;
	}

}
