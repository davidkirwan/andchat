package com.coderdojo.libretalk.network;

import java.io.IOException;

import android.os.Handler;
import android.util.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * Handles the delivery of new messages from the RabbitMQ queue. 
 * New messages are passed to a {@link ILibretalkMessageEventHandler} for processing.
 * 
 * @author Liam Power
 * @version 3.3
 * 
 */
public final class LibretalkMessageReceiver
{
    /**
     * The connection to a RabbitMQ server.
     */
    private final LibretalkConnection connection;
    
    /**
     * Handles incoming messages.
     */
    private final ILibretalkMessageEventHandler eventHandler;
    
    /**
     * Used to post messages from the Receiver thread to the UI (main) thread.
     */
    private final Handler handler = new Handler();
    
    /**
     * Constructs a basic Message Receiver.
     * 
     * @param connection A connection to a RabbitMQ server.
     * @param eventHandler The handler for incoming messages.
     */
    public LibretalkMessageReceiver(final LibretalkConnection connection, final ILibretalkMessageEventHandler eventHandler)
    {
        this.connection = connection;
        this.eventHandler = eventHandler;
    }
    
    /**
     * Starts up the {@link MessageConsumer} thread.
     */
    public final void startConsuming()
    {        
        try
        {
            connection.getChannel().basicConsume(connection.getUserTag(), false, new MessageConsumer(connection.getChannel()));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * @deprecated 
     *
     * This was originally used to shut down the consumer thread, however this is no longer necessary
     * as RabbitMQ consumers now handle their own threads.
     */
    public final void stopConsuming()
    {
        Log.d("libretalk::LibretalkMessageReceiver", "Stopping consumer...");
    }
    
    
    /**
     * 
     * Used to post messages from the {@link MessageConsumer} thread to the UI (main) thread.
     * 
     */
    private final class PostMessageTask implements Runnable
    {
        private final byte[] msg;
        
        protected PostMessageTask(final byte[] msg)
        {
            this.msg = msg;
        }
        
        @Override
        public void run()
        {
            eventHandler.onMessageReceived(msg);
        }
    }
    
    /**
     * 
     * Handles the delivery and processing of incoming messages.
     * 
     */
    private final class MessageConsumer extends DefaultConsumer
    {    
        protected MessageConsumer(final Channel channel)
        {
            super(channel);
        }
        
        
        
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties props, final byte[] body) 
                throws IOException
        {
            handler.post(new PostMessageTask(body));
            connection.getChannel().basicAck(envelope.getDeliveryTag(), false);
            
            Log.d("libretalk::LibretalkMessageReceiver::MessageConsumer", "Received :" + new String(body));
        }
        
        
        
        @Override
        public void handleShutdownSignal(final String consumerTag, final ShutdownSignalException ex)
        {
            Log.i("libretalk::LibretalkMessageReceiver::MessageConsumer", "Received shutdown signal from "
                    + consumerTag + ": " + ex);
            
            if (ex.isHardError())
            {
                Log.e("libretalk::LibretalkMessageReceiver::MessageConsumer", "[!] Hard error received");
            }
        }
        
    }
}
