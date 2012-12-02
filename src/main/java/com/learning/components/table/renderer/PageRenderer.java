package com.learning.components.table.renderer;

import java.util.HashMap;
import java.util.Map;

import com.learning.components.table.IPageInfo;
import com.learning.utils.FtlUtils;

public class PageRenderer implements IPageRenderer{
	private String pageTemplate = "/template/pagination/simple.ftl";
	private static String headTemplate = "/template/table/head.ftl";
	@Override
	public String render(IPageInfo pageInfo) {
		return FtlUtils.getContentFromFtl(this.pageTemplate, pageInfo);
	}
	
	static Map<String, Object> newMap(Object...args){
		Map<String, Object> result = new HashMap<String, Object>();
		for(int i = 0; i < args.length; i += 2){
			result.put(args[i].toString(), args[i+1]);
		}
		return result;
	}
	public static void main(String[] args) {
		testHead();
		//testPage();
//		render(pageInfo,  1);
//		render(pageInfo, 20);
//		render(pageInfo, 80);
//		render(pageInfo, 98);
//		render(pageInfo, 100);
	}

	protected static void testHead() {
		HashMap<String, Object> context = new HashMap<String, Object>();
		Map<String, Object> column = newMap("title", "标题",  "sortable", true, "width", "100%", "cssClass", "xxxx", "style", "width:100%;");
		context.put("column", column);
		Map<String, Object> pageInfo = newMap("sortColumn", "title", "property", "title", "sortAsc", true, "baseLink", "sss", "currentPage", 1);
		context.put("pageInfo", pageInfo);
		String result = FtlUtils.getContentFromFtl(headTemplate, context);
		System.out.println(result);
	}
	protected static String testPage() {
		Map<String, Object> pageInfo = newMap("currentPage",  5, "baseLink", "http://www.google.com", "totalPages", 20, "sortColumn", "title", "sortAsc", true);
		long startTime = System.currentTimeMillis();
		String content = FtlUtils.getContentFromFtl("/template/pagination/simple.ftl", pageInfo);
		System.out.println(System.currentTimeMillis() - startTime);
		System.out.println(content);
		return content;
	}

}
