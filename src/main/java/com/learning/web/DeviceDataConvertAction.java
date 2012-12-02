package com.learning.web;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.learning.domain.DeviceData;
import com.learning.manager.DeviceDataParser;
import com.learning.utils.ExcelBuilder;
import com.learning.utils.ExportUtils;
@InterceptorRef("fileUploadStack")
@Result( type = "stream", params = { "inputName",
        "inputStream", "contentType", "application/xls", "contentDisposition",
        "attachment; filename=${exportFileName}" })
public class DeviceDataConvertAction extends ActionBase{
	@Autowired
	private DeviceDataParser deviceDataParser;
	private File txtFile;
	private InputStream inputStream;
	
	@Override
	protected void doExecute() throws Exception {
		ExcelBuilder excelBuilder = ExcelBuilder.load("/template/device_data.xls");
		 List<String> deviceDatas = FileUtils.readLines(txtFile);
        for(int index = 0, length = deviceDatas.size(); index < length; index++){
            String rawData = deviceDatas.get(index);
			DeviceData deviceData = deviceDataParser.parse(rawData);
            if(deviceData == null){
            	logger.warn("can't parse data: {}", rawData);
            	continue;
            }
            int rowIndex = 3 + index;
            int columnIndex = 1;
            boolean isLast = index == length - 1;
            if(!isLast){
                excelBuilder.cloneRow(rowIndex, rowIndex + 1);
            }
            excelBuilder.setValue(rowIndex, columnIndex++, deviceData.getHta())
                    .setValue(rowIndex, columnIndex++, deviceData.getCnt())
                    .setValue(rowIndex, columnIndex++, deviceData.getDat())
                    .setValue(rowIndex, columnIndex++, deviceData.getBat())
                    .setValue(rowIndex, columnIndex++, deviceData.getTm());
        }
        inputStream = excelBuilder.getInputStream();
	}
	
	public void setTxtFile(File txtFile) {
		this.txtFile = txtFile;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public String getExportFileName() throws UnsupportedEncodingException{
		return ExportUtils.getExportFileName("设备数据明细.xls");
	}
}
