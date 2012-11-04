package com.gprs.repository;

import org.springframework.data.repository.CrudRepository;

import com.gprs.model.DeviceData;

public interface DeviceDataRepository extends CrudRepository<DeviceData, Long>{
	
}
