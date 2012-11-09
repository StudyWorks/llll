package com.learning.gateway;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.learning.manager.ChannelManager;

public class ChannelCollector extends SimpleChannelUpstreamHandler{
	private ChannelManager channelManager;
	
	public ChannelCollector(ChannelManager channelManager) {
		super();
		this.channelManager = channelManager;
	}

	@Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
    		throws Exception {
		channelManager.add(e.getChannel());
    	super.channelConnected(ctx, e);
    }
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		channelManager.remove(e.getChannel());
		super.channelDisconnected(ctx, e);
	}
}
