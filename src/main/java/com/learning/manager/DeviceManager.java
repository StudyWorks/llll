package com.learning.manager;


import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.domain.Device;

@Service
public class DeviceManager {
	@Autowired
	private ISimpleManager simpleManager;
	
	public Device findByDeviceId(String deviceId){
		return simpleManager.findFirst(Device.class, Restrictions.eq("deviceId", deviceId));
	}
}
