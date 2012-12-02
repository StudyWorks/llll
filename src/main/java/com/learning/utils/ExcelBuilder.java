package com.learning.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.google.common.base.Strings;

public class ExcelBuilder {
	HSSFWorkbook wb;
	HSSFSheet sheet;  
	
	public static ExcelBuilder load(String template){
		return new ExcelBuilder(template);
	}
	
	public ExcelBuilder(String template){
		this(template, 0);
	}
	
	public ExcelBuilder(String template, int sheetIndex){
		InputStream inputStream = this.getClass().getResourceAsStream(template);
		try {
			wb = new HSSFWorkbook(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception happend while loading template: " + template, e);
		}finally{
			IOUtils.closeQuietly(inputStream);
		}
		setSheet(sheetIndex);
	}
	
	public ExcelBuilder setSheet(int index){
		sheet = wb.getSheetAt(index);
		return this;
	}
	
	public HSSFRow getRow(int rowIndex){
		HSSFRow row = this.sheet.getRow(rowIndex);
		if(row == null){
			row = this.sheet.createRow(rowIndex);
		}
		return row;
	}
	
	public String getValue(int rowIndex, int columnIndex){
		HSSFRow row = this.sheet.getRow(rowIndex);
		if(row == null){
			return "";
		}
		HSSFCell cell = row.getCell(columnIndex);
		if(cell == null){
			return "";
		}
		return cell.getStringCellValue();
	}
	
	public ExcelBuilder setValue(int rowIndex, int columnIndex, Object value){
		if(value == null)
			return this;
		HSSFRow row = this.sheet.getRow(rowIndex);
		if(row == null){
			row = this.sheet.createRow(rowIndex);
		}
		HSSFCell cell = row.getCell(columnIndex);
		if(cell == null){
			cell = row.createCell(columnIndex);
		}
		setCellValue(cell, value);
		return this;	
	}
	
	void setCellValue(HSSFCell cell, Object value) {
//		if(value instanceof Number){
//			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//		}else if(value instanceof Boolean){
//			cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
//		}else{
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//		}
		if(value != null){ 
			cell.setCellValue(value.toString());
		}
	}
	
	public ExcelBuilder buildHead(int maxRowIndexOfHead, int maxColumnIndexOfHead, Object context){
		for(int i = 0; i < maxRowIndexOfHead; i++){
			HSSFRow row = this.sheet.getRow(i);
			if(row == null)
				continue;
			for(int j = 0; j < maxColumnIndexOfHead; j++){
				HSSFCell cell = row.getCell(j);
				if(cell == null)
					continue;
				String expr = cell.getStringCellValue();
				if(Strings.isNullOrEmpty(expr) || !expr.contains("${"))
					continue;
				Object value = parseValue(expr, context);
				this.setCellValue(cell, value);
			}
		}
		return this;
	}

	private Object parseValue(String expr, Object context) {
		Matcher matcher = pattern.matcher(expr);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			matcher.appendReplacement(sb, matcher.group(1));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	static Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");

	public InputStream getInputStream(){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			this.wb.write(output);
		} catch (IOException e) {
			e.printStackTrace();
			//should not happend
		}
		return new ByteArrayInputStream(output.toByteArray());
	}

	public ExcelBuilder cloneRow(int rowIndex, int targetIndex) {
		HSSFRow srcRow = this.sheet.getRow(rowIndex);
		if(srcRow == null){
			throw new IllegalArgumentException("Can't clone row: " + rowIndex + " because it doesn't exist!");
		}
		HSSFRow targetRow = this.sheet.getRow(targetIndex);
		if(null == targetRow)
			targetRow = this.sheet.createRow(targetIndex);
		int lastCellNum = srcRow.getLastCellNum();
		for(int i = 0; i <= lastCellNum; i++){
			HSSFCell srcCell = srcRow.getCell(i);
			if(srcCell == null)
				continue;
			HSSFCell targetCell = targetRow.getCell(i);
			if(targetCell == null)
				targetCell = targetRow.createCell(i);
			targetCell.setCellStyle(srcCell.getCellStyle());
		}
		return this;
	}
	
	public ExcelBuilder mergeCell(int startRow, int endRow, int startColumn, int endColumn){
		this.sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startColumn, endColumn));
		return this;
	}

}
