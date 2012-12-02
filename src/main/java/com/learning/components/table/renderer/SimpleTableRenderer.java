package com.learning.components.table.renderer;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.learning.components.table.HtmlBuilder;
import com.learning.components.table.IPageInfo;
import com.learning.components.table.tags.ColumnTag;
import com.learning.components.table.tags.TableTag;

public class SimpleTableRenderer implements ITableRenderer {
	protected TableTag table;
	protected HtmlBuilder html;
	protected List<ColumnTag> columns;
	private IPageInfo pageInfo;

	@Override
	public void render(HtmlBuilder html, TableTag table) {
		this.html = html;
		this.table = table;
		this.columns = this.table.getColumns();
		this.pageInfo = this.table.getPageInfo();
		doRenderer();
	}

	protected void doRenderer() {
//		renderTotal();
		renderTable();
	}
	
	protected void renderTotal(){
		html.div().styleClass("pager_number").close();
		html.append("共<strong>").append(pageInfo.getCount()).append("</strong>条数据");
		html.divEnd();
	}

	protected void renderTable() {
		this.html.div().id("tabCont").styleClass("tabCont").close();

		this.html.table(0).close();
		this.buildHeader();
		this.buildBody();
		this.html.tableEnd(0);

		this.html.divEnd();
	}

	void buildHeader() {
		this.html.thead(0).close();
		html.tr(0).close();
		for (ColumnTag column : this.columns) {
			renderHead(column);
		}
		html.trEnd(0);
		this.html.theadEnd(0);
	}

	public void buildBody() {
		this.html.tbody(0).close();
		boolean even = true;
		for (int i = 0; i < table.getItems().size(); i++) {
			Object current = table.getItems().get(i);
			this.table.getPageContext().setAttribute(
					this.table.getVar(), current);
			this.table.getPageContext().setAttribute(
					TableTag.ROW_INDEX, i);
			
			String styleClass = buildStyleClass(even);
			html.tr(0).styleClass(styleClass).close();
			for (ColumnTag column : columns) {
				renderCell(column, current);
			}
			html.trEnd(0);
			even = !even;
		}
		this.table.getPageContext()
				.removeAttribute(this.table.getVar());
		this.html.tbodyEnd(0);
	}

	private String buildStyleClass(boolean even) {
		String styleClass = even ? "even" : "odd";
		String rowStyleClass = table.getRowStyleClass();
		if(StringUtils.isNotBlank(rowStyleClass)){
			styleClass += " " + table.evalExpression(rowStyleClass, String.class);
		}
		return styleClass;
	}

	public void renderHead(ColumnTag column) {
		String headerClass = column.getHeaderClass();
		if(column.isSortable()){
			boolean isCurrentColumn = pageInfo.getSortColumn().equals(column.getProperty());
			String sortClass = pageInfo.isSortAsc() ? "tab_sortup" : "tab_sortdown";
			String cssClass =  isCurrentColumn ? sortClass : "tab_sortdefault";
			headerClass = cssClass + " " +headerClass;
		}
		html.th(0).styleClass(headerClass).width(column.getWidth()).paddingLeft(column.getPaddingLeft()).close();
		String title = column.getTitle();
		if(!column.isSortable()){
			html.append(title);
		}else{
			html.append(renderTitle(this.pageInfo, column.getProperty(), title));
		}
		html.thEnd();
	}

	public static String renderTitle(IPageInfo pageInfo, String property,  String title) {
		HtmlBuilder html = new HtmlBuilder();
		boolean isCurrentColumn = pageInfo.getSortColumn().equals(property);
		String[] urls = {pageInfo.getBaseLink(),
				"&sortColumn=" + property,
				"sortAsc=" + (isCurrentColumn ? !pageInfo.isSortAsc() : false),
				"currentPage=" + pageInfo.getCurrentPage()};
		String href = Joiner.on("&").join(urls);
		
		html.a().href(href).close().append(title);
		html.span().close();
		html.spanEnd();
		html.aEnd();
		return html.toString();
	}
	
	public static String renderSort(IPageInfo pageInfo, String property,  String title,String style,String position) {
		HtmlBuilder html = new HtmlBuilder();
		boolean isCurrentColumn = pageInfo.getSortColumn().equals(property);
		boolean sortAsc = isCurrentColumn ? !pageInfo.isSortAsc() : false;
		String[] urls = {pageInfo.getBaseLink(),
				"&sortColumn=" + property,
				"sortAsc=" + sortAsc,
				"currentPage=" + pageInfo.getCurrentPage()};
		String href = Joiner.on("&").join(urls);
		
		html.th(0).style(style);
		if(isCurrentColumn){
			if(sortAsc){
				html.styleClass(position + " tab_sortdown");
			}else{
				html.styleClass(position + " tab_sortup");
			}
		}else{
			html.styleClass(position + " tab_sortdefault");
		}
		html.close();
		html.a().href(href).close().append(title);
		html.span().close();
		html.spanEnd();
		html.aEnd();
		html.thEnd();
		return html.toString();
	}
	
	String buildHref(){
		String[] urls = {pageInfo.getBaseLink(),
				"&sortColumn=" + pageInfo.getSortColumn(),
				"sortAsc=" + pageInfo.isSortAsc(),
				"currentPage=" + pageInfo.getCurrentPage()};
		return  Joiner.on("&").join(urls);
	}

	public void renderCell(ColumnTag column, Object item) {
		Object value = column.getDisplayValue(item);
		html.td(0).styleClass(column.getStyleClass());
		if(column.isTitlize() && value != null){
			String title;
			if(StringUtils.isBlank(column.getProperty()) ){
				title = value.toString();
			}else{
				title = column.getValueAfterMapping(item);
			}
			html.title(StringEscapeUtils.escapeHtml4(title));
		}
		html.close();
		html.append(value);
		html.tdEnd();
	}
}
