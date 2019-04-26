package com.asterisk.nam.demomvpandloadapi.network;

import com.asterisk.nam.demomvpandloadapi.model.User;

import java.util.List;

public interface NetworkCallback {
    void receiverUsersSuccess(List<User> users);
    void receiverUsersFail();
    void cancelAsync();
}
