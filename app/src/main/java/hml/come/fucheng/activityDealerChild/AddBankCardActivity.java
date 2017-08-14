package hml.come.fucheng.activityDealerChild;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.activity.BaseActivity;
import hml.come.fucheng.adapter.BankCardAdapter;
import hml.come.fucheng.moudle.AddBankData;
import hml.come.fucheng.moudle.BankCardData;
import hml.come.fucheng.net_work.BaseResult;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.singleton.CustomInfo;

/**
 * Created by TX on 2017/8/8.
 */

public class AddBankCardActivity extends BaseActivity implements View.OnClickListener{
    private EditText bank_class, bank_name, bank_number, user_name, phone, adress;
    private TextView commit_button, title_text;
    private AddBankData data;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        back();
        immersion();
        bank_class = (EditText)findViewById(R.id.bank_class);
        bank_name = (EditText)findViewById(R.id.bank_name);
        bank_number = (EditText)findViewById(R.id.bank_number);
        user_name = (EditText)findViewById(R.id.user_name);
        phone = (EditText)findViewById(R.id.call_phone);
        adress = (EditText)findViewById(R.id.company_adress);
        commit_button = (TextView)findViewById(R.id.add_bank_card_button);
        commit_button.setOnClickListener(this);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("新增银行卡");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_bank_card_button:
                if (bank_class.equals("") || bank_class == null || bank_name == null ||
                        bank_name.equals("") || bank_number == null || bank_number.equals("") ||
                        user_name == null || user_name.equals("") || phone == null ||
                        phone.equals("") || adress == null || adress.equals("")){
                    Toast.makeText(AddBankCardActivity.this, "请填写完整资料", Toast.LENGTH_SHORT).show();
                }else {
                    addCard();
                }
                break;
        }
    }

    private void addCard(){
        RequestParams params = new RequestParams();
        params.put("username", CustomInfo.getInfo().getDealer_id());
        params.put("opening", bank_class.getText());
        params.put("opening_bank", bank_name.getText());
        params.put("where_account", bank_number.getText());
        params.put("name", user_name.getText());
        params.put("phone", phone.getText());
        params.put("address", adress.getText());
        HttpClient.get_istance().post(NetUrl.ADD_BANK_CARD, params, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                data = gson.fromJson(response.toString(), AddBankData.class);
                if (data.status.equals("1")){
                    finish();
                }else {
                    Toast.makeText(AddBankCardActivity.this, "添加银行卡失败，请重试",
                            Toast.LENGTH_SHORT).show();
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {

            }
        });
    }
}
