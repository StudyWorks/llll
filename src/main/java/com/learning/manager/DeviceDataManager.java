package com.learning.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.learning.gateway.TextMessageSubscriber;

@Service
public class DeviceDataManager implements TextMessageSubscriber{
	@Autowired
	private ChannelManager channelManager;
	
	@Async
	public void accept(String delimitedMessage, Integer channelId) {
		delimitedMessage.split(";|#");
	}
	

}
