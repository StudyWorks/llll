package com.learning.web;

import com.learning.components.query.criteria.CriteriaQuery;
import com.learning.domain.DeviceData;
public class DeviceDataIndexAction extends QueryActionSupport<DeviceData>{
	private String deviceId;

	@Override
	protected void populateCriteriaQuery(CriteriaQuery query) {
		query.eq("hta", deviceId);
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
