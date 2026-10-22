package com.msnegi.serviceexample1;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class BackResults implements Parcelable
{
    int id = 0;
    String name = "";

    public BackResults() {}

    public BackResults(Parcel parcel)
    {
        readFromParcel(parcel);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
    }

    private void readFromParcel(Parcel parcel)
    {
        // We just need to read back each field in the order that it was written to the parcel
        id = parcel.readInt();
        name = parcel.readString();
    }

    //This field is needed for Android to be able to create new objects, individually or as arrays.
    //This also means that you can use the default constructor to create the object and use another
    //method to update it as necessary.

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public BackResults createFromParcel(Parcel in)
        {
            return new BackResults(in);
        }

        public BackResults[] newArray(int size)
        {
            return new BackResults[size];
        }
    };
}
