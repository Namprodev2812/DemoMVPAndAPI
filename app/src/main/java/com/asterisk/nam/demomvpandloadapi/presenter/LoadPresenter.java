package com.asterisk.nam.demomvpandloadapi.presenter;

import com.asterisk.nam.demomvpandloadapi.base.BasePresenter;
import com.asterisk.nam.demomvpandloadapi.contract.LoadContract;

public class LoadPresenter extends BasePresenter<LoadContract.LoadView> implements LoadContract.LoadPresenter {

    @Override
    public void loadDataInAPI() {
        loadData();
    }

    private void loadData(){

        //getView().loadDataRecyclerSuccess();
        //getView().loadDataRecyclerFail();
    }
}
