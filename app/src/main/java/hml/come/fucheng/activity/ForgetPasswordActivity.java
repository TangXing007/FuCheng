package hml.come.fucheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.net_work.FuChenHttpHandler;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.moudle.IdentifyingCodeData;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.moudle.ZhuCeData;

/**
 * Created by TX on 2017/7/26.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout back_button;
    private TextView title_text, identifyingCode, zhuce_button;
    private EditText forget_phone, forget_identifyingCode, forget_password_confirm, forget_password;
    private String yan_zheng_ma, number, password, password_confirm;
    private ZhuCeData forgetData;
    private IdentifyingCodeData identifyingCodeData;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        immersion();
        back_button = (LinearLayout)findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("忘记密码");
        identifyingCode = (TextView)findViewById(R.id.has_identifyingCode);
        identifyingCode.setOnClickListener(this);
        zhuce_button = (TextView)findViewById(R.id.forget_land_button);
        zhuce_button.setOnClickListener(this);
        forget_phone = (EditText)findViewById(R.id.forget_phone);
        forget_identifyingCode = (EditText)findViewById(R.id.forget_identifyingCode);
        forget_password = (EditText)findViewById(R.id.forget_password);
        forget_password_confirm = (EditText)findViewById(R.id.forget_password_confirm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.has_identifyingCode:
                number = forget_phone.getText().toString();
                if (number == null || number.equals("")){
                    Toast.makeText(ForgetPasswordActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("phone", number);
                    HttpClient.get_istance().post(NetUrl.YAN_ZHENG_MA, requestParams, new FuChenHttpHandler(){
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            super.onSuccess(statusCode, headers, responseBody);
                            if (responseStr != null && !responseBody.equals("")){
                                Gson identifyingCodeGosn = new Gson();
                                identifyingCodeData = identifyingCodeGosn.fromJson(responseStr, IdentifyingCodeData.class);
                            }
                            if (identifyingCodeData.status.equals("1")){
                                Toast.makeText(ForgetPasswordActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.forget_land_button:
                number = forget_phone.getText().toString();
                password = forget_password.getText().toString();
                password_confirm = forget_password_confirm.getText().toString();
                yan_zheng_ma = forget_identifyingCode.getText().toString();
                if (number == null || number.equals("") || yan_zheng_ma == null ||yan_zheng_ma.equals("")
                        || password == null || password.equals("") || password_confirm == null ||
                        password_confirm.equals("")){
                    Toast.makeText(ForgetPasswordActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(password_confirm)){
                    Toast.makeText(ForgetPasswordActivity.this, "请保证密码一致", Toast.LENGTH_SHORT).show();
                }else {
                    forgetHttp();
                }
                break;
            case R.id.back_button:
                finish();
                break;
        }
    }

    private void forgetHttp(){
        RequestParams params = new RequestParams();
        params.put("phone", forget_phone.getText());
        params.put("password", forget_password.getText());
        params.put("yzm", forget_identifyingCode.getText());
        HttpClient.get_istance().post(NetUrl.FORGET_PASSWORD, params, new FuChenHttpHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                super.onSuccess(statusCode, headers, responseBody);
                if (responseStr != null && !responseBody.equals("")){
                    Gson gson = new Gson();
                    forgetData = gson.fromJson(responseStr, ZhuCeData.class);
                    String status = forgetData.status;
                    if (status.equals("1")){
                        Toast.makeText(ForgetPasswordActivity.this, forgetData.msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgetPasswordActivity.this, LandingActivity.class);
                        intent.putExtra("phone", number);
                        intent.putExtra("password", password);
                        ForgetPasswordActivity.this.setResult(2, intent);
                        finish();
                    }
                    if (status.equals("2")){
                        Toast.makeText(ForgetPasswordActivity.this, forgetData.msg, Toast.LENGTH_SHORT).show();
                    }
                    if (status.equals("0")){
                        Toast.makeText(ForgetPasswordActivity.this, forgetData.msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
