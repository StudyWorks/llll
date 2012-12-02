package com.learning.components.table.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.springframework.util.Assert;

import com.learning.components.table.IPageInfo;
import com.learning.components.table.renderer.SimpleTableRenderer;

public class SortTag extends BaseTag{
	private IPageInfo pageInfo;
	private String columnName;
	private String style;
	private String position;
	@Override
	public void doTag() throws JspException, IOException {
		if(null == pageInfo){
			pageInfo = (IPageInfo)getRequest().getAttribute("pageInfo");
		}
		Assert.notNull(pageInfo, "pageInfo can't be null");
		String sortedTitle = SimpleTableRenderer.renderSort(pageInfo, columnName, super.getRawBody(),style,position);
		super.getJspContext().getOut().write(sortedTitle);
	}
	
	public void setPageInfo(IPageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
