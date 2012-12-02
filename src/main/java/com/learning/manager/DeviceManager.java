package com.learning.manager;


import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.domain.Device;
import com.learning.domain.DeviceData;

@Service
public class DeviceManager {
	@Autowired
	private ISimpleManager simpleManager;
	
	public Device findByDeviceId(String deviceId){
		return simpleManager.findFirst(Device.class, Restrictions.eq("deviceId", deviceId));
	}

	public void createOrUpdate(DeviceData data, Integer channelId) {
		String deviceId = data.getDeviceId();
		Device device = findByDeviceId(deviceId);
		if(null == device){
			device = new Device();
			device.setDeviceId(deviceId);
			device.setDataCount(0l);
		}
		device.setLastData(data);
		device.increateDataCount(1);
		device.setChannelId(channelId);
		simpleManager.saveOrUpdate(device);
	}
}
