package com.learning.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.learning.domain.DeviceData;
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
		if(!delimitedMessage.startsWith("#HTA"))
			return;
		DeviceData deviceData = deviceDataParser.parse(delimitedMessage);
		simpleManager.save(deviceData);
		deviceManager.createOrUpdate(deviceData.getDeviceId(), channelId);
	}

}
