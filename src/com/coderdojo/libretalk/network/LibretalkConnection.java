package com.coderdojo.libretalk.network;

import java.io.IOException;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.coderdojo.libretalk.R;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownSignalException;

public final class LibretalkConnection
{
    public static final String EXCHANGE_NAME = "libretalk.chat.peers";
    public static final String GLOBAL_EXCHANGE_NAME = "libretalk.chat.global";
    public static final String ROUTING_KEY = "libretalk.user.";
    
    private final String host;
    private final String user;
    private final String pass;
    private final String userTag;
    private final Object lock = new Object();
    
    private volatile ConnectionStatus status = ConnectionStatus.NOT_CONNECTED;
    
    private Connection connection;
    private Channel channel;
    
    
    public LibretalkConnection(final String host, final String user, final String pass, final long uid)
    {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.userTag = ROUTING_KEY + Long.toString(uid);
    }
    
    
    public final void connect() throws LibretalkNetworkException
    {
        new ConnectToNetworkTask().execute();

        synchronized (lock)
        {
            while (this.status != ConnectionStatus.CONNECTED)
            {
               
                try
                {
                    lock.wait(5000L);
                    
                    if (this.status == ConnectionStatus.CONNECTION_FAILED)
                    {
                        throw new LibretalkNetworkException("Failed to connect to network! CSTATUS FAILED");
                    }
                }
                catch (InterruptedException ex)
                {
                    throw new LibretalkNetworkException("Failed to connect to network! Connector thread interrupted!", ex);
                }
            }
        }
    }
    
    
    public final Channel getChannel()
    {
        return channel;
    }
    
    
    public final String getUserTag()
    {
        return userTag;
    }
    
    public final ConnectionStatus getStatus()
    {
        return status;
    }
    
    
    public final void close() throws IOException, ShutdownSignalException
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
        
        this.status = ConnectionStatus.NOT_CONNECTED;        
    }
    
    
    public enum ConnectionStatus
    {
        NOT_CONNECTED,
        CONNECTED,
        CONNECTION_FAILED
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
                    fac.setPassword(user);
                    fac.setUsername(pass);
                    fac.setConnectionTimeout(5000);
                    
                    connection = fac.newConnection();
                    channel = connection.createChannel();
                    
                    // Declare the libretalk.chat.global fanout exchange
                    channel.exchangeDeclare(GLOBAL_EXCHANGE_NAME, "fanout");
                    
                    // Declare the libretalk.user.ID queue
                    channel.queueDeclare(userTag, false, true, true, null);
                    
                    // Bind the queue to the exchange
                    channel.queueBind(userTag, GLOBAL_EXCHANGE_NAME, "");
                    
                    
                    Log.d("libretalk::LibretalkConnection", "Set connected to true");
                    
                    synchronized (lock)
                    {
                        status = ConnectionStatus.CONNECTED;
                        
                        lock.notifyAll();
                    }
                }
                catch (IOException ex)
                {
                    Log.e("libretalk::LibretalkConnection", "[==== Failed to connect to libretalkNetwork! ====]");
                    ex.printStackTrace();
                    
                    status = ConnectionStatus.CONNECTION_FAILED;
                    
                    this.cancel(true);
                }             
            }
            
            return null;
        }     
    }
}
