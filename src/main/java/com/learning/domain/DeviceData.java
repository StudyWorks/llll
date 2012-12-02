package com.learning.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "t_device_data")
public class DeviceData extends BaseEntity<Long>{
	// 分机设备ID
	private String hta;
	// 数据发送时间
	private String tm;
	// 发送的数据次数
	private String cnt;
	// 上传给服务器的数据
	private String dat;
	// 当前电池电压
	private String bat;
	
	public String getHta() {
		return hta;
	}

	public void setHta(String hta) {
		this.hta = hta;
	}

	public String getTm() {
		return tm;
	}

	public void setTm(String tm) {
		this.tm = tm;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getDat() {
		return dat;
	}

	public void setDat(String dat) {
		this.dat = dat;
	}

	public String getBat() {
		return bat;
	}

	public void setBat(String bat) {
		this.bat = bat;
	}
	
	@Transient
	public String getDeviceId(){
		return this.hta;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
