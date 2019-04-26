package com.asterisk.nam.demomvpandloadapi.presenter;

import com.asterisk.nam.demomvpandloadapi.model.Song;
import com.asterisk.nam.demomvpandloadapi.network.NetworkCallback;
import com.asterisk.nam.demomvpandloadapi.network.RemoteDataSource;
import com.asterisk.nam.demomvpandloadapi.contract.LoadContract;
import com.asterisk.nam.demomvpandloadapi.model.User;
import com.asterisk.nam.demomvpandloadapi.view.MainActivity;

import java.util.List;

public class LoadPresenter implements LoadContract.Presenter, NetworkCallback {

    private LoadContract.View mView;
    private RemoteDataSource mRemoteDataSource;

    public void setView(LoadContract.View view) {
        this.mView = view;
    }

    @Override
    public void handleLoadUser() {
        mRemoteDataSource = new RemoteDataSource(this);
        mRemoteDataSource.execute(MainActivity.URL);
    }

    @Override
    public void cancelAsync() {
        mRemoteDataSource.cancel(true);
    }

    @Override
    public void receiverUsersSuccess(List<Song> songs) {
        mView.loadUserSuccess(songs);
    }

    @Override
    public void receiverUsersFail() {
        mView.loadUserFailure("error load ");
    }
}
