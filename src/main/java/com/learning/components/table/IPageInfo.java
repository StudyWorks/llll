package com.learning.components.table;

public interface IPageInfo {
	public static final String NAME_CURRENT_PAGE = "currentPage";
	public static final String NAME_PER_PAGE = "perPage";
	public static final String NAME_SORT_COLUMN = "sortColumn";
	public static final String NAME_SORT_ASC = "sortAsc";
	
	int getCurrentPage();

	int getPerPage();
	
	int getFirstResult();

	long getCount();
	
	int getTotalPages();
	
	String getQueryString();
	
	String getBaseLink();
	
	String getSortColumn();
	
	boolean isSortAsc();
}
