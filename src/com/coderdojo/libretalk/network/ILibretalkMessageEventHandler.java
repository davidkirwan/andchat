package com.coderdojo.libretalk.network;

/**
 * Handles all events fired by Libretalk Network Components.
 * 
 * @author Liam Power
 * @version 3.3
 *
 */
public interface ILibretalkMessageEventHandler
{
    
    /**
     * Fired when a message is received from {@link LibretalkMessageReceiver}
     * 
     * @param message The message retrieved from the RabbitMQ message queue. This can usually be encoded into a UTF-8 String
     */
    void onMessageReceived(final byte[] message);
}
