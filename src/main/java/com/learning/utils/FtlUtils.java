package com.learning.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * FTL模板工具
 * 
 * @author baiye
 */
public class FtlUtils {
	private static final Logger log = LoggerFactory
			.getLogger(FtlUtils.class);
	private static Configuration cfg = new Configuration();
	static {
		try {
			cfg.setEncoding(Locale.getDefault(), "UTF-8");
			cfg.setClassForTemplateLoading(FtlUtils.class, "/");
		} catch (Exception e) {
			log.warn("freemark模板引擎加载失败！", e);
		}
		log.info("freemark模板引擎加载成功！");
	}

	/**
	 * 将FTL模板内容转换为字符串
	 */
	public static String getContentFromFtl(String ftl, Object values) {
		Writer writer = null;
		try {
			Template template = cfg.getTemplate(ftl);
			writer = new StringWriter();
			template.process(values, writer);
			return writer.toString();
		} catch (Exception e) {
			log.warn("从模板中取得内容异常！", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	public static String getContentFromStrTemplate(String strTemplate, Object context) {
		try {
			Template template = new Template(null,
					new StringReader(strTemplate), cfg);
			StringWriter out = new StringWriter();
			template.process(context, out);
			return out.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("", e);
		}
	}
}
