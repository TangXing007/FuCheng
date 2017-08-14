package hml.come.fucheng.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.moudle.LandingData;
import hml.come.fucheng.net_work.NetUrl;

/**
 * Created by TX on 2017/7/24.
 */

public class LandingActivity extends BaseActivity implements View.OnClickListener{
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    private EditText landing_phone, landing_password;
    private TextView landing_button, new_zhuce, remeber_password, title;
    private LandingData landingData;
    private String phone, password;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        immersion();
        landing_phone = (EditText)findViewById(R.id.langding_phone);
        landing_password = (EditText)findViewById(R.id.landing_password);
        landing_button = (TextView)findViewById(R.id.landing_button);
        title = (TextView)findViewById(R.id.title_text);
        title.setText("登陆");
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = preferences.edit();
        new_zhuce = (TextView)findViewById(R.id.new_zhuce);
        remeber_password = (TextView)findViewById(R.id.remeber_password);
        landing_button.setOnClickListener(this);
        new_zhuce.setOnClickListener(this);
        remeber_password.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if (requestCode != 0 && data != null){
                    phone = data.getStringExtra("phone");
                    password = data.getStringExtra("password");
                    landing_phone.setText(phone);
                    landing_password.setText(password);
                    break;
                }
            case 2:
                if (requestCode != 0 && data != null) {
                    phone = data.getStringExtra("phone");
                    password = data.getStringExtra("password");
                    landing_phone.setText(phone);
                    landing_password.setText(password);
                    break;
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.landing_button:
                final String number = landing_phone.getText().toString();
                final String password = landing_password.getText().toString();
                if (number == null || number.equals("") || password == null || password.equals("")){
                    Toast.makeText(LandingActivity.this,"账户和密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    RequestParams params = new RequestParams();
                    params.put("phone", number);
                    params.put("password", password);
                    HttpClient.get_istance().post(NetUrl.USER_LANDING, params, new JsonHttpResponseHandler(){
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            if (statusCode == 200){
                                Gson gson = new Gson();
                                landingData = gson.fromJson(response.toString(), LandingData.class);
                            }
                            String status = landingData.status;
                            if (status.equals("1")){
                                editor.putString("number", number);
                                editor.putString("password", password);
                                editor.putString("user_id" , landingData.uid);
                                editor.commit();
                                Toast.makeText(LandingActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(LandingActivity.this, "登陆失败请检查账户密码", Toast.LENGTH_SHORT).show();
                            }
                            /*if (status.equals("2")){
                                Toast.makeText(LandingActivity.this, "登陆失败请检查账户密码", Toast.LENGTH_SHORT).show();
                            }
                            if (status.equals("3")){
                                Toast.makeText(LandingActivity.this, landingData.mag.toString(), Toast.LENGTH_SHORT).show();
                            }*/
                        }
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            System.out.println("wrong");
                        }
                    });
                }
                break;
            case R.id.new_zhuce:
                Intent zhuceIntent = new Intent(LandingActivity.this, ZhuCeActivity.class);
                startActivityForResult(zhuceIntent, 1);
                break;
            case R.id.remeber_password:
                Intent forgetPasswordIntent = new Intent(LandingActivity.this, ForgetPasswordActivity.class);
                startActivityForResult(forgetPasswordIntent, 2);
                break;
        }
    }
}
