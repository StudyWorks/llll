package com.learning.components.query.hsql;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.PropertyAccessException;
import org.hibernate.transform.ResultTransformer;
/**
 * 将查询结果通过属性注入的方式映射为pojo，实现了Hibernate的ResultTransformer
 * @author pengtao
 *
 */
public class FuzzyAliasToBeanResultTransformer implements ResultTransformer{
	private Class<?> entityClass;
	public FuzzyAliasToBeanResultTransformer(Class<?> entityClass) {
		super();
		this.entityClass = entityClass;
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object target;
		try {
			target = entityClass.newInstance();
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: " + entityClass.getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + entityClass.getName());
		}
		for(int i = 0; i < tuple.length; i++){
			setValue(target, tuple[i], aliases[i]);
		}
		return target;
	}

	void setValue(Object target, Object value, String alias){
		for(Field field : this.entityClass.getDeclaredFields()){
			if(field.getName().equalsIgnoreCase(alias)){
				try {
					if(value == null && field.getType().isPrimitive())
						continue;
					field.setAccessible(true);
					field.set(target, value);
				} catch (Exception e) {
					throw new PropertyAccessException(e, "could not set a field value by reflection", true, entityClass, field.getName());
				}
			}
		}
	}
	public List transformList(List collection) {
		return collection;
	}
}
