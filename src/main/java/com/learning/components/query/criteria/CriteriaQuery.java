package com.learning.components.query.criteria;

import static com.learning.components.query.criteria.FilterType.BTW;
import static com.learning.components.query.criteria.FilterType.END_LIKE;
import static com.learning.components.query.criteria.FilterType.EQ;
import static com.learning.components.query.criteria.FilterType.GE;
import static com.learning.components.query.criteria.FilterType.GT;
import static com.learning.components.query.criteria.FilterType.ILIKE;
import static com.learning.components.query.criteria.FilterType.IN;
import static com.learning.components.query.criteria.FilterType.LE;
import static com.learning.components.query.criteria.FilterType.LIKE;
import static com.learning.components.query.criteria.FilterType.LT;
import static com.learning.components.query.criteria.FilterType.NE;
import static com.learning.components.query.criteria.FilterType.START_LIKE;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import com.learning.components.query.IQuery;
import com.learning.components.query.criteria.filters.AliasWrapper;
import com.learning.components.query.criteria.filters.CriterionWrapper;
import com.learning.components.query.criteria.filters.DefaultFilter;

/**
 * 通用查询条件的封装 
 *  new FilterList(User.class).like("name", "peter").eq("age",  "18").rowCount();
 * @author pengtao
 * 
 * @param <T>
 */
public class CriteriaQuery  implements IQuery{
	private List<Filter> filters = new ArrayList<Filter>();

	private Class<?> entityClass;

	private List<Order> orders = new ArrayList<Order>();

	private List<String> joins = new ArrayList<String>();
	
	private ICriteriaQueryExecutor queryExecutor;
	
	private int firstResult;
	
	private int maxResults;
	
	private ProjectionList projectionList;
	
	private ResultTransformer resultTransformer;
	
	private Projection countProjection = Projections.rowCount();

	public CriteriaQuery(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	
	public CriteriaQuery(Class<?> entityClass, ICriteriaQueryExecutor queryExecutor){
		this(entityClass);
		this.queryExecutor = queryExecutor;
	}
	

	/**
	 * 增加full like条件
	 */
	public CriteriaQuery like(String property, Object value) {
		return this.addDefaultFilter(property, value, LIKE);
	}
	
	/**
	 * 增加左 like条件
	 */
	public CriteriaQuery startLike(String property, Object value){
		return this.addDefaultFilter(property, value, START_LIKE);
	}
	
	/**
	 * 增加右 like条件
	 */
	public CriteriaQuery endLike(String property, Object value){
		return this.addDefaultFilter(property, value, END_LIKE);
	}
	
	/**
	 * 增加like条件
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public CriteriaQuery ilike(String property, Object value) {
		return this.addDefaultFilter(property, value, ILIKE);
	}

	/**
	 * 增加等于条件
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public CriteriaQuery eq(String property, Object value) {
		return this.addDefaultFilter(property, value, EQ);
	}

	/**
	 * 增加大于条件
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public CriteriaQuery gt(String property, Object value) {
		return this.addDefaultFilter(property, value, GT);
	}

	/**
	 * 增加大于等于条件
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public CriteriaQuery ge(String property, Object value) {
		return this.addDefaultFilter(property, value, GE);
	}

	/**
	 * 增加小于条件
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public CriteriaQuery lt(String property, Object value) {
		return this.addDefaultFilter(property, value, LT);
	}

	/**
	 * 增加小于等于条件
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public CriteriaQuery le(String property, Object value) {
		return this.addDefaultFilter(property, value, LE);
	}
	
	/**
	 * 增加大于等于且小于条件
	 * 
	 * @param property
	 * @param start between的起始条件
	 * @param end	between的结束条件
	 * @return
	 */
	public CriteriaQuery between(String property, Object start,Object end) {
		if(null == start && null == end){
			return this;
		}
		if(null == start){
			return this.addDefaultFilter(property, end, LT);
		}
		if(null == end){
			return this.addDefaultFilter(property, start, GE);
		}
		return this.addDefaultFilter(property, new Object[]{start,end}, BTW);
	}
	
	/**
	 * 增加为空条件
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public CriteriaQuery isNull(String property) {
		return this.add(Restrictions.isNull(property));
	}
	
	/**
	 * 增加不为为空条件
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public CriteriaQuery isNotNull(String property) {
		return this.add(Restrictions.isNotNull(property));
	}

	/**
	 * 增加不等于条件
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public CriteriaQuery ne(String property, Object value) {
		return this.addDefaultFilter(property, value, NE);
	}

	public CriteriaQuery in(String property, Object values) {
		return this.addDefaultFilter(property, values, IN);
	}

	CriteriaQuery addDefaultFilter(String property, Object value, FilterType type) {
		DefaultFilter filter = new DefaultFilter();
		filter.setProperty(property);
		filter.setValue(value);
		filter.setType(type);
		this.addFilter(filter);
		return this;
	}

	public CriteriaQuery addFilter(Filter filter) {
		this.filters.add(filter);
		return this;
	}

	/**
	 * 升序排序
	 * 
	 * @param property
	 * @return
	 */
	public CriteriaQuery asc(String property) {
		return this.orderBy(property, true);
	}

	/**
	 * 降序排序
	 * 
	 * @param property
	 * @return
	 */
	public CriteriaQuery desc(String property) {
		return this.orderBy(property, false);
	}

	/**
	 * 排序
	 * 
	 * @param property
	 * @return
	 */
	public CriteriaQuery orderBy(String property, boolean isAsc) {
		orders.add(isAsc ? Order.asc(property) : Order.desc(property));
		return this;
	}

	/**
	 * 急迫加载关联的对象
	 * 
	 * @param associationPath
	 * @return
	 */
	public CriteriaQuery join(String associationPath) {
		this.joins.add(associationPath);
		return this;
	}
	
	public CriteriaQuery joins(String... associations){
		for(String association : associations){
			join(association);
		}
		return this;
	}

	/**
	 * 创建别名
	 * 
	 * @param alias
	 * @return
	 */
	public CriteriaQuery createAlias(String alias) {
		this.addFilter(new AliasWrapper(alias));
		return this;
	}

	/**
	 * 增加or条件，如 or(Restrictions.eq("prop1", "val1"), Restrictions.like("prop2",
	 * "val2")); 可以通过静态导入Restrictions使代码可读性更强，如： import static
	 * org.hibernate.criterion.Restrictions or(eq("prop1", "val1"),
	 * like("prop2", "val2"));
	 * 
	 * @param criterions
	 * @return
	 */
	public CriteriaQuery or(Criterion... criterions) {
		Junction junction = Restrictions.disjunction();
		for (Criterion c : criterions) {
			junction.add(c);
		}
		this.add(junction);
		return this;
	}
	
	public CriteriaQuery addProjection(Projection projection, String alias){
		getProjectionList().add(projection, alias);
		return this;
	}
	
	public CriteriaQuery setCountProjection(Projection countProjection){
		this.countProjection = countProjection;
		return this;
	}
	
	public Projection getCountProjection() {
		return countProjection;
	}
	
	private ProjectionList getProjectionList() {
		if(null ==projectionList)
			projectionList = Projections.projectionList();
		return projectionList;
	}
	
	public int getFirstResult() {
		return firstResult;
	}

	public CriteriaQuery setFirstResult(int firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public CriteriaQuery setMaxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	/**
	 * 增加Criterion类型的查询条件
	 * 
	 * @param criterion
	 * @return
	 */
	public CriteriaQuery add(Criterion criterion) {
		this.addFilter(new CriterionWrapper(criterion));
		return this;
	}

	public DetachedCriteria createCriteriaForItems() {
		DetachedCriteria criteria = this.createCommonCriteria();
		for (Order order : this.orders) {
			criteria.addOrder(order);
		}
		for (String join : joins) {
			criteria.setFetchMode(join, FetchMode.JOIN);
		}
		if(this.projectionList != null)
			criteria.setProjection(projectionList);
		if(resultTransformer != null)
			criteria.setResultTransformer(resultTransformer);
		return criteria;
	}
	
	public DetachedCriteria createCommonCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(this.entityClass);
		CriteriaHelper cHelper = new CriteriaHelper(criteria);
		for (Filter filter : filters) {
			filter.addTo(cHelper);
		}
		return criteria;
	}
	
	public long count(){
		validateQueryExecutor();
		return queryExecutor.findCountByCriteriaQuery(this);
	}
	
	public List<?> list(){
		validateQueryExecutor();
		return queryExecutor.findListByCriteriaQuery(this);
	}
	
	public <T> List<T> list(Class<T> entityClass){
		return (List<T>)list();
	}
	
	public <T> T uniqueResult(Class<T> resultType, T defaultValue){
		T result = uniqueResult(resultType);
		if(result == null)
			result = defaultValue;
		return result;
	}
	
	public <T> T uniqueResult(Class<T> resultType){
		validateQueryExecutor();
		return queryExecutor.uniqueResult(this, resultType);
	}

	public boolean exists(){
		return count() > 0;
	}
	
	private void validateQueryExecutor() {
		Assert.notNull(this.queryExecutor, "queryExecutor can't be null!");
	}
	
	public CriteriaQuery setResultTransformer(ResultTransformer resultTransformer){
		this.resultTransformer = resultTransformer;
		return this;
	}
}
