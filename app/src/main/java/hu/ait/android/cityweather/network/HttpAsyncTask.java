package hu.ait.android.cityweather.network;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.greenrobot.event.EventBus;
import hu.ait.android.cityweather.data.CityResult;

/**
 * Created by joe on 11/29/15.
 */
public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    public static final String FILTER_HTTP_RESULT = "FILTER_HTTP_RESULT";
    public static final String KEY_EXCHANGE_RESULT = "KEY_EXCHANGE_RESULT";
    private Context context;

    public HttpAsyncTask(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {
        String result = "";

        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            is = conn.getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bos.write(ch);
            }

            result = new String(bos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            Gson gson = new Gson();
            CityResult cityResult = gson.fromJson(result, CityResult.class);
            EventBus.getDefault().post(cityResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
