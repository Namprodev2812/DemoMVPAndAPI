package com.asterisk.nam.demomvpandloadapi.model;

public class User {
    private int mId;
    private String mName;
    private String mAvatar;

    public User() {

    }

    public User(int mId, String mName, String mAvatar) {
        this.mId = mId;
        this.mName = mName;
        this.mAvatar = mAvatar;
    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getFullData() {
        return mId + mName;
    }

    public String getmAvatar() {
        return mAvatar;
    }
}
