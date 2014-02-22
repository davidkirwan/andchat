package com.coderdojo.libretalk.network;

import java.io.IOException;

import android.os.AsyncTask;

public final class LibretalkMessageSender
{
    private final LibretalkConnection connection;
    
    public LibretalkMessageSender(final LibretalkConnection connection)
    {
        this.connection = connection;
    }
    
    public final void send(final byte[] message, final String key)
    {
       new SendMessageTask(message, key).execute(); 
    }
    
    private final class SendMessageTask extends AsyncTask<Void, Void, Void>
    {
        private final byte[] message;
        private final String key;
        
        protected SendMessageTask(final byte[] message, final String key)
        {
            this.message = message;
            this.key = key;
        }

        @Override
        protected Void doInBackground(Void... noParams)
        {
            try
            {
                connection.getChannel().basicPublish(LibretalkConnection.EXCHANGE_NAME, key, null, message);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
        
        
    }
    
    
    
}
