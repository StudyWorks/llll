package com.learning.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.learning.domain.DeviceData;
import com.learning.domain.RawData;
import com.learning.gateway.TextMessageSubscriber;

@Service
public class DeviceDataManager implements TextMessageSubscriber {
	@Autowired
	private DeviceDataParser deviceDataParser;
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private ISimpleManager simpleManager;

	@Async
	public void accept(String delimitedMessage, Integer channelId) {
		saveAsRawData(delimitedMessage);
		if(!delimitedMessage.startsWith("#HTA"))
			return;
		DeviceData deviceData = deviceDataParser.parse(delimitedMessage);
		simpleManager.save(deviceData);
		deviceManager.createOrUpdate(deviceData, channelId);
	}
	
	private void saveAsRawData(String delimitedMessage) {
		RawData rawData = new RawData();
		rawData.setData(delimitedMessage);
		simpleManager.save(rawData);
	}

	public List<DeviceData> findAll(){
		return simpleManager.findAll(DeviceData.class);
	}
}
