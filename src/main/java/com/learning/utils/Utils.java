package com.learning.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Utils {

	static Log logger = LogFactory.getLog(Utils.class);

	public static String uuid() {
		return UUID.randomUUID().toString();
	}


	public static boolean hasBlank(Object... objs) {
		for (Object obj : objs) {
			if (null == obj || StringUtils.isBlank(obj.toString())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNotBlank(Object obj) {
		return obj != null && StringUtils.isNotBlank(obj.toString());
	}

	public static <T> Class<T> getGenericType(Class<?> klass){
		return getGenericType(klass, 0);
	}
	
	public static <T> Class<T> getGenericType(Class<?> klass, int index) {
		Type genericSuperclass = klass.getGenericSuperclass();
		if(genericSuperclass instanceof ParameterizedType)
			return (Class<T>) ((ParameterizedType) klass.getGenericSuperclass())
					.getActualTypeArguments()[index];
		if(genericSuperclass.equals(Object.class))
			return null;
		return getGenericType(klass.getSuperclass(), index);
	}

	public static <T> List<T> splitToList(String str, Class<T> klass) {
		List<T> result = new ArrayList<T>();
		if (StringUtils.isNotBlank(str)) {
			for (String string : str.trim().split("\\s*,\\s*")) {
				Object item = klass.equals(Integer.class) ? Integer
						.parseInt(string) : string;
				result.add((T) item);
			}
		}
		return result;
	}

	static ParserContext parserContext = new ParserContext() {

		public String getExpressionPrefix() {
			return "${";
		}

		public String getExpressionSuffix() {
			return "}";
		}

		public boolean isTemplate() {
			return true;
		}
	};
	
	static ExpressionParser parser = new SpelExpressionParser();

	public static Object parse(String expression, Object root) {
		return parser.parseExpression(expression, parserContext).getValue(root);
	}

	public static Map<String, Object> toMap(Object... args) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < args.length; i++) {
			map.put(args[i].toString(), args[++i]);
		}
		return map;
	}
	
}
