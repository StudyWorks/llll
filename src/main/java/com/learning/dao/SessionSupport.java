package com.learning.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class SessionSupport {
	final protected Logger logger = LoggerFactory.getLogger(getClass());
	protected SessionFactory sessionFactory;
	protected HibernateTemplate ht;
	
	final protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	} 
	final protected Query createQuery(String hql, Object... args) {
		Query query = this.getSession().createQuery(hql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
		return query;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.ht = new HibernateTemplate(this.sessionFactory);
	}
	
	final protected String getIdName(Class<?> entityClass) {
		return getMeta(entityClass).getIdentifierPropertyName();
	}

	final protected ClassMetadata getMeta(Class<?> entityClass) {
		return this.sessionFactory.getClassMetadata(entityClass);
	}

}
