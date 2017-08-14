package hml.come.fucheng.activityDealerChild;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.activity.BaseActivity;
import hml.come.fucheng.adapter.ContractAdapter;
import hml.come.fucheng.moudle.ContractData;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.singleton.CustomInfo;

/**
 * Created by TX on 2017/8/8.
 */

public class ContractActivity extends BaseActivity {
    private ContractData data;
    private TextView title_text;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private ContractAdapter adapter;
    @Override
    public void onCreate(Bundle savedInsatanceState){
        super.onCreate(savedInsatanceState);
        setContentView(R.layout.activity_contract);
        back();
        immersion();
        contractHttp();
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("我的合同");
        recyclerView = (RecyclerView)findViewById(R.id.contract_recycleView);
        layoutManager = new GridLayoutManager(ContractActivity.this, 3, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void contractHttp(){
        RequestParams params = new RequestParams();
        params.put("id", CustomInfo.getInfo().getDealer_id());
        HttpClient.get_istance().post(NetUrl.CONTRACT, params, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                data = gson.fromJson(response.toString(), ContractData.class);
                if (data.code.equals("1")){
                    adapter = new ContractAdapter(data.data);
                    recyclerView.setAdapter(adapter);
                }
                adapter.setOnItemClickListener(new ContractAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Intent ContractIntent = new Intent(ContractActivity.this,
                                ContractManagementActivity.class);
                        ContractIntent.putExtra("pid", data.data.get(postion).pid);
                        startActivity(ContractIntent);
                    }
                });
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {

            }
        });
    }
}
