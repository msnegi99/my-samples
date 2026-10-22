package com.msnegi.parampassing.parcelable_array;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    int id = 0;
    String name = "";

    public Student() {
    }

    public Student(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parsel, int flags) {
        parsel.writeInt(id);
        parsel.writeString(name);
    }

    private void readFromParcel(Parcel parcel) {
        // We just need to read back each field in the order that it was written to the parcel
        id = parcel.readInt();
        name = parcel.readString();
    }

    //This field is needed for Android to be able to create new objects, individually or as arrays.
    //This also means that you can use the default constructor to create the object and use another
    //method to update it as necessary.
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Student createFromParcel(Parcel parsel) {
            return new Student(parsel);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}