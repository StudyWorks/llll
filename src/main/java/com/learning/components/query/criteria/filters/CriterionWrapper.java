package com.learning.components.query.criteria.filters;

import org.hibernate.criterion.Criterion;

import com.learning.components.query.criteria.CriteriaHelper;
import com.learning.components.query.criteria.Filter;

public class CriterionWrapper implements Filter{
	private Criterion criterion;
	
	public CriterionWrapper(Criterion criterion) {
		this.criterion = criterion;
	}

	public void addTo(CriteriaHelper cHelper) {
		cHelper.add(criterion);
	}

}
