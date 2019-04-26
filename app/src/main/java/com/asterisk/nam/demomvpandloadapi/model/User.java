package com.asterisk.nam.demomvpandloadapi.model;

public class User {
    private int mId;
    private String mName;
    private String mAvatar;

    public User(int id, String name, String avatar) {
        mId = id;
        mName = name;
        mAvatar = avatar;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }
}
