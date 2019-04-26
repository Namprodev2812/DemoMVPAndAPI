package com.asterisk.nam.demomvpandloadapi.presenter;

import com.asterisk.nam.demomvpandloadapi.network.NetworkCallback;
import com.asterisk.nam.demomvpandloadapi.network.RemoteDataSource;
import com.asterisk.nam.demomvpandloadapi.contract.LoadContract;
import com.asterisk.nam.demomvpandloadapi.model.User;
import com.asterisk.nam.demomvpandloadapi.view.MainActivity;

import java.util.List;

public class LoadPresenter implements LoadContract.Presenter, NetworkCallback {

    private LoadContract.View mView;
    private RemoteDataSource mRemoteDataSource = new RemoteDataSource(this);

    public void setView(LoadContract.View view) {
        this.mView = view;
    }

    @Override
    public void handleLoadUser() {
        mRemoteDataSource.execute(MainActivity.URL);
    }

    @Override
    public void cancelAsync() {
        mRemoteDataSource.cancel(true);
    }

    @Override
    public void receiverUsersSuccess(List<User> users) {
        mView.loadUserSuccess(users);
    }

    @Override
    public void receiverUsersFail() {
        mView.loadUserFailure("error load ");
    }
}
