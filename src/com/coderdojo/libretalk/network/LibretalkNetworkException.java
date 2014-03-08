package com.coderdojo.libretalk.network;

public final class LibretalkNetworkException extends Exception
{
    private static final long serialVersionUID = 116047751564973062L;
    
    public LibretalkNetworkException(final String message) { super(message); }
    public LibretalkNetworkException(final Throwable cause) { super(cause); }
    public LibretalkNetworkException(final String message, final Throwable cause) { super(message, cause); }
    public LibretalkNetworkException() { super();}

}

