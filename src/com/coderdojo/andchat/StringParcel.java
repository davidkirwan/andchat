package com.coderdojo.andchat;

import android.os.Parcel;
import android.os.Parcelable;

public class StringParcel implements Parcelable {

	private String theString;
	
	public StringParcel(String newString)
	{
		this.theString = newString;
	}
	
	private StringParcel(Parcel in) {
		this.theString = in.readString();
    }

	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeString(this.theString);
	}
	
	@Override
    public String toString() {
        return this.theString;
    }


    public static final Parcelable.Creator<StringParcel> CREATOR = new Parcelable.Creator<StringParcel>() {
        public StringParcel createFromParcel(Parcel in) {
            return new StringParcel(in);
        }

        public StringParcel[] newArray(int size) {
            return new StringParcel[size];
        }
    };

}
