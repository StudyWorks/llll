package com.learning.gateway;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
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

    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);

    private TextMessageSubscriber textMessageSubscriber;
    
    public TextMessageHandler(TextMessageSubscriber textMessageSubscriber) {
    	this.textMessageSubscriber = textMessageSubscriber;
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
        logger.debug("messageReceived: {}", delimitedMessage);
        textMessageSubscriber.accept(delimitedMessage, e.getChannel().getId());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    	logger.error("Unexpected exception in the text message gateway.  Closing the channel.", e);
        e.getChannel().close();
    }
}
