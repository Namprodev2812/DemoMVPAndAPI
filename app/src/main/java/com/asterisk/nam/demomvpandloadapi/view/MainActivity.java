package com.asterisk.nam.demomvpandloadapi.view;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.asterisk.nam.demomvpandloadapi.R;
import com.asterisk.nam.demomvpandloadapi.adapter.UserAdapter;
import com.asterisk.nam.demomvpandloadapi.base.BaseActivity;
import com.asterisk.nam.demomvpandloadapi.base.BaseView;
import com.asterisk.nam.demomvpandloadapi.contract.HandlesLoadData;
import com.asterisk.nam.demomvpandloadapi.contract.LoadContract;
import com.asterisk.nam.demomvpandloadapi.model.User;
import com.asterisk.nam.demomvpandloadapi.network.CreateAsyncTask;
import com.asterisk.nam.demomvpandloadapi.presenter.LoadPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<LoadPresenter> implements LoadContract.LoadView,SwipeRefreshLayout.OnRefreshListener, HandlesLoadData {

    public final static String URL = "https://api.github.com/users";
    public final static String URL_ID = "id";
    public final static String URL_NAME = "login";
    public final static String URL_AVATAR = "avatar_url";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean feel;
    private boolean mFeelLoading = false;
    private List<User> mListUser, mSubListUser;
    private int mLoadMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        createUser();
        createSwipeRefeshLayout();
        getPresenter().loadDataInAPI();
    }

    @Override
    public void loadDataRecyclerSuccess(List<User> users) {

    }

    @Override
    public void loadDataRecyclerFail() {

    }
    public void initViews() {
        mRecyclerView = findViewById(R.id.recyclerview_main);
        mSwipeRefreshLayout = findViewById(R.id.swipe_main);
    }

    public void createUser() {
        CreateAsyncTask createAsyncTask = new CreateAsyncTask(this);
        createAsyncTask.execute(URL);
    }

    public void createSwipeRefeshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void createRecyclerView(List<User> mListUser) {
        feel = true;
        if (mListUser != null) {

            this.mListUser = new ArrayList<>();
            this.mListUser.addAll(mListUser);
            mUserAdapter = new UserAdapter(this, this.mListUser);
            mLinearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setAdapter(mUserAdapter);
            registerListenerScroll();
        }
    }

    public void registerListenerScroll() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                Log.e("MainActivity", mFeelLoading+"---"+"Size= " + (mListUser.size() - 1) + "--- findlast = " + linearLayoutManager.findLastVisibleItemPosition());
                if (mFeelLoading == false && linearLayoutManager != null && linearLayoutManager.findLastVisibleItemPosition() == mListUser.size() - 1) {
                    mFeelLoading = true;
                    mLoadMore++;
                    Toast.makeText(MainActivity.this, "LoadMore: " + mLoadMore, Toast.LENGTH_SHORT).show();
                    loadMore();
                }
            }
        });
    }

    public void loadMore() {

        mListUser.add(null);
        mUserAdapter.notifyItemInserted(mListUser.size() - 1);

        /*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 5000);
        */
        loadMoreBody();
    }

    public void loadMoreBody() {
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

    public void loadMoreFake() {
        //mListUser.add(null);
        //mUserAdapter.notifyItemInserted(mListUser.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mListUser.remove(mListUser.size() - 1);
                int scrollPosition = mListUser.size();
                //mUserAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;
                while (currentSize - 1 < nextLimit) {
                    mListUser.add(new User(1, "mojombo", "https://avatars0.githubusercontent.com/u/1?v=4"));
                    mListUser.add(new User(2, "defunkt", "https://avatars0.githubusercontent.com/u/2?v=4"));
                    currentSize++;
                }
                mSubListUser = new ArrayList<>();
                mSubListUser.addAll(mListUser);
                mListUser.clear();
                mListUser.addAll(mSubListUser);
                mUserAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(mListUser.size() - 1);
                mFeelLoading = false;
            }
        }, 5000);
    }

    @Override
    public void onRefresh() {

        //mListUser.clear();
        //mListUser.addAll(mListSubPersons);
        //mRecyclerView.getAdapter().notifyDataSetChanged();
        //mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void update(List<User> mListUser) {
        mSwipeRefreshLayout.setRefreshing(false);
        createRecyclerView(mListUser);
    }
}
