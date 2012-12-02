package com.learning.components.table.tags;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;

import com.learning.components.table.Mappable;

/**
 * 用于显示每列的标签类
 * 
 * @author pengtao
 */
public class ColumnTag extends BaseTag {
	private String width;
	private String paddingLeft;
	// 列样式
	protected String styleClass = "";
	// 表头样式
	protected String headerClass = "";
	// 列标题
	protected String title = "";
	// 列属性
	protected String property;
	// 需要将值映射为对应的显示值
	private Map<Object, String> mappingItem;
	// 是否需要导出到Excel表格，默认为true
	private boolean exportable = true;
	// 是否可以排序
	private boolean sortable;
	// 默认值（在值为空的情况下显示）
	private String defaultValue;
	
	private boolean titlize;
	//是否高亮显示改列所在的行
	private boolean hightLight= false;
	
	@Override
	public void doTag() throws JspException, IOException {
		TableTag table = (TableTag) super.findAncestorWithClass(this,
				TableTag.class);
		table.addColumn(this);
	}

	public Object getDisplayValue(Object item) {
		if (super.hasBody()) {
			return super.getRawBody();
		}
		return this.getValueAfterMapping(item);
	}

	public String getValueAfterMapping(Object item) {
		Object value = this.getRawValue(item);
		if (null != this.getMappingItems()) {
			value = this.getMappingItems().get(value);
		}
		return null == value ? "" : value.toString();
	}

	public Object getRawValue(Object item) {
		Object itemValue = "";
		if (item instanceof Map) {
			for (Entry<String, Object> entry : ((Map<String, Object>) item)
					.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(property)) {
					itemValue = entry.getValue();
				}
			}
			return itemValue;
		}
		try {
			itemValue = PropertyUtils.getProperty(item, property);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("title is: " + this.title);
			logger
					.warn("item  " + item + " does not have property "
							+ property);
			itemValue = "";
		}
		if (null == itemValue) {
			itemValue = this.getDefaultValue();
		}
		return itemValue;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getWidth() {
		return this.width;
	}

	public String getStyleClass() {
		return super.evalExpression(this.styleClass, String.class);
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getHeaderClass() {
		return this.headerClass;
	}

	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}

	public boolean isExportable() {
		return this.exportable;
	}

	public void setExportable(boolean exportable) {
		this.exportable = exportable;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setMappingItem(Object mappingItem) {
		if (mappingItem instanceof Map) {
			this.mappingItem = (Map) mappingItem;
			return;
		}
		this.mappingItem = new LinkedHashMap<Object, String>();
		if (mappingItem instanceof Object[]) {
			mappingItem = Arrays.asList(((Object[]) mappingItem));
		}
		if (mappingItem instanceof List) {
			for (Object obj : (List) mappingItem) {
				if (obj instanceof Mappable) {
					((Mappable) obj).toMap(this.mappingItem);
				}
			}
		}
	}

	public Object getMappingItem() {
		return mappingItem;
	}

	public void setMappingItem(Map mappingItem) {
		this.mappingItem = mappingItem;
	}

	public Map<Object, String> getMappingItems() {
		return mappingItem;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isTitlize() {
		return titlize;
	}

	public void setTitlize(boolean titlize) {
		this.titlize = titlize;
	}

	public void setPaddingLeft(String paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public String getPaddingLeft() {
		return paddingLeft;
	}

	public boolean isHightLight() {
		return hightLight;
	}

	public void setHightLight(boolean hightLight) {
		this.hightLight = hightLight;
	}
	
}
