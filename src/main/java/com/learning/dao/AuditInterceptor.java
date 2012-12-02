package com.learning.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;
@Component
public class AuditInterceptor extends EmptyInterceptor {
	private static final long serialVersionUID = 5982119840291337488L;

	private static final String COLUMN_UPDATED_AT = "updated";
	private static final String COLUMN_CREATED_AT = "created";

    @Override
    public boolean onFlushDirty(Object entity, Serializable id,
            Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) {
        return update(currentState, propertyNames, entity);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state,
            String[] propertyNames, Type[] types) {
        setValue(state, propertyNames, COLUMN_CREATED_AT, new Date());
        return update(state, propertyNames, entity);
    }

    private boolean update(Object[] state, String[] propertyNames, Object entity) {
        setValue(state, propertyNames, COLUMN_UPDATED_AT, new Date());
        return true;
    }

    private void setValue(Object[] currentState, String[] propertyNames,
            String propertyToSet, Object value) {
        int index = Arrays.asList(propertyNames).indexOf(propertyToSet);
        if (index >= 0) {
            currentState[index] = value;
        }
    }
}
