package com.learning.components.table.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.learning.components.table.HtmlBuilder;
import com.learning.components.table.IPageInfo;
import com.learning.components.table.renderer.ITableRenderer;
import com.learning.components.table.renderer.SimpleTableRenderer;

/**
 * @author pengtao
 */
public class TableTag extends BaseTag {
	public static final String ROW_INDEX = "rowIndex";
	private HtmlBuilder html;
	private String tableId = "tabWrap";
	// 设置在迭代时使用的变量名
	private String var = "var";
	
	private String varStatus = "varStatus";
	// 设置列表宽度
	private String width = "100%";
	// 设置列表高度
	private String height = "auto";
	// 所有的列
	private List<ColumnTag> columns = new ArrayList<ColumnTag>();
	// 待显示的数据列表
	private List<Object> items;
	// table展示器
	private ITableRenderer tableRenderer;
	private IPageInfo pageInfo;
	private String styleClass = "tab_wrap";
	
	private String rowStyleClass;
	

	@Override
	public void doTag() throws JspException, IOException {
		html = new HtmlBuilder();
		super.getRawBody();
		if (null == tableRenderer)
			tableRenderer = new SimpleTableRenderer();
		if(null == this.items)
			this.items = (List<Object>)super.getRequest().getAttribute("items");
		if(null == this.items)
			this.items = Collections.EMPTY_LIST;
//			throw new IllegalArgumentException("Can't find items!");
		if(null == this.pageInfo)
			this.pageInfo = (IPageInfo)getRequest().getAttribute("pageInfo");
		if(null == this.pageInfo)
			throw new IllegalArgumentException("PageInfo can't be null");
		tableRenderer.render(html, this);
		getJspContext().getOut().write(html.toString());
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getVar() {
		return var;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public HtmlBuilder getHtmlBuilder() {
		return this.html;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public List<ColumnTag> getColumns() {
		return this.columns;
	}

	public String getWidth() {
		return width;
	}

	public String getHeight() {
		return height;
	}
	
	public void setItems(List<Object> items) {
		this.items = items;
	}

	public List<Object> getItems() {
		return this.items;
	}

	public void addColumn(ColumnTag column) {
		this.columns.add(column);
	}

	public void setTableRenderer(ITableRenderer tableRenderer) {
		this.tableRenderer = tableRenderer;
	}

	public String getId() {
		return this.tableId;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public IPageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(IPageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public void setVarStatus(String varStatus) {
		this.varStatus = varStatus;
	}
	
	public String getVarStatus() {
		return varStatus;
	}
	
	public String getRowStyleClass() {
		return rowStyleClass;
	}
	public void setRowStyleClass(String rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
	}
}
