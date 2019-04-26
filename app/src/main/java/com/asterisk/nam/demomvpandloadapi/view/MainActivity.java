package com.asterisk.nam.demomvpandloadapi.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.asterisk.nam.demomvpandloadapi.Config;
import com.asterisk.nam.demomvpandloadapi.R;
import com.asterisk.nam.demomvpandloadapi.adapter.UserAdapter;
import com.asterisk.nam.demomvpandloadapi.contract.LoadContract;
import com.asterisk.nam.demomvpandloadapi.model.Song;
import com.asterisk.nam.demomvpandloadapi.model.User;
import com.asterisk.nam.demomvpandloadapi.presenter.LoadPresenter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements LoadContract.View, View.OnClickListener {

    public final static String URL = "https://api-v2.soundcloud.com/charts?kind=top&genre=soundcloud%3Agenres%3Aall-music&client_id=e534ffbc2d474446c8538d23b1c7605c&limit=20&offset=0";
    public final static String URL_DOWNLOAD = "https://api.soundcloud.com/tracks/259412502/download?client_id=e534ffbc2d474446c8538d23b1c7605c";
    public final static String URL_STREAM_NOID = "https://api.soundcloud.com/tracks/";
    public final static String URL_STREAM_CLIENT = "/stream?client_id=";
    public final static String URL_CLIENT = "e534ffbc2d474446c8538d23b1c7605c";
    public final static String URL_ID = "id";
    public final static String URL_TITLE = "title";
    public final static String URL_COLLECTION = "collection";
    public final static String URL_TRACK = "track";
    public final static String URL_SCORE = "score";
    public final static String URL_AVATAR = "avatar_url";
    public final static String URL_USER_KEY = "User_Agent";
    public final static String URL_USER_VALUE = "my-rest-app-v0.1";
    public final static int URL_REQUEST_TIME = 3000;
    public final static int URL_REQUEST_CODE = 200;
    public final static String URL_REQUEST_METHOD = "GET";

    private Animation mAnimation, mAnimationNot;
    private TextView mTextView;
    private ImageView mImageViewPlayPause, mImageViewPlayNext, mImageViewPlayBack, mImageViewShup, mImageViewRandom, mImageViewCD,mImageViewDownload;
    private SeekBar mSeekBar;
    private UserAdapter mSongAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static int mFeelClick = 0;
    private boolean mFeelShup = false;
    private boolean mFeelprepare = false;
    private int mFeelPlay = 0;
    private List<Song> mListSong;
    private static int position = 0;
    private String sub_URL;
    private int mLoadMore;

    private LoadPresenter mLoadPresenter;
    private static MediaPlayer mediaPlayer;

    private final static String TAG_MEDIA = "tag";
    private final static String CHANNEL_ID = "android8";
    private final static int NOTIFICATION_ID = 69;
    private final static String BUTTON_BACK_TITLE = "back";
    private final static String BUTTON_NEXT_TITLE = "next";
    private final static String BUTTON_PAUSE_TITLE = "pause";
    private NotificationManager mNotificationManager;
    private Notification mBuilder;
    private NotificationCompat.Builder mBuilderC;
    private MediaSession mMediaSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initPresenter();
        registerListener();
    }

    public void addSong(List<Song> listSong) {
        position = 0;
        this.mListSong = listSong;
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("resultID", "" + mListSong.get(position).getId());
                mFeelprepare = true;
                if (mFeelClick == 0) {
                    playMp3();
                } else if (mFeelClick == -1) {
                    playBackMp3();
                } else if (mFeelClick == 1) {
                    playNextMp3();
                }
            }
        });
    }

    public void setMediaPlayer() {
        if (mFeelPlay == 0) {
            //Log.e("mFeelPlay", "" + mFeelPlay + "---" + position + "---" + mListSong.size());

            if (position >= 0) {
                try {
                    sub_URL = MainActivity.URL_STREAM_NOID + mListSong.get(position).getId() + MainActivity.URL_STREAM_CLIENT + MainActivity.URL_CLIENT;
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(MainActivity.URL_STREAM_NOID + mListSong.get(position).getId() + MainActivity.URL_STREAM_CLIENT + MainActivity.URL_CLIENT);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (position < 0) {
                position = 0;
                try {
                    sub_URL = MainActivity.URL_STREAM_NOID + mListSong.get(position).getId() + MainActivity.URL_STREAM_CLIENT + MainActivity.URL_CLIENT;
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(MainActivity.URL_STREAM_NOID + mListSong.get(position).getId() + MainActivity.URL_STREAM_CLIENT + MainActivity.URL_CLIENT);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (position > mListSong.size() - 1) {
                position = 0;
                try {
                    sub_URL = MainActivity.URL_STREAM_NOID + mListSong.get(position).getId() + MainActivity.URL_STREAM_CLIENT + MainActivity.URL_CLIENT;
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(MainActivity.URL_STREAM_NOID + mListSong.get(position).getId() + MainActivity.URL_STREAM_CLIENT + MainActivity.URL_CLIENT);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (mFeelPlay == 1) {
            //Log.e("mFeelPlay", "" + mFeelPlay + "----" + resultIntRandom(mListSong) + "---" + mListSong.size());
            try {
                sub_URL = MainActivity.URL_STREAM_NOID + mListSong.get(position).getId() + MainActivity.URL_STREAM_CLIENT + MainActivity.URL_CLIENT;
                mediaPlayer.reset();
                mediaPlayer.setDataSource(MainActivity.URL_STREAM_NOID + mListSong.get(resultIntRandom(mListSong)).getId() + MainActivity.URL_STREAM_CLIENT + MainActivity.URL_CLIENT);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (mFeelPlay == 3) {
            //Log.e("mFeelPlay", "" + mFeelPlay + "---" + "---" + mListSong.size());
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(sub_URL);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public int resultIntRandom(List<Song> mListSong) {
        Random random = new Random();
        return random.nextInt(mListSong.size());
    }

    public void playMp3() {
        if (mediaPlayer != null & mFeelprepare == true) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mImageViewPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                offAnimation();
            } else {
                loadAnimation();
                setSeekBar(mediaPlayer.getDuration());
                mediaPlayer.start();
                registerListenerSeekBar();
                mImageViewPlayPause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                updateTextTime();
            }
        }
    }

    public void playNextMp3() {

        if (mediaPlayer != null & mFeelprepare == true) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mImageViewPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
            } else {
                setSeekBar(mediaPlayer.getDuration());
                mediaPlayer.start();
                registerListenerSeekBar();
                mImageViewPlayPause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                updateTextTime();
            }
        }
    }

    public void playBackMp3() {

        if (mediaPlayer != null & mFeelprepare == true) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mImageViewPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
            } else {
                setSeekBar(mediaPlayer.getDuration());
                mediaPlayer.start();
                registerListenerSeekBar();
                mImageViewPlayPause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                updateTextTime();
            }
        }
    }

    public void playShup() {

        if (mFeelShup == false) {
            mImageViewShup.setImageResource(R.drawable.ic_repeat_yellow_24dp);
            mFeelShup = true;
            mFeelPlay = 3;
        } else {
            mImageViewShup.setImageResource(R.drawable.ic_repeat_black_24dp);
            mFeelShup = false;
            mFeelPlay = 0;
        }
    }

    public void playRandom() {

        if (mFeelShup == false) {
            mFeelPlay = 1;
            mImageViewRandom.setImageResource(R.drawable.ic_all_inclusive_yellow_24dp);
            mFeelShup = true;
        } else {
            mImageViewRandom.setImageResource(R.drawable.ic_all_inclusive_black_24dp);
            mFeelShup = false;
            mFeelPlay = 0;
        }
    }

    public void updateTextTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setTimeNow();
                mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        setMediaPlayer();
                        mFeelClick = 1;
                    }
                });
                handler.postDelayed(this, 1000);
            }
        }, 100);
    }

    public void setTimeFirst(MediaPlayer mediaPlayer) {
        mTextView.setText(simpleDateFormat(mediaPlayer.getDuration()) + "");
    }

    public void setTimeNow() {
        mTextView.setText(simpleDateFormat(mediaPlayer.getCurrentPosition()));
    }

    public String simpleDateFormat(int time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(time);
    }

    public void registerListenerSeekBar() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(mSeekBar.getProgress());
                setTimeNow();
            }
        });
    }

    public void registerListener() {
        mImageViewPlayPause.setOnClickListener(this);
        mImageViewPlayNext.setOnClickListener(this);
        mImageViewPlayBack.setOnClickListener(this);
        mImageViewShup.setOnClickListener(this);
        mImageViewRandom.setOnClickListener(this);
        mImageViewDownload.setOnClickListener(this);
    }

    public void setSeekBar(int duration) {
        mSeekBar.setMax(duration);
    }

    public void downloadMp3(){
            showNotifiWhenWifiOn(this);
    }

    private void showNotifiWhenWifiOn(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMediaSession = new MediaSession(context, TAG_MEDIA);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder = new Notification.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_adjust_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.winter))
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(context.getString(R.string.em_se_la_co_dau))
                    .setContentText(context.getString(R.string.auth_eslcd))
                    .setAutoCancel(true)
                    .setColor(Color.GRAY)
                    .setStyle(new Notification.MediaStyle().setShowActionsInCompactView(0, 1, 2).setMediaSession(mMediaSession.getSessionToken()))
                    .setContentIntent(Main2Activity.getIntentMp3(context))
                    .addAction(R.drawable.ic_skip_previous_black_24dp, BUTTON_BACK_TITLE, Main2Activity.getIntentMp3(context))
                    .addAction(R.drawable.ic_pause_black_24dp, BUTTON_PAUSE_TITLE, Main2Activity.getIntentMp3(context))
                    .addAction(R.drawable.ic_skip_next_black_24dp, BUTTON_NEXT_TITLE, Main2Activity.getIntentMp3(context)).build()
            ;
        }
        {
            mBuilderC = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_adjust_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.winter))
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(context.getString(R.string.em_se_la_co_dau))
                    .setContentText(context.getString(R.string.auth_eslcd))
                    .setAutoCancel(true)
                    .setColor(Color.GRAY)
                    .setContentIntent(Main2Activity.getIntentMp3(context))
                    .addAction(R.drawable.ic_skip_previous_black_24dp, BUTTON_BACK_TITLE, Main2Activity.getIntentMp3(context))
                    .addAction(R.drawable.ic_pause_black_24dp, BUTTON_PAUSE_TITLE, Main2Activity.getIntentMp3(context))
                    .addAction(R.drawable.ic_skip_next_black_24dp, BUTTON_NEXT_TITLE, Main2Activity.getIntentMp3(context));
        }
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.my_data);
            String description = context.getString(R.string.my_des);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            mNotificationManager.createNotificationChannel(channel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder);
        } else {
            mNotificationManager.notify(NOTIFICATION_ID, mBuilderC.build());
        }
    }

    private void initView() {
        mSeekBar = findViewById(R.id.seekbar_main);
        mTextView = findViewById(R.id.textview_duration);
        mImageViewPlayPause = findViewById(R.id.imageview_play_pause);
        mImageViewPlayNext = findViewById(R.id.imageview_play_next);
        mImageViewPlayBack = findViewById(R.id.imageview_play_back);
        mImageViewShup = findViewById(R.id.imageview_shup);
        mImageViewRandom = findViewById(R.id.imageview_random);
        mImageViewCD = findViewById(R.id.imageview_cd);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_cd);
        mAnimationNot = AnimationUtils.loadAnimation(this, R.anim.not_rotate);
        mImageViewDownload = findViewById(R.id.imageview_download);
    }

    private void initPresenter() {
        mLoadPresenter = new LoadPresenter();
        mLoadPresenter.setView(this);
        mLoadPresenter.handleLoadUser();
    }

    private void loadAnimation() {
        mImageViewCD.setAnimation(mAnimation);
    }

    private void offAnimation() {
        mImageViewCD.setAnimation(mAnimationNot);
    }

    @Override
    public void loadUserSuccess(List<Song> songs) {
        addSong(songs);
        //loadUser(songs);
        //mLoadPresenter.cancelAsync();
    }

    @Override
    public void loadUserFailure(String error) {
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoadPresenter.cancelAsync();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageview_play_pause:
                Log.d("valueSUB",""+sub_URL);
                if(sub_URL == null){
                    setMediaPlayer();
                    mFeelClick = 0;
                } else {
                    playMp3();
                    mFeelClick = 0;
                }
                break;
            case R.id.imageview_play_next:
                position++;
                setMediaPlayer();
                mFeelClick = 1;
                break;
            case R.id.imageview_play_back:
                position--;
                setMediaPlayer();
                mFeelClick = -1;
                break;
            case R.id.imageview_shup:
                playShup();
                break;
            case R.id.imageview_random:
                playRandom();
                break;
            case R.id.imageview_download:
                downloadMp3();
                break;
        }
    }
}
