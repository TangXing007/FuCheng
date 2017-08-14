package hml.come.fucheng.activityDealerChild;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.activity.BaseActivity;
import hml.come.fucheng.adapter.SalesListAdapter;
import hml.come.fucheng.moudle.SalesManagementListData;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.singleton.CustomInfo;

/**
 * Created by TX on 2017/8/10.
 */

public class SalesManagementListActivity extends BaseActivity {
    private TextView title_text;
    private String status;
    private ListView listView;
    private SalesListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);
        back();
        immersion();
        title_text = (TextView)findViewById(R.id.title_text);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        status = intent.getStringExtra("status");
        title_text.setText(title);
        listView = (ListView)findViewById(R.id.sales_listView);
        listHttp();
    }

    public void listHttp(){
        RequestParams params = new RequestParams();
        params.put("id", CustomInfo.getInfo().getDealer_id());
        params.put("status", status);
        HttpClient.get_istance().post(NetUrl.SALES_MANAGMENT_LIST, params, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Type listType = new TypeToken<LinkedList<SalesManagementListData>>(){}.getType();
                Gson gson = new Gson();
                LinkedList<SalesManagementListData> list = gson.fromJson(response.toString(), listType);
                adapter = new SalesListAdapter(SalesManagementListActivity.this, list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        onListCilck();
                    }
                });
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONArray errorResponse) {

            }
        });
    }

    private void onListCilck(){
        Intent showIntent = new Intent(SalesManagementListActivity.this, ShowManagementActivity.class);
        startActivity(showIntent);
    }
}
