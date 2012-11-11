package com.learning.manager;

import java.util.Iterator;
import java.util.Set;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.springframework.stereotype.Service;

@Service
public class ChannelManager {
	private ChannelGroup channels = new DefaultChannelGroup("all_channels");
	
	public Channel findById(Integer channelId){
		return channels.find(channelId);
	}

	public void add(Channel channel) {
		channels.add(channel);
	}

	public void remove(Channel channel) {
		channels.remove(channel);
	}
	
	
	public Set<Channel> findAll(){
		return channels;
	}

}
