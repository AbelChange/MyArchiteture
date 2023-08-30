package com.ablec.myarchitecture.data;

import android.os.Parcel;
import android.os.Parcelable;

public class AidlData implements Parcelable {

    private String name;
    private int age;


    public AidlData() {
    }

    public AidlData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public AidlData(Parcel source) {
        this.name = source.readString();
        this.age = source.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Creator<AidlData> CREATOR = new Creator<AidlData>() {
        @Override
        public AidlData createFromParcel(Parcel source) {
            return new AidlData(source);
        }

        @Override
        public AidlData[] newArray(int size) {
            return new AidlData[size];
        }
    };

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


    public void readFromParcel(Parcel reply) {
        AidlData data = CREATOR.createFromParcel(reply);
        this.name =  data.name;
        this.age = data.age;
    }
}
