package com.learning.gateway;

/**
 * The implementor will receive the text message on a Netty
 * thread as they arrive.
 * 
 * @author ewhite
 */
public interface TextMessageSubscriber {
    /**
     * @param delimitedMessage
     * @param channelId 
     */
    public void accept(String delimitedMessage, Integer channelId);
}
