package com.coderdojo.libretalk.netwk;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

public final class LibreTalkNetwork implements AutoCloseable
{
    private static final String QUEUE = "libre_talk";

    private final Connection connection;
    private final Channel channel;
    private final QueueingConsumer consumer;

    public LibreTalkNetwork(final String host) throws IOException
    {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        this.channel.queueDeclare(QUEUE, false, false, false, null);

        this.consumer = new QueueingConsumer(this.channel);

    }

    public final void send(final String msg) throws IOException
    {
        this.channel.basicPublish("", QUEUE, null, msg.getBytes());
    }




    @Override
    public void close() throws Exception
    {
        this.channel.close();
        this.connection.close();
    }
}
