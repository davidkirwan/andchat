package com.coderdojo.libretalk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import com.google.gson.Gson;

import android.graphics.Color;
import android.util.Log;

public final class LibretalkMessageData implements Serializable
{
	/**
	 *  [!] AUTO GENERATED DO NOT MODIFY 
	 */
	//private static final long serialVersionUID = 2649285673568863822L;
	@SuppressWarnings("unused")
	private static final byte CLASS_VERSION = 0x01;
	
	
	private final String data;
	private final String senderTag;
	
	public LibretalkMessageData(final String senderTag, final String message)
	{
		this.senderTag = senderTag;
		this.data = message;
	}
	
	public static final int getColorFromString(final String s)
	{		
		String str = s.substring(1, 2);
		Log.d("libretalk::LibretalkMessageData", "Original String: " + s);
		Log.d("libretalk::LibretalkMessageData", "Substring: " + str);
		
		String[] colours = {"#9EB08E", "#9E8EB0", "#AB2E2E", "#C0FF00", "#FFACAC", 
		                 "#699FF0", "#69F0E7", "#FCD04D", "#FCED4D", "#217989", "#892121",
		                 "#FF0000", "#2600FF", "#00FF26", "#FCFF00", "#FFF886", "#D5720F", 
		                 "#B5DC8E", "#8EDCD3", "#4A6CC3", "#CE13DF", "#2B6F9D", "#75E5DE",
		                 "#51C121", "#EC5A32", "#5C4BDC"};
		
		char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		
		int index = 0;
		
		for(int i = 0; i < alphabet.length; i++){
			if(str.equalsIgnoreCase(Character.toString(alphabet[i]))){
				Log.d("libretalk::LibretalkMessageData", "Match found at index: " + index);
				index = i;
			}
		}
		
		Log.d("libretalk::LibretalkMessageData", "Colour Index: " + index);
		Log.d("libretalk::LibretalkMessageData", "Colour: " + colours[index]);
		
		return Color.parseColor(colours[index]);
	}
	

	public String getSenderTag()
	{
		return senderTag;
	}

	public String getData()
	{
		return data;
	}

	
	/*public static final byte[] serialize(final LibretalkMessageData o) throws IOException
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ObjectOutputStream objOut = new ObjectOutputStream(out);
		
		objOut.writeObject(o);
		return out.toByteArray();		
	}*/
	
	public static final String serialize(final LibretalkMessageData msg)
	{
		Gson gson = new Gson();
		return gson.toJson(msg);
	}
	
	
	public static final LibretalkMessageData deserialize(final String json)
	{
		Gson gson = new Gson();
		return gson.fromJson(json, LibretalkMessageData.class);
	}
	
	
	/*public static final LibretalkMessageData deserialize(final byte[] data) throws 
																			StreamCorruptedException, 
																			IOException, 
																			ClassNotFoundException
	{
		final ByteArrayInputStream input = new ByteArrayInputStream(data);
		final ObjectInputStream objInput = new ObjectInputStream(input);
		
		return (LibretalkMessageData) objInput.readObject();
		
	}*/
	
	@Override
	public final String toString()
	{
		return "LibretalkMessage#" + senderTag + "->{" + data + "}_COLOR(" + getColorFromString(senderTag) + ")";
	}
	
}
