package hml.come.fucheng.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hml.come.fucheng.R;

/**
 * Created by TX on 2017/7/19.
 */

public class FuChengActivity extends BaseActivity {
    private TextView title_text;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fucheng);
        immersion();
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("公司简介");
        back();
    }
}
