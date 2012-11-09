package com.learning.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Device {
	@Id
	@GeneratedValue
	private Long id;
	//业务主键
	private String deviceId;
	
	private Integer collectedChannelId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getCollectedChannelId() {
		return collectedChannelId;
	}

	public void setCollectedChannelId(Integer collectedChannelId) {
		this.collectedChannelId = collectedChannelId;
	}
	
	
}
