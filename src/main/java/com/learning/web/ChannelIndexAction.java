package com.learning.web;

import java.util.Set;

import org.jboss.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;

import com.learning.manager.ChannelManager;
import com.opensymphony.xwork2.ActionSupport;

public class ChannelIndexAction extends ActionSupport{
	@Autowired
	private ChannelManager channelManager;
	
	public Set<Channel> getChannels(){
		return channelManager.findAll();
	}
	
}
