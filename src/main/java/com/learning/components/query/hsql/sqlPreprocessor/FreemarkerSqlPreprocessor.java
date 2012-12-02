package com.learning.components.query.hsql.sqlPreprocessor;

import java.io.StringReader;
import java.io.StringWriter;

import com.learning.components.query.hsql.ISqlPreprocessor;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerSqlPreprocessor implements ISqlPreprocessor{
	private static Configuration config = new Configuration();
	private Object context;
	
	public FreemarkerSqlPreprocessor(Object context) {
		super();
		this.context = context;
	}

	@Override
	public String preprocess(String sql) {
		try {
			Template template = new Template(null, new StringReader(sql), config);
			StringWriter out = new StringWriter();
			template.process(context, out);
			return out.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlPreprocessException(e);
		}
	}
}
