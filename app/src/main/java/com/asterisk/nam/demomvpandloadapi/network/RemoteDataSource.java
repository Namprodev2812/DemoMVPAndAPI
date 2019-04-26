package com.asterisk.nam.demomvpandloadapi.network;

import android.os.AsyncTask;

import com.asterisk.nam.demomvpandloadapi.model.User;
import com.asterisk.nam.demomvpandloadapi.MainActivity;

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

public class RemoteDataSource extends AsyncTask<String, List<User>, List<User>> {

    private NetworkCallback mNetworkCallback;

    public RemoteDataSource(NetworkCallback mNetworkCallback) {
        this.mNetworkCallback = mNetworkCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<User> doInBackground(String... strings) {
        List<User> mListUser = new ArrayList<>();
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
                mListUser = readUserJSONFile(mInputStream);
                publishProgress(mListUser);
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
        return mListUser;
    }

    @Override
    protected void onProgressUpdate(List<User>... values) {
        mNetworkCallback.receiverUsersSuccess(values[0]);
    }

    @Override
    protected void onPostExecute(List<User> s) {
    }

    private static List<User> readUserJSONFile(InputStream inputStream) throws IOException, JSONException {

        List<User> mListUser = new ArrayList<>();
        String jsonText = readText(inputStream);

        JSONArray jsonArray = new JSONArray(jsonText);
        JSONObject[] mListJson = new JSONObject[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            mListJson[i] = jsonArray.getJSONObject(i);
        }
        for (int i = 0; i < mListJson.length; i++) {
            int id = mListJson[i].getInt(MainActivity.URL_ID);
            String name = mListJson[i].getString(MainActivity.URL_NAME);
            String avatar = mListJson[i].getString(MainActivity.URL_AVATAR);
            mListUser.add(new User(id, name, avatar));
        }
        return mListUser;
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
