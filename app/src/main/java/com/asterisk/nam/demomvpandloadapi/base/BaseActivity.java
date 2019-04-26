package com.asterisk.nam.demomvpandloadapi.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    public T presenter;
    public T getPresenter(){
        return presenter;
    }


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initParameter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    public void initParameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
        String parameterClassName = pt.getActualTypeArguments()[0].toString().split("\\s")[1];
        try {
            this.presenter = (T) Class.forName(parameterClassName).getConstructor().newInstance();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        this.presenter.setView(this);
    }
}
