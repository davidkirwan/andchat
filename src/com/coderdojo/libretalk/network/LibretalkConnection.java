package com.coderdojo.libretalk.network;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public final class LibretalkConnection
{
    public static final String EXCHANGE_NAME = "libretalk.chat.peers";
    public static final String GLOBA_EXCHANGE_NAME = "libretalk.chat.global";
    
    private final String host;
    private final String userTag;
    private final Object lock = new Object();
    
    private volatile boolean connected;
    
    private Connection connection;
    private Channel channel;
    
    
     
    
    public LibretalkConnection(final String host, final long uid)
    {
        this.host = host;
        this.userTag = "libretalk.user." + Long.toString(uid);
    }
    
    
    public final void connect()
    {
        new ConnectToNetworkTask().execute();
        
        synchronized (lock)
        {
            while (!connected)
            {
                Log.d("libretalk::LibretalkConnection", "Waiting for connection...");
                try
                {
                    lock.wait();
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    
    public final Channel getChannel()
    {
        return channel;
    }
    
    
    public final boolean isConnected()
    {
        return connected;
    }
    
    
    public final String getUserTag()
    {
        return userTag;
    }
    
    
    public final void close() throws IOException
    {        
        if (this.channel != null)
        {
            this.channel.close();
            Log.d("libretalk::LibretalkConnection", "Closed LibretalkConnection#channel");
        }
        
        if (this.connection != null)
        {
            this.connection.close();
            Log.d("libretalk::LibretalkConnection", "Closed LibretalkConnection#connection");
        }
        
    }
    
    
    
    
    private final class ConnectToNetworkTask extends AsyncTask<Void, Void, Void>
    {   
        @Override
        protected Void doInBackground(Void... noParams)
        {
            Thread.currentThread().setName("libretalkNetworkConnector");
            
            if (channel != null && channel.isOpen())
            {
                return null;
            }
            else
            {
                try
                {
                    final ConnectionFactory fac = new ConnectionFactory();
                    fac.setHost(host);
                    
                    connection = fac.newConnection();
                    channel = connection.createChannel();
                    
                    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
                    channel.queueDeclare(userTag, false, true, true, null);
                    channel.queueBind(userTag, EXCHANGE_NAME, userTag);
                    
                    Log.d("libretalk::LibretalkConnection", "Set connected to true");
                    
                    synchronized (lock)
                    {
                        connected = true;
                        lock.notifyAll();
                    }
                }
                catch (IOException ex)
                {
                    Log.e("libretalk::LibretalkConnection", "[==== Failed to connect to libretalkNetwork! ====]");
                    ex.printStackTrace();
                    
                    this.cancel(true);
                }             
            }
            
            return null;
        }     
    }
}
