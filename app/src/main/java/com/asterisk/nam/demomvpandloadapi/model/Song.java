package com.asterisk.nam.demomvpandloadapi.model;

public class Song {
    private int mId;
    private String mTitle;
    private String mAvatar;
    private int mScore;

    public int getScore() {
        return mScore;
    }

    public Song(int id, String title,int score) {
        mId = id;
        mTitle = title;
        mScore = score;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAvatar() {
        return mAvatar;
    }
}
