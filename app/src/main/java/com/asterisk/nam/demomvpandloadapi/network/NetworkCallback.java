package com.asterisk.nam.demomvpandloadapi.network;

import com.asterisk.nam.demomvpandloadapi.model.Song;
import com.asterisk.nam.demomvpandloadapi.model.User;

import java.util.List;

public interface NetworkCallback {
    void receiverUsersSuccess(List<Song> songs);
    void receiverUsersFail();
    void cancelAsync();
}
