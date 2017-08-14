package hml.come.fucheng.activityDealerChild;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import hml.come.fucheng.R;
import hml.come.fucheng.singleton.CustomInfo;

/**
 * Created by TX on 2017/7/31.
 */

public class DealerActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout sales_management, on_line_car, my_contract, my_message, bank_card,
            out, back_button;
    private TextView title_text;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.bj));
        }
        sales_management = (LinearLayout)findViewById(R.id.sales_management);
        sales_management.setOnClickListener(this);
        on_line_car = (LinearLayout)findViewById(R.id.on_line_car);
        on_line_car.setOnClickListener(this);
        my_contract = (LinearLayout)findViewById(R.id.my_contract);
        my_contract.setOnClickListener(this);
        my_message = (LinearLayout)findViewById(R.id.my_message);
        my_message.setOnClickListener(this);
        bank_card = (LinearLayout)findViewById(R.id.bank_card);
        bank_card.setOnClickListener(this);
        out = (LinearLayout)findViewById(R.id.out);
        out.setOnClickListener(this);
        back_button = (LinearLayout)findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        title_text =  (TextView)findViewById(R.id.title_text);
        title_text.setText("经销商");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                finish();
                break;
            case R.id.sales_management:
                Intent saleIntent = new Intent(DealerActivity.this, SalesManagementActivity.class);
                startActivity(saleIntent);
                break;
            case R.id.my_message:
                Intent messageIntent = new Intent(DealerActivity.this, MyMessageActivity.class);
                startActivity(messageIntent);
                break;
            case R.id.on_line_car:
                Intent onLineIntent = new Intent(DealerActivity.this, OnLineCarActivity.class);
                startActivity(onLineIntent);
                break;
            case R.id.my_contract:
                Intent contractIntent = new Intent(DealerActivity.this, ContractActivity.class);
                startActivity(contractIntent);
                break;
            case R.id.bank_card:
                Intent bankIntent = new Intent(DealerActivity.this, BankCardActivity.class);
                startActivity(bankIntent);
                break;
            case R.id.out:
                preferences = getSharedPreferences("data", MODE_PRIVATE);
                editor = preferences.edit();
                editor.remove("dealer_id");
                editor.commit();
                CustomInfo.getInfo().setDealer_landing(false);
                Intent landingIntent = new Intent(DealerActivity.this, DealerLandingActivity.class);
                startActivity(landingIntent);
                finish();
                break;
        }
    }
}
