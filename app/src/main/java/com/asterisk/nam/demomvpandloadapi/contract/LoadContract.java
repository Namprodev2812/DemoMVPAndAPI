package com.asterisk.nam.demomvpandloadapi.contract;

import com.asterisk.nam.demomvpandloadapi.base.BasePresenter;
import com.asterisk.nam.demomvpandloadapi.base.BaseView;
import com.asterisk.nam.demomvpandloadapi.model.User;

import java.util.List;

public interface LoadContract {

    interface LoadView extends BaseView {
        void loadDataRecyclerSuccess(List<User> users);
        void loadDataRecyclerFail();
    }

    interface LoadPresenter{
        void loadDataInAPI();
    }
}
