package com.learning.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_raw_data")
public class RawData extends BaseEntity<Long> {
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
