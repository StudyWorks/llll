package com.learning.components.query.criteria;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ge;
import static org.hibernate.criterion.Restrictions.gt;
import static org.hibernate.criterion.Restrictions.ilike;
import static org.hibernate.criterion.Restrictions.le;
import static org.hibernate.criterion.Restrictions.lt;
import static org.hibernate.criterion.Restrictions.ne;

import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.learning.dao.EscapeLikeExpression;

public enum FilterType {
	LIKE{
		public Criterion toCriterion(String property, Object value) {
			return EscapeLikeExpression.like(property, value.toString());
		}
	},
	START_LIKE{
		public Criterion toCriterion(String property, Object value) {
			return EscapeLikeExpression._like(property, value.toString());
		}
	},
	END_LIKE{
		public Criterion toCriterion(String property, Object value) {
			return EscapeLikeExpression.like_(property, value.toString());
		}
	},
	ILIKE {
		public Criterion toCriterion(String property, Object value) {
			return ilike(property, value.toString(), MatchMode.ANYWHERE);
		}
	},
	EQ {
		public Criterion toCriterion(String property, Object value) {
			return eq(property, value);
		}
	},
	GT {
		public Criterion toCriterion(String property, Object value) {
			return gt(property, value);
		}
	},
	GE {
		public Criterion toCriterion(String property, Object value) {
			return ge(property, value);
		}
	}, 
	LT {
		public Criterion toCriterion(String property, Object value) {
			return lt(property, value);
		}
	},
	LE {
		public Criterion toCriterion(String property, Object value) {
			return le(property, value);
		}
	},
	NE {
		public Criterion toCriterion(String property, Object value) {
			return ne(property, value);
		}
	},
	IN{
		@Override
		public Criterion toCriterion(String property, Object value) {
			if (Object[].class.isInstance(value)){
				return Restrictions.in(property, (Object[])value);
			}
			if(Collection.class.isInstance(value)){
				return Restrictions.in(property, (Collection)value);
			}
			throw new IllegalArgumentException(
			"the args should be array or collection!");
		}
	},
	BTW {
		@Override
		public Criterion toCriterion(String property, Object value) {
			if (!Object[].class.isInstance(value)
					|| ((Object[]) value).length != 2) {
				throw new IllegalArgumentException(
						"the arg should be 2 length's object array!");
			}
			Object[] args = (Object[]) value;
			return Restrictions.between(property, args[0], args[1]);
		}
	};

	abstract public Criterion toCriterion(String property, Object value);


}
