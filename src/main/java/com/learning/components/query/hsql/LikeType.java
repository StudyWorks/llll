package com.learning.components.query.hsql;

import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public enum LikeType {
	EQ{
		public Criterion  toCriterion(String propertyName, Object value){
			return Restrictions.eq(propertyName, value);
		}
	}, LT{
		public Criterion  toCriterion(String propertyName, Object value){
			return Restrictions.lt(propertyName, value);
		}
	}, LE{
		public Criterion  toCriterion(String propertyName, Object value){
			return Restrictions.le(propertyName, value);
		}
	}, GT{
		public Criterion  toCriterion(String propertyName, Object value){
			return Restrictions.gt(propertyName, value);
		}
	}, GE{
		public Criterion  toCriterion(String propertyName, Object value){
			return Restrictions.ge(propertyName, value);
		}
	}, NE{
		public Criterion  toCriterion(String propertyName, Object value){
			return Restrictions.ne(propertyName, value);
		}
	}, LIKE{
		public Criterion  toCriterion(String propertyName, Object value){
			return Restrictions.like(propertyName, value);
		}
	}, START_LIKE{
		public Criterion  toCriterion(String propertyName, Object value){
			return Restrictions.like(propertyName, (String)value, MatchMode.START);
		}
	}, END_LIKE{
		public Criterion  toCriterion(String propertyName, Object value){
			return Restrictions.like(propertyName, (String)value, MatchMode.END);
		}
	}, IN{
		public Criterion  toCriterion(String propertyName, Object value){
			if(value instanceof Collection){
				return Restrictions.in(propertyName, (Collection)value);
			}else if(value instanceof Object[]){
				return Restrictions.in(propertyName, (Object[])value);
			}else {
				throw new IllegalArgumentException("value  must be Collection or Array!");
			}
		}
	};
	
	abstract public Criterion toCriterion(String propertyName, Object value);
}
