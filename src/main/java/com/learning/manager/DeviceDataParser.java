package com.learning.manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.learning.domain.DeviceData;

@Service
public class DeviceDataParser {
	
	//"#HTA:3901;TM:12/08/12,10:18:02;CNT:0001;DAT:%3.987; BAT:3.62#";
	public DeviceData parse(String message){
		DeviceData deviceData = new DeviceData();
		String pattern = "([^#;]+:[^#;]+)";
		Matcher matcher = Pattern.compile(pattern).matcher(message);
		while(matcher.find()){
				String group = matcher.group(0);
				String[] splits = group.split(":", 2);
				String name = splits[0].trim();
				String value = splits[1];
				if(name.equals("HTA")){
					deviceData.setDeviceId(value);
				}else if(name.equals("TM")){
					
				}
		}
		return deviceData;
	}
}
