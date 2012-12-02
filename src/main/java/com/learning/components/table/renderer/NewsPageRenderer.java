package com.learning.components.table.renderer;

import com.learning.components.table.IPageInfo;
import com.learning.utils.FtlUtils;

public class NewsPageRenderer implements IPageRenderer{
	private String pageTemplate = "/template/pagination/news.ftl";
	
	@Override
	public String render(IPageInfo pageInfo) {
		return FtlUtils.getContentFromFtl(this.pageTemplate, pageInfo);
	}

}
