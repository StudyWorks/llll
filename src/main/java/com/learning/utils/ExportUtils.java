package com.learning.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.ServletActionContext;

public class ExportUtils {
    
    public static String getExportFileName(String exportFileName) throws UnsupportedEncodingException{     
        String agent = ServletActionContext.getRequest().getHeader("USER-AGENT");
         if (null != agent && !agent.contains("MSIE") && agent.contains("Mozilla")) {
            return "=?UTF-8?B?"
                    + (new String(Base64.encodeBase64(exportFileName.getBytes("UTF-8")))) + "?=";
        } 
         return URLEncoder.encode(exportFileName, "UTF8");
    }     
} 
