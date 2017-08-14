package hml.come.fucheng.activityDealerChild;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.activity.BaseActivity;
import hml.come.fucheng.adapter.BankCardAdapter;
import hml.come.fucheng.moudle.BankCardData;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.singleton.CustomInfo;

/**
 * Created by TX on 2017/8/8.
 */

public class BankCardActivity extends BaseActivity implements View.OnClickListener{
    private TextView title_text;
    private ListView listView;
    private View headView;
    private LinearLayout add;
    private BankCardData cardData;
    private BankCardAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);
        back();
        immersion();
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("我的银行卡");
        listView = (ListView)findViewById(R.id.bank_card_listView);
        headView = LayoutInflater.from(this).inflate(R.layout.bank_card_head_view, null);
        add = (LinearLayout)headView.findViewById(R.id.add_bank_card);
        add.setOnClickListener(this);
        myBankCard();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_bank_card:
                Intent addIntent = new Intent(BankCardActivity.this, AddBankCardActivity.class);
                startActivity(addIntent);
                break;
        }
    }

    private void myBankCard(){
        RequestParams params = new RequestParams();
        params.put("id", CustomInfo.getInfo().getDealer_id());
        HttpClient.get_istance().post(NetUrl.MY_BANK_CARD, params, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                cardData = gson.fromJson(response.toString(), BankCardData.class);
                adapter = new BankCardAdapter(BankCardActivity.this, cardData.data);
                listView.addFooterView(headView);
                listView.setAdapter(adapter);
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        myBankCard();
    }
}
