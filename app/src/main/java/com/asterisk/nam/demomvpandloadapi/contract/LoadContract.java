package com.asterisk.nam.demomvpandloadapi.contract;

import android.content.Context;

import com.asterisk.nam.demomvpandloadapi.model.User;

import java.util.List;

public interface LoadContract {

    interface View {
        void loadUserSuccess(List<User> users);
        void loadUserFailure(String error);
    }

    interface Presenter {
        void handleLoadUser();
        void cancelAsync();
    }
}
