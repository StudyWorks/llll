package com.learning.components.table.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.springframework.util.Assert;

import com.google.common.base.Strings;
import com.learning.components.table.HtmlBuilder;
import com.learning.components.table.IPageInfo;
import com.learning.components.table.renderer.IPageRenderer;
import com.learning.components.table.renderer.NewsPageRenderer;
import com.learning.components.table.renderer.PageRenderer;

public class ToolbarTag extends BaseTag{
	private static final String TOOLBAR_CACHE_NAME = "TOOLBAR";
	private IPageInfo pageInfo;
	private IPageRenderer pageRenderer = null;
	private String tendererName;
	private HtmlBuilder html = new HtmlBuilder();
	private boolean cache = true;
	@Override
	public void doTag() throws JspException, IOException {
		if(null == pageInfo){
			pageInfo = (IPageInfo)getRequest().getAttribute("pageInfo");
		}
		Assert.notNull(pageInfo, "pageInfo can't be null");
		render();
		String content = html.toString();
		if(cache)
			super.getJspContext().setAttribute(TOOLBAR_CACHE_NAME, content);
		super.getJspContext().getOut().write(content);
	}

	void render() {
		if(Strings.isNullOrEmpty(tendererName)){
			pageRenderer = new PageRenderer();
			this.html.div().styleClass("tab_ft").close();
			this.html.div().styleClass("tab_ft_op fl").close();
			this.html.append(super.getRawBody());
			this.html.divEnd();
			String pager = pageRenderer.render(pageInfo);
			this.html.append(pager);
			this.html.divEnd();
		}else if(tendererName.equals("newsPage")){
			pageRenderer = new NewsPageRenderer();
			String pager = pageRenderer.render(pageInfo);
			this.html.append(pager);
		}
	}
	
	public void setPageInfo(IPageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	public void setPageRenderer(IPageRenderer pageRenderer) {
		this.pageRenderer = pageRenderer;
	}

	public void setTendererName(String tendererName) {
		this.tendererName = tendererName;
	}

	public String getTendererName() {
		return tendererName;
	}
	
	public void setCache(boolean cache) {
		this.cache = cache;
	}
}
