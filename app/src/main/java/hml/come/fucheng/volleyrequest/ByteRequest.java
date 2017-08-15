package hml.come.fucheng.volleyrequest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Created by tangdi on 8/15/17.
 */

public class ByteRequest extends Request<byte[]>{

    private Response.Listener<byte[]> mListener;

    private Map<String, String> mParame;

    /**
     * this is GET
     * @param url
     * @param listener
     * @param errorlistener
     */
    public ByteRequest(String url, Response.Listener<byte[]> listener, Response.ErrorListener errorlistener) {
        super(Method.GET, url, errorlistener);
        mListener = listener;
    }

    /**
     * this is post
     * @param url
     * @param parame
     * @param listener
     * @param errorlistener
     */
    public ByteRequest(String url, Map<String, String> parame, Response.Listener<byte[]> listener, Response.ErrorListener errorlistener) {
        super(Method.POST, url, errorlistener);
        mListener = listener;
        mParame = parame;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<String, String>();
        if(mParame != null){
            for(Map.Entry<String, String> entry : mParame.entrySet()){
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }
}
