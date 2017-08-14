package hml.come.fucheng.activityDealerChild;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.LinearLayout;
import android.widget.TextView;

import hml.come.fucheng.R;
import hml.come.fucheng.activity.BaseActivity;

/**
 * Created by TX on 2017/8/11.
 */

public class ContractManagementActivity extends BaseActivity {
    private LinearLayout back, back_title;
    private TextView title_text;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_management);
        back = (LinearLayout)findViewById(R.id.include_back);
        back_title = (LinearLayout)findViewById(R.id.include_back_title);
        back.setBackground(getResources().getDrawable(R.color.blue_h));
        back_title.setBackground(getResources().getDrawable(R.color.blue_h));
        back();
        immersion();
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setTextColor(getResources().getColor(R.color.whrite));
        title_text.setText("合同详情");
    }
}
