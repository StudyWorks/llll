package com.learning.gateway;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The handler determines what to do with each text line received. In all cases
 * but the stop case it passes on the line to the {@link TextMessageSubscriber}.
 * 
 * See: {@link TextMessageSubscriber}
 * See: Special Stop Command SHUTDOWN_COMMAND
 * 
 * @author ewhite
 */
public class TextMessageHandler extends SimpleChannelUpstreamHandler {

    private static final Logger l = LoggerFactory.getLogger(TextMessageHandler.class.getName());

    /** The special shutdown command */
    private static final String SHUTDOWN_COMMAND = "C|STOP";

    private static ChannelGroup channels = new DefaultChannelGroup("test");

    
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
    		throws Exception {
    	e.getChannel().write("Welcome!\n");
    	channels.add(e.getChannel());
    	super.channelConnected(ctx, e);
    }
    /**
     * At this point the message will be lined delimited and these messages can
     * be directly sent to disruptor for processing.
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        String delimitedMessage = (String) e.getMessage();
        delimitedMessage = delimitedMessage.trim();
        if ("".equals(delimitedMessage))
            return;

        if(l.isTraceEnabled())
            l.trace(delimitedMessage);
//        publish(delimitedMessage);
        for(Channel channel : channels){
        	if(channel.equals(e.getChannel())){
        		channel.write("send success!\n");
        	}else{
        		channel.write(delimitedMessage + "\n");
        	}
        }
    }

   
    /**
     * Finally send the message to the subscriber.
     * 
     * @param delimitedMessage
     */
    private void publish(String delimitedMessage) {
        System.out.println(delimitedMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        l.error("Unexpected exception in the text message gateway.  Closing the channel.", e);
        e.getChannel().close();
    }
}
