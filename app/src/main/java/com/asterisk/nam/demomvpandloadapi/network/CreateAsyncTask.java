package com.asterisk.nam.demomvpandloadapi.network;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.asterisk.nam.demomvpandloadapi.contract.HandlesLoadData;
import com.asterisk.nam.demomvpandloadapi.model.User;
import com.asterisk.nam.demomvpandloadapi.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class CreateAsyncTask extends AsyncTask<String, List<User>, List<User>> {

    private HandlesLoadData mHandles;

    public CreateAsyncTask(MainActivity mainActivity) {
        this.mHandles = mainActivity;
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
            mHttpConnection.setRequestProperty("User_Agent", "my-rest-app-v0.1");
            mHttpConnection.setReadTimeout(3000);
            mHttpConnection.setConnectTimeout(3000);
            mHttpConnection.setRequestMethod("GET");
            mHttpConnection.setDoInput(true);
            mHttpConnection.connect();
            if (mHttpConnection.getResponseCode() == 200) {
                InputStream mInputStream = mHttpConnection.getInputStream();
                mListUser = readUserJSONFile(mInputStream);
                Log.e("CreateAsyncTask", "doInBackground: " + mListUser.size());
                publishProgress(mListUser);
            } else {

            }
            mHttpConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mListUser;
    }

    @Override
    protected void onProgressUpdate(List<User>... values) {
        mHandles.update(values[0]);
    }

    @Override
    protected void onPostExecute(List<User> s) {
        Log.e("CreateAsyncTask", "onPostExecute: " + s);
    }

    public static List<User> readUserJSONFile(InputStream inputStream) throws IOException, JSONException {

        List<User> mListUser = new ArrayList<>();
        String jsonText = readText(inputStream);
        Log.e("CreateAsyncTask", "readCompanyJSONFile: " + jsonText);

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

    public void contactJsonReader(InputStream mInputStream) {

        try {
            InputStreamReader mInputStreamReader = new InputStreamReader(mInputStream, "UTF-8");
            JsonReader mJsonReader = new JsonReader(mInputStreamReader);

            mJsonReader.beginObject();
            while (mJsonReader.hasNext()) {
                String key = mJsonReader.nextName();
                if (key.equals("id")) {
                    String value = mJsonReader.nextString();
                    Log.e("CreateAsyncTask", "doInBackground: " + value);
                    break;
                } else {
                    mJsonReader.skipValue();
                }
            }
            mJsonReader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
