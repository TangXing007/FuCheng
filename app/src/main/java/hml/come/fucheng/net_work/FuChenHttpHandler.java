package hml.come.fucheng.net_work;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.Charset;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TX on 2017/7/24.
 */

public class FuChenHttpHandler extends AsyncHttpResponseHandler {

    private static final String TAG = "FuChenHttpHandler";

    protected String responseStr;

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        responseStr = new String(responseBody, Charset.forName("UTF-8"));
        Log.d(TAG, responseStr);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, error.getMessage());
    }
}
