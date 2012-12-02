package com.learning.dao;

import org.hibernate.criterion.LikeExpression;
import org.hibernate.criterion.MatchMode;

public class EscapeLikeExpression extends LikeExpression {
	public static final char ESCAPE_CHAR = '!';
	
	protected EscapeLikeExpression(
			String propertyName,
			String value,
			MatchMode matchMode) {
		this(propertyName, value, matchMode, false );
	}
	protected EscapeLikeExpression(
			String propertyName,
			String value,
			MatchMode matchMode,
			boolean ignoreCase) {
		super( propertyName, matchMode.toMatchString( escapeString(value) ), ESCAPE_CHAR, ignoreCase );
	}

	public static EscapeLikeExpression like(String propertyName, String value){
		return new EscapeLikeExpression(propertyName, value, MatchMode.ANYWHERE);
	}
	
	public static EscapeLikeExpression _like(String propertyName, String value){
		return new EscapeLikeExpression(propertyName, value, MatchMode.START);
	}
	
	public static EscapeLikeExpression like_(String propertyName, String value){
		return new EscapeLikeExpression(propertyName, value, MatchMode.END);
	}
	
	public static EscapeLikeExpression ilike(String propertyName, String value){
		return new EscapeLikeExpression(propertyName, value, MatchMode.ANYWHERE, true);
	}
	
	public static String escapeString(String inputString) {
		inputString = inputString.replace("!", "!!").replace("_", "!_").replace("%", "!%");
		return inputString;
	}

}
