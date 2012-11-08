package com.learning.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.learning.BaseTestCase;
import com.learning.domain.DeviceData;

public class SimpleManagerTest extends BaseTestCase{
	@Autowired
	private ISimpleManager simpleManager;
	

	@Test
	public void findAll(){
		DeviceData dd = new DeviceData();
		dd.setDeviceId("1234");
		simpleManager.save(dd);
		List<DeviceData> allDeviceData = simpleManager.findAll(DeviceData.class);
		System.out.println(allDeviceData.size());
	}
}
