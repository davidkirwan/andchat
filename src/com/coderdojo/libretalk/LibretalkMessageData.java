package com.coderdojo.libretalk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import android.graphics.Color;

public final class LibretalkMessageData implements Serializable
{
	/**
	 *  [!] AUTO GENERATED DO NOT MODIFY 
	 */
	private static final long serialVersionUID = 2649285673568863822L;
	
	
	private final String data;
	private final String senderTag;
	private final int color;
	
	public LibretalkMessageData(final String senderTag, final String message)
	{
		this.senderTag = senderTag;
		this.data = message;
		this.color = getColorFromString(senderTag);
	}
	
	private static final int getColorFromString(final String s)
	{
		final int c = s.hashCode();
		
		final int r = (c << 16) & 0xFF; 
		final int g = (c << 8)  & 0xFF;
		final int b = c & 0xFF;
		
		return Color.rgb(r, g, b);
	}
	

	public String getSenderTag()
	{
		return senderTag;
	}

	public int getColor()
	{
		return color;
	}

	public String getData()
	{
		return data;
	}

	
	public static final byte[] serialize(final LibretalkMessageData o) throws IOException
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ObjectOutputStream objOut = new ObjectOutputStream(out);
		
		objOut.writeObject(o);
		return out.toByteArray();		
	}
	
	public static final LibretalkMessageData deserialize(final byte[] data) throws 
																			StreamCorruptedException, 
																			IOException, 
																			ClassNotFoundException
	{
		final ByteArrayInputStream input = new ByteArrayInputStream(data);
		final ObjectInputStream objInput = new ObjectInputStream(input);
		
		return (LibretalkMessageData) objInput.readObject();
		
	}
	
	@Override
	public final String toString()
	{
		return "LibretalkMessage#" + senderTag + "->{" + data + "}_COLOR(" + color + ")";
	}
	
}
