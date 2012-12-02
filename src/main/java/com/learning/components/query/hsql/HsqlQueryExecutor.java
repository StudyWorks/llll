package com.learning.components.query.hsql;

import java.util.Collection;
import java.util.List;

import ognl.Ognl;
import ognl.OgnlException;

import org.hibernate.Query;
import org.hibernate.Session;

import com.learning.components.query.hsql.sqlPreprocessor.FreemarkerSqlPreprocessor;

public class HsqlQueryExecutor implements IConditionProvider{
	private HsqlQuery hsql;
	private IQueryInfo queryInfo;
	private SqlParser sqlParser;
	public HsqlQueryExecutor(HsqlQuery hsql, IQueryInfo queryInfo) {
		this.hsql = hsql;
		this.queryInfo = queryInfo;
		ISqlPreprocessor sqlPreprocessor = ISqlPreprocessor.DEFAULT;
		if (hsql.needPreprocess())
			sqlPreprocessor = new FreemarkerSqlPreprocessor(
					this.queryInfo.getContext());
		this.sqlParser = new SqlParser(hsql, sqlPreprocessor);
	}

	public List<?> getItems(Session session) {
		Query query = createQuery(session, sqlParser.getItemsSql());
		if (hsql.getResultTransformer() != null) {
			query.setResultTransformer(hsql.getResultTransformer());
		}
		if (queryInfo.getFirstResult() >= 0) {
			query.setFirstResult(queryInfo.getFirstResult());
		}
		if (queryInfo.getMaxResults() > 0) {
			query.setMaxResults(queryInfo.getMaxResults());
		}
		return query.list();
	}

	public Long getCount(Session session) {
		Query query = createQuery(session, sqlParser.getCountSql());
		return Long.parseLong(String.valueOf(query.uniqueResult()));
	}

	Query createQuery(Session session, String sql) {
		Query query = hsql.isNative() ? session.createSQLQuery(sql)
				: session.createQuery(sql);
		String[] namedParameters = query.getNamedParameters();
		for (String name : namedParameters) {
			Object value = findValue(name);
			if (value instanceof Collection) {
				query.setParameterList(name, (Collection) value);
			} else if (value instanceof Object[]) {
				query.setParameterList(name, (Object[]) value);
			} else {
				query.setParameter(name, value);
			}
		}
		return query;
	}

	@Override
	public Object findValue(String name) {
		try {
			return Ognl.getValue(name, this.queryInfo.getContext());
		} catch (OgnlException e) {
			e.printStackTrace();
			return null;
		}
	}
}
