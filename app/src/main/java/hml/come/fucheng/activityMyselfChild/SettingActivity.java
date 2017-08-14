package hml.come.fucheng.activityMyselfChild;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import hml.come.fucheng.R;
import hml.come.fucheng.eventbus.FirstEventBus;

/**
 * Created by TX on 2017/7/26.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private LinearLayout peson_data, intention_car, alter_password, service_clause, versions;
    private TextView outLanding_button, title_text;
    private LinearLayout back_button;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.bj));
        }
        outLanding_button = (TextView) findViewById(R.id.outlanding_button);
        outLanding_button.setOnClickListener(this);
        back_button = (LinearLayout)findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("设置");
        peson_data = (LinearLayout)findViewById(R.id.peson_data);
        peson_data.setOnClickListener(this);
        intention_car = (LinearLayout)findViewById(R.id.intention_car);
        intention_car.setOnClickListener(this);
        alter_password = (LinearLayout)findViewById(R.id.alter_password);
        alter_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.outlanding_button:
                preferences = getSharedPreferences("data", MODE_PRIVATE);
                editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
                EventBus.getDefault().post(new FirstEventBus("msg"));
                break;
            case R.id.back_button:
                finish();
                break;
            case R.id.peson_data:
                Intent personIntent = new Intent(SettingActivity.this, PersonDataActivity.class);
                startActivity(personIntent);
                break;
            case R.id.intention_car:
                Intent carIntent = new Intent(SettingActivity.this, IntentionCarActivity.class);
                startActivity(carIntent);
                break;
            case R.id.alter_password:
                Intent alterIntent = new Intent(SettingActivity.this, AlterPasswordActivity.class);
                startActivity(alterIntent);
                break;
        }
    }
}
