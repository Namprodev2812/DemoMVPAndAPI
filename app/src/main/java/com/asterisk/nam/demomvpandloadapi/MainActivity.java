package com.asterisk.nam.demomvpandloadapi;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.asterisk.nam.demomvpandloadapi.adapter.UserAdapter;
import com.asterisk.nam.demomvpandloadapi.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadContract.View, SwipeRefreshLayout.OnRefreshListener {

    public final static String URL = "https://api.github.com/users";
    public final static String URL_ID = "id";
    public final static String URL_NAME = "login";
    public final static String URL_AVATAR = "avatar_url";
    public final static String URL_USER_KEY = "User_Agent";
    public final static String URL_USER_VALUE = "my-rest-app-v0.1";
    public final static int URL_REQUEST_TIME = 3000;
    public final static int URL_REQUEST_CODE = 200;
    public final static String URL_REQUEST_METHOD = "GET";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mFeelLoading = false;
    private List<User> mListUser;
    private int mLoadMore;

    private LoadPresenter mLoadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initRecyclerView();
        initPresenter();
        initSwipeRefeshLayout();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerview_main);
        mSwipeRefreshLayout = findViewById(R.id.swipe_main);
    }

    private void initPresenter() {
        mLoadPresenter = new LoadPresenter();
        mLoadPresenter.setView(this);


    }

    @Override
    public void loadUserSuccess(List<User> users) {
        mSwipeRefreshLayout.setRefreshing(false);
        loadUser(users);
    }

    @Override
    public void loadUserFailure(String error) {
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    public void initSwipeRefeshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        mListUser = new ArrayList<>();
        mUserAdapter = new UserAdapter(this, mListUser);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mUserAdapter);
        registerListenerScroll();
    }

    private void loadUser(List<User> mListUser) {
        if (mListUser != null) {
            this.mListUser.clear();
            this.mListUser.addAll(mListUser);
            mUserAdapter.notifyDataSetChanged();
        }
    }

    private void registerListenerScroll() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (mFeelLoading == false && linearLayoutManager != null && linearLayoutManager.findLastVisibleItemPosition() == mListUser.size() - 1) {
                    mFeelLoading = true;
                    mLoadMore++;
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        mListUser.add(null);
        mUserAdapter.notifyItemInserted(mListUser.size() - 1);
        loadMoreBody();
    }

    private void loadMoreBody() {
        mListUser.remove(mListUser.size() - 1);
        int scrollPosition = mListUser.size();
        mUserAdapter.notifyItemRemoved(scrollPosition);
        int currentSize = scrollPosition;
        int nextLimit = currentSize + 10;
        while (currentSize - 1 < nextLimit) {
            mListUser.add(new User(1, "mojombo", "https://avatars0.githubusercontent.com/u/1?v=4"));
            currentSize++;
        }
        mUserAdapter.notifyDataSetChanged();
        mFeelLoading = false;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mLoadPresenter.handleLoadUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoadPresenter.cancelAsync();
    }
}
