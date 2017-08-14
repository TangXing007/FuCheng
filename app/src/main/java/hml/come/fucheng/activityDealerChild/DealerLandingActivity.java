package hml.come.fucheng.activityDealerChild;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.activity.ApplyForJoin;
import hml.come.fucheng.moudle.DealerLandingData;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.net_work.NetUrl;

/**
 * Created by TX on 2017/8/2.
 */

public class DealerLandingActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText landing_phone, landing_password;
    private TextView apply_for, title_text, landing_button;
    private LinearLayout back_button;
    private DealerLandingData dealerData;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String code;
    private PopupWindow popupWindow;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.bj));
        }
        setContentView(R.layout.activity_dealer_landing);
        landing_phone = (EditText)findViewById(R.id.dealer_landing_phone);
        landing_password = (EditText)findViewById(R.id.dealer_landing_password);
        apply_for = (TextView)findViewById(R.id.apply_for_dealer);
        apply_for.setOnClickListener(this);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("经销商登陆");
        landing_button = (TextView)findViewById(R.id.dealer_landing_button);
        landing_button.setOnClickListener(this);
        back_button = (LinearLayout)findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dealer_landing_button:
                View pop = LayoutInflater.from(this).inflate(R.layout.landing_popupwindow, null);
                popupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT,
                        800);
                popupWindow.showAsDropDown(back_button);
               dealer_landing();
                break;
            case R.id.apply_for_dealer:
                Intent apply_dealerIntent = new Intent(DealerLandingActivity.this, ApplyForJoin.class);
                startActivity(apply_dealerIntent);
                break;
            case R.id.back_button:
                finish();
                break;
        }
    }

    private void dealer_landing(){
        String phone = landing_phone.getText().toString();
        String password = landing_password.getText().toString();
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("password", password);
        params.put("type", 1);
        if (phone != null || !phone.equals("") || password != null || !password.equals("")){
            HttpClient.get_istance().post(NetUrl.DEALER_LANDING, params,
                    new JsonHttpResponseHandler(){
                        public void onSuccess(int statusCode, Header[] headers,
                                              JSONObject response) {
                            Gson gson = new Gson();
                            dealerData = gson.fromJson(response.toString(), DealerLandingData.class);
                            code = dealerData.status;
                            popupWindow.dismiss();
                            if (code.equals("1")){
                                String id = dealerData.uid;
                                preferences = getSharedPreferences("data", MODE_PRIVATE);
                                editor = preferences.edit();
                                editor.putString("dealer_id", id);
                                editor.commit();
                                Toast.makeText(DealerLandingActivity.this,
                                        "登陆成功", Toast.LENGTH_SHORT).show();
                                Intent dealerIntent = new Intent(DealerLandingActivity.this,
                                        DealerActivity.class);
                                startActivity(dealerIntent);
                                finish();
                            }else {
                                Toast.makeText(DealerLandingActivity.this, "登陆失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        public void onFailure(int statusCode, Header[] headers,
                                              Throwable throwable, JSONObject errorResponse) {

                        }
                    });
        }else {
            Toast.makeText(DealerLandingActivity.this, "账户密码不能空", Toast.LENGTH_SHORT).show();
        }
    }
}
