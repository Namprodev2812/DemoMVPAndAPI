package com.asterisk.nam.demomvpandloadapi.view;

import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.asterisk.nam.demomvpandloadapi.R;

public class Main2Activity extends AppCompatActivity {

    public static final int REQUEST_CODE = 69;
    public static final int FLAGS = 0;

    DownloadManager mDownloadManager;
    long mLongQueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        setContentView(R.layout.videourl);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
                        DownloadManager.Query requery = new DownloadManager.Query();
                        requery.equals(mLongQueId);

                        Cursor c = mDownloadManager.query(requery);
                        if(c.moveToFirst()){
                            int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            if(DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)){

                                VideoView videoView = (VideoView) findViewById(R.id.videoview);
                                String uriSring = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                                MediaController mediaController = new MediaController(getApplicationContext());
                                mediaController.setAnchorView(videoView);
                                videoView.requestFocus();
                                videoView.setVideoURI(Uri.parse(uriSring));
                                videoView.start();
                            }
                        }
                    }
            }
        };

        //registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        findViewById(R.id.downmp3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadMp3();
            }
        });

        findViewById(R.id.viewmp3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDown();
            }
        });
    }

    public static PendingIntent getIntentMp3(Context context){
        Intent intent = new Intent(context,Main2Activity.class);
        PendingIntent pendingIntent=  PendingIntent.getActivity(context,REQUEST_CODE,intent,FLAGS);
        return  pendingIntent;
    }

    private void downloadMp3(){
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //DownloadManager.Request request = new DownloadManager.Request(Uri.parse(MainActivity.URL_DOWNLOAD));
        //DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://appeteria.com/video.mp4"));
        //DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://www.google.com/search?q=messi&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiBjt7ihZviAhUiG6YKHR7JCzYQ_AUIDigB&biw=906&bih=627#imgrc=vB6PjPkt3wIUvM:"));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://api.soundcloud.com/tracks/259412502/download?client_id=e534ffbc2d474446c8538d23b1c7605c"));
        request.setTitle("");
        mLongQueId = mDownloadManager.enqueue(request);
    }

    private void viewDown(){
        Intent intent= new Intent();
        intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(intent);
    }
}
