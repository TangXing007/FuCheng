package hml.come.fucheng.activityDealerChild;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.moudle.SalesManagementData;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.singleton.CustomInfo;

/**
 * Created by TX on 2017/7/31.
 */

public class SalesManagementActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ly1, ly2, ly3, ly4, ly5, ly6, ly7, back_button;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, title_text;
    private SalesManagementData saleData;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_management);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.bj));
        }
        ly1 = (LinearLayout)findViewById(R.id.sales_management_one);
        ly1.setOnClickListener(this);
        ly2 = (LinearLayout)findViewById(R.id.sales_management_two);
        ly2.setOnClickListener(this);
        ly3 = (LinearLayout)findViewById(R.id.sales_management_three);
        ly3.setOnClickListener(this);
        ly4 = (LinearLayout)findViewById(R.id.sales_management_four);
        ly4.setOnClickListener(this);
        ly5 = (LinearLayout)findViewById(R.id.sales_management_five);
        ly5.setOnClickListener(this);
        ly6 = (LinearLayout)findViewById(R.id.sales_management_six);
        ly6.setOnClickListener(this);
        ly7 = (LinearLayout)findViewById(R.id.sales_management_seven);
        ly7.setOnClickListener(this);
        back_button = (LinearLayout)findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        tv1 = (TextView)findViewById(R.id.sponsor);
        tv2 = (TextView)findViewById(R.id.first_trial);
        tv3 = (TextView)findViewById(R.id.last_instance);
        tv4 = (TextView)findViewById(R.id.make_loans);
        tv5 = (TextView)findViewById(R.id.remittance);
        tv6 = (TextView)findViewById(R.id.returned_money);
        tv7 = (TextView)findViewById(R.id.refuse);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("销售管理");
        saleHttp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sales_management_one:
                Intent oneIntent = new Intent(SalesManagementActivity.this, SalesManagementListActivity.class);
                oneIntent.putExtra("status", "0");
                oneIntent.putExtra("title", "已发起");
                startActivity(oneIntent);
                break;
            case R.id.sales_management_two:
                Intent twoIntent = new Intent(SalesManagementActivity.this, SalesManagementListActivity.class);
                twoIntent.putExtra("status", "1");
                twoIntent.putExtra("title", "已初审");
                startActivity(twoIntent);
                break;
            case R.id.sales_management_three:
                Intent threeIntent = new Intent(SalesManagementActivity.this, SalesManagementListActivity.class);
                threeIntent.putExtra("status", "2");
                threeIntent.putExtra("title", "已终审");
                startActivity(threeIntent);
                break;
            case R.id.sales_management_four:
                Intent Intent4 = new Intent(SalesManagementActivity.this, SalesManagementListActivity.class);
                Intent4.putExtra("status", "3");
                Intent4.putExtra("title", "已放款");
                startActivity(Intent4);
                break;
            case R.id.sales_management_five:
                Intent Intent5 = new Intent(SalesManagementActivity.this, SalesManagementListActivity.class);
                Intent5.putExtra("status", "4");
                Intent5.putExtra("title", "已汇款");
                startActivity(Intent5);
                break;
            case R.id.sales_management_six:
                Intent Intent6 = new Intent(SalesManagementActivity.this, SalesManagementListActivity.class);
                Intent6.putExtra("status", "5");
                Intent6.putExtra("title", "已回款");
                startActivity(Intent6);
                break;
            case R.id.sales_management_seven:
                Intent Intent7 = new Intent(SalesManagementActivity.this, SalesManagementListActivity.class);
                Intent7.putExtra("status", "-1");
                Intent7.putExtra("title", "已拒绝");
                startActivity(Intent7);
                break;
            case R.id.back_button:
                finish();
                break;
        }
    }

    private void saleHttp(){
        RequestParams params = new RequestParams();
        params.put("id", CustomInfo.getInfo().getDealer_id());
        HttpClient.get_istance().post(NetUrl.SALES_MANAGEMENT, params, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                saleData = gson.fromJson(response.toString(), SalesManagementData.class);
                tv1.setText(saleData.status_1 + "辆车");
                tv2.setText(saleData.status_2 + "辆车");
                tv3.setText(saleData.status_3 + "辆车");
                tv4.setText(saleData.status_4 + "辆车");
                tv5.setText(saleData.status_5 + "辆车");
                tv6.setText(saleData.status_6 + "辆车");
                tv7.setText(saleData.status_7 + "辆车");
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });
    }
}
