package com.learning.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_device")
public class Device extends BaseEntity<Integer>{
	//业务主键
	private String deviceId;
	
	private Integer channelId;
	
	private Long dataCount;
	
	@ManyToOne
	private DeviceData lastData;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Long getDataCount() {
		return dataCount;
	}

	public void setDataCount(Long dataCount) {
		this.dataCount = dataCount;
	}

	public DeviceData getLastData() {
		return lastData;
	}

	public void setLastData(DeviceData lastData) {
		this.lastData = lastData;
	}

	public void increateDataCount(int i) {
		if(this.dataCount == null)
			this.dataCount = 0l;
		this.dataCount += i;
	}
	
}
