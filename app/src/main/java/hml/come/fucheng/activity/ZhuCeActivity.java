package hml.come.fucheng.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.net_work.FuChenHttpHandler;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.moudle.IdentifyingCodeData;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.moudle.ZhuCeData;

/**
 * Created by TX on 2017/7/24.
 */

public class ZhuCeActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout back_button;
    private TextView title_text, identifyingCode, zhuce_button;
    private EditText zhuce_phone, zhuce_identifyingCode, zhuce_password_confirm, zhuce_password;
    private IdentifyingCodeData codeData;
    private String Syan_zheng_ma, number, password, password_confirm;
    private int yan_zheng_ma;
    private ZhuCeData zhuCeData;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        immersion();
        back_button = (LinearLayout)findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("个人注册");
        identifyingCode = (TextView)findViewById(R.id.identifyingCode);
        identifyingCode.setOnClickListener(this);
        zhuce_button = (TextView)findViewById(R.id.zhuce_button);
        zhuce_button.setOnClickListener(this);
        zhuce_phone = (EditText)findViewById(R.id.zhuce_phone);
        zhuce_identifyingCode = (EditText)findViewById(R.id.zhuce_identifyingCode);
        zhuce_password = (EditText)findViewById(R.id.zhuce_password);
        zhuce_password_confirm = (EditText)findViewById(R.id.zhuce_password_confirm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.identifyingCode:
                number = zhuce_phone.getText().toString();
                if (number == null || number.equals("")){
                Toast.makeText(ZhuCeActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            }else {
                RequestParams requestParams = new RequestParams();
                requestParams.put("phone", zhuce_phone.getText());
                HttpClient.get_istance().post(NetUrl.YAN_ZHENG_MA, requestParams, new FuChenHttpHandler(){
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        super.onSuccess(statusCode, headers, responseBody);
                        if (responseStr != null && !responseBody.equals("")){
                            Gson gson = new Gson();
                            codeData = gson.fromJson(responseStr, IdentifyingCodeData.class);
                        }
                        if (codeData.status.equals("1")){
                            Toast.makeText(ZhuCeActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
                break;
            case R.id.zhuce_button:
                Syan_zheng_ma = zhuce_identifyingCode.getText().toString();
                number = zhuce_phone.getText().toString();
                password = zhuce_password.getText().toString();
                password_confirm = zhuce_password_confirm.getText().toString();
                if (number == null || number.equals("") || Syan_zheng_ma == null ||Syan_zheng_ma.equals("")
                        || password == null || password.equals("") || password_confirm == null ||
                        password_confirm.equals("")){
                    Toast.makeText(ZhuCeActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(password_confirm)){
                    Toast.makeText(ZhuCeActivity.this, "请保证密码一致", Toast.LENGTH_SHORT).show();
                }else {
                    zhuceHttp();
                }
                break;
            case R.id.back_button:
                finish();
                break;
        }
    }

    private void zhuceHttp(){
        final RequestParams zhuceparams = new RequestParams();
        zhuceparams.put("phone", zhuce_phone.getText());
        zhuceparams.put("password", zhuce_password.getText());
        zhuceparams.put("yzm", zhuce_identifyingCode.getText());
        HttpClient.get_istance().post(NetUrl.USER_ZHU_CE, zhuceparams, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson zhuceGson = new Gson();
                zhuCeData = zhuceGson.fromJson(response.toString(), ZhuCeData.class);
                String status = zhuCeData.status;
                if (status.equals("1")){
                    Toast.makeText(ZhuCeActivity.this, zhuCeData.msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ZhuCeActivity.this, LandingActivity.class);
                    intent.putExtra("phone", number);
                    intent.putExtra("password", password);
                    ZhuCeActivity.this.setResult(2, intent);
                    finish();
                }
                if (status.equals("2")){
                    Toast.makeText(ZhuCeActivity.this, zhuCeData.msg, Toast.LENGTH_SHORT).show();
                }
                if (status.equals("3")){
                    Toast.makeText(ZhuCeActivity.this, zhuCeData.msg, Toast.LENGTH_SHORT).show();
                }
                if (status.equals("0")){
                    Toast.makeText(ZhuCeActivity.this, zhuCeData.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statecode, Header[] headers, String name, Throwable throwable){
                Log.e("dismiss","wrong");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("Failed: ", ""+statusCode);
                Log.e("Error : ", "" + throwable);
            }
        });
    }
}
