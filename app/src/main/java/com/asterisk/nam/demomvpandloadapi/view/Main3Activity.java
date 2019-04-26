package com.asterisk.nam.demomvpandloadapi.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.asterisk.nam.demomvpandloadapi.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class Main3Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        new DownloadTask(this).execute("https://api.soundcloud.com/tracks/259412502/download?client_id=e534ffbc2d474446c8538d23b1c7605c");
    }

    static class DownloadTask extends AsyncTask<String,Integer,String> {

        ImageView mImageView;
        private Activity mActivity;
        String mStringFileName;
        double mDoubleFileSize;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            /*
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/Download/file_name.extension");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();ry {
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
            */

            /*
            String path = sUrl[0];
            int file_length = 0;
            try{

                URL url = new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                file_length = urlConnection.getContentLength();
                File new_folder = new File("Download/photoalbum");
                if(!new_folder.exists()){
                    new_folder.mkdir();
                }

                File input_file = new File(new_folder,"downloaded_image.jpg");
                InputStream inputStream = new BufferedInputStream(url.openStream(),8192);
                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;
                OutputStream outputStream = new FileOutputStream(new_folder);
                while ((count = inputStream.read(data))!= 1){

                    total += count;
                    outputStream.write(data,0,count);
                    int progress =  (int)total*100/file_length;
                    publishProgress(progress);
                }

                inputStream.close();
                outputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download complete ...";
            */

            mStringFileName = sUrl[0].substring(sUrl[0].lastIndexOf("/")+ 1);

            try{
                InputStream input = null;
                OutputStream output =  null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(sUrl[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                        return "Server return HTTP" + connection.getResponseCode() + " " + connection.getResponseMessage();
                    }

                    int fileLength =  connection.getContentLength();
                    mDoubleFileSize = fileLength;

                    input = connection.getInputStream();
                    //output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyDownloadedFiles/"+mStringFileName);
                    output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyDownloadedFiles/"+mStringFileName);

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while((count = input.read(data)) != 1){
                        if(isCancelled()){
                            return null;
                        }

                        total += count;
                        if(fileLength > 0){
                            publishProgress((int)(total*100/fileLength));
                        }

                        output.write(data,0,count);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally
                {
                    if (output != null){
                        try {
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(input != null){
                        try {
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(connection != null){
                        connection.disconnect();
                    }
                }
                    {
                }
            }finally {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //PowerManager pm = (PowerManager) mActivity.getSystemService(Context.POWER_SERVICE);
            //mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            //mWakeLock.acquire();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.e("progressdown",""+progress[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            //mWakeLock.release();
            //if (result != null)
                //Toast.makeText(mActivity,"Download error: "+result, Toast.LENGTH_LONG).show();
            //else
              //  Toast.makeText(mActivity,"File downloaded", Toast.LENGTH_SHORT).show();

            /*
            Log.e("onPostExecute",""+result);
            String path = "Download/photoalbum/downloaded_image.jpg";
            mImageView = mActivity.findViewById(R.id.imageview_load);
            mImageView.setImageDrawable(Drawable.createFromPath(path));
            */


        }
    }
}
