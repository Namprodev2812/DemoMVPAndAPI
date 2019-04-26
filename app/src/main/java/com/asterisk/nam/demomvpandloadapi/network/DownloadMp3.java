package com.asterisk.nam.demomvpandloadapi.network;

import android.os.AsyncTask;

import com.asterisk.nam.demomvpandloadapi.model.Song;
import com.asterisk.nam.demomvpandloadapi.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DownloadMp3 extends AsyncTask<String, List<Song>, List<Song>> {

    private NetworkCallback mNetworkCallback;

    public DownloadMp3(NetworkCallback mNetworkCallback) {
        this.mNetworkCallback = mNetworkCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Song> doInBackground(String... strings) {
        List<Song> mSong = new ArrayList<>();
        try {
            URL mURL = new URL(strings[0]);
            HttpsURLConnection mHttpConnection = (HttpsURLConnection) mURL.openConnection();
            mHttpConnection.setRequestProperty(MainActivity.URL_USER_KEY, MainActivity.URL_USER_VALUE);
            mHttpConnection.setReadTimeout(MainActivity.URL_REQUEST_TIME);
            mHttpConnection.setConnectTimeout(MainActivity.URL_REQUEST_TIME);
            mHttpConnection.setRequestMethod(MainActivity.URL_REQUEST_METHOD);
            mHttpConnection.setDoInput(true);
            mHttpConnection.connect();
            if (mHttpConnection.getResponseCode() == MainActivity.URL_REQUEST_CODE) {
                InputStream mInputStream = mHttpConnection.getInputStream();
                mSong = readUserJSONFile(mInputStream);
                publishProgress(mSong);
            } else {

            }
            mHttpConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            mNetworkCallback.receiverUsersFail();
        } catch (IOException e) {
            e.printStackTrace();
            mNetworkCallback.receiverUsersFail();
        } catch (JSONException e) {
            e.printStackTrace();
            mNetworkCallback.receiverUsersFail();
        }
        return mSong;
    }

    @Override
    protected void onProgressUpdate(List<Song>... values) {
        mNetworkCallback.receiverUsersSuccess(values[0]);
    }

    @Override
    protected void onPostExecute(List<Song> s) {

    }

    private static List<Song> readUserJSONFile(InputStream inputStream) throws IOException, JSONException {

        List<Song> mSong = new ArrayList<>();
        String jsonText = readText(inputStream);

        JSONObject mJSONObject = new JSONObject(jsonText);
        JSONArray jsonArray = new JSONArray(mJSONObject.getString(MainActivity.URL_COLLECTION));
        JSONObject[] mJSONObject1 = new JSONObject[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            mJSONObject1[i] = jsonArray.getJSONObject(i);
            JSONObject mJSONObject2 = new JSONObject(mJSONObject1[i].getString(MainActivity.URL_TRACK));
            mSong.add(new Song(mJSONObject2.getInt(MainActivity.URL_ID), mJSONObject2.getString(MainActivity.URL_TITLE), mJSONObject1[i].getInt(MainActivity.URL_SCORE)));
        }

        return mSong;
    }

    private static String readText(InputStream inputStream) throws IOException {
        InputStream is = inputStream;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}
