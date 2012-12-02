package com.learning.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.learning.components.query.hsql.IQueryInfo;
import com.learning.components.table.IPageInfo;
import com.learning.utils.WebUtils;
import com.opensymphony.xwork2.Preparable;

/**
 * 分页查询的基类
 * 
 * @author pengtao
 */
public class PaginationActionSupport<T> extends ActionBase implements IPageInfo, IQueryInfo<T>, ParameterAware, Preparable{
	public static final int SHOW_ALL = -1;
	public static final int MAX_ROWS = 1000;
	private int perPage = 10;
	private int currentPage = 1;
	private long count;
	private List<T> items;
	private String sortColumn = "created";
	private boolean sortAsc;
	private Map<String, String[]> parameters;
	private String queryString;
	private String requestUri;
	private List<String> includeParams = new ArrayList<String>();
	private List<String> excludeParams = new ArrayList<String>(){{
		add(NAME_CURRENT_PAGE);
		add(NAME_PER_PAGE);
		add(NAME_SORT_COLUMN);
		add(NAME_SORT_ASC);
		add("message");
		add("warn");
		add("session.reset");
	}};
	
	@Override
	public void prepare() throws Exception {
		requestUri = ServletActionContext.getRequest().getRequestURI();
	}
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		try{
			this.currentPage = Integer.parseInt(currentPage);
		}catch(Exception e){
			logger.info("currentPage to set is: {}", currentPage);
			this.currentPage = 1;
		}
		if (this.currentPage<1) {
			this.currentPage = 1;
		}
	}

	public int getFirstResult() {
		int firstResult = (currentPage - 1) * this.getPerPage();
		if(firstResult < 0)
			firstResult = 0;
		return firstResult;
	}

	public void setCount(long count) {
		this.count = count;
		int totalPages = this.getTotalPages();
		if(this.currentPage > totalPages)
			this.currentPage = totalPages;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getMaxResults() {
		return this.getPerPage();
	}
	
	public int getPerPage(){
		Integer page = (Integer)ServletActionContext.getRequest().getSession().getAttribute("pagination.perPage");
		if (null == page) {
			return this.perPage;
		}else{
			return page;
		}
	}

	public long getCount() {
		return count;
	}

	public List<T> getItems() {
		return items;
	}

	public void setPerPage(int perPage) {
		super.session.put("pagination.perPage", perPage);
		this.perPage = perPage;
	}
	
	public IPageInfo getPageInfo(){
		return this;
	}
	
	@Override
	public Object getContext() {
		return this;
	}

	@Override
	public String getQueryString() {
		if(Strings.isNullOrEmpty(queryString)){
			buildQueryString();
		}
		return queryString;
	}

	public String getBaseLink(){
		String baseLink = requestUri + "?" + getQueryString();
		return baseLink;
	}
	
	void buildQueryString() {
		if(!buildIncludeList())
			buildExcludeList();
//		try {
//			this.queryString = URLEncoder.encode(this.queryString, Constants.GLOBAL_ENCODING);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	}
	
	boolean buildIncludeList(){
		if(this.includeParams.isEmpty())
			return false;
		List<String> params = new ArrayList<String>();
		for(String param : this.includeParams){
			String[] values = parameters.get(param);
			for(String value : values)
				params.add(param+ "=" + WebUtils.encode(value));
		}
		queryString = Joiner.on("&").join(params.iterator());
		return true;
	}

	void buildExcludeList() {
		for(String excludeParam : excludeParams)
			this.parameters.remove(excludeParam);
		List<String> params = new ArrayList<String>();
		for(Map.Entry<String, String[]> param : parameters.entrySet()){
			String[] values = param.getValue();
			for(String value : values){
				params.add(param.getKey() + "=" + WebUtils.encode(value));
			}
		}
		queryString = Joiner.on("&").join(params.iterator());
	}
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	
	public boolean isSortAsc() {
		return sortAsc;
	}

	public void setSortAsc(boolean sortAsc) {
		this.sortAsc = sortAsc;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	@Override
	public int getTotalPages() {
		int totalPages = (int)this.count / this.getPerPage();
		if(this.count % this.getPerPage() != 0)
			totalPages ++;
		return totalPages;
	}
	
	final protected void excludeParam(String... params){
		for(String param : params){
			this.excludeParams.add(param);
		}
	}
	
	final protected void includeParam(String... params){
		for(String param : params){
			this.includeParams.add(param);
		}
	}
	
}