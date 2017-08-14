package hml.come.fucheng.activityDealerChild;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.activity.BaseActivity;
import hml.come.fucheng.adapter.CarListAdapter;
import hml.come.fucheng.moudle.CarListData;
import hml.come.fucheng.net_work.BaseResult;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.singleton.CustomInfo;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by TX on 2017/8/4.
 */
@RuntimePermissions
public class OnLineCarActivity extends BaseActivity implements View.OnClickListener{
    private View pop;
    private Bitmap bitmapP;
    private ArrayList<RadioButton> list = new ArrayList<>();
    private ArrayList<RadioButton> Llist = new ArrayList<>();
    private TextView title_text, brand_models, endowment_proportion, car_adress;
    private int index = 0;
    private int rb = 0, lrb = 0;
    private byte[] bytes, bytes1, bytes2, bytes3, bytes4, bytes5, bytes6, bytes7, bytes8, bytes9;
    private int length;
    private TextView commit_button;
    private LinearLayout ly1, ly2, ly3, ly4, ly5, ly6, ly7, ly8, ly9;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    private EditText company_name, color, chassis_number, factory_time, closing_price,
            forecast_sale_price, endowment_price, new_car_guided, salesman;
    private PopupWindow popupWindow1, popupWindow2, popupWindow3;
    private ListView car_listView;
    private CarListAdapter adapter;
    private CarListData listData;
    private String carName, carId;
    private View headView;
    private CarListData.Data data;
    private RadioButton head_button;
    private RadioButton rb0, rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, lrb1, lrb2, lrb3, lrb4;
    private LinearLayout choose, proportion_one, proportion_two, proportion_three, proportion_four,
            proportion_five, proportion_six, proportion_seven, proportion_eight, location_one,
    location_two, location_three, location_four;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_on_line_car);
        back();
        immersion();
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("在线订车");
        image1 = (ImageView)findViewById(R.id.car_head_image);
        ly1 = (LinearLayout)findViewById(R.id.car_head);
        ly1.setOnClickListener(this);
        image2 = (ImageView)findViewById(R.id.car_side_image);
        ly2 = (LinearLayout)findViewById(R.id.car_side);
        ly2.setOnClickListener(this);
        image3 = (ImageView)findViewById(R.id.car_place_image);
        ly3 = (LinearLayout)findViewById(R.id.car_place);
        ly3.setOnClickListener(this);
        image4 = (ImageView)findViewById(R.id.car_control_image);
        ly4 = (LinearLayout)findViewById(R.id.car_control);
        ly4.setOnClickListener(this);
        image5 = (ImageView)findViewById(R.id.car_dashboard_image);
        ly5 = (LinearLayout)findViewById(R.id.car_dashboard);
        ly5.setOnClickListener(this);
        image6 = (ImageView)findViewById(R.id.car_nameplate_image);
        ly6 = (LinearLayout)findViewById(R.id.car_nameplate);
        ly6.setOnClickListener(this);
        image7 = (ImageView)findViewById(R.id.car_engine_image);
        ly7 = (LinearLayout)findViewById(R.id.car_engine);
        ly7.setOnClickListener(this);
        image8 = (ImageView)findViewById(R.id.car_qualified_image);
        ly8 = (LinearLayout)findViewById(R.id.car_qualified);
        ly8.setOnClickListener(this);
        image9 = (ImageView)findViewById(R.id.car_skylight_image);
        ly9 = (LinearLayout)findViewById(R.id.car_skylight);
        ly9.setOnClickListener(this);
        company_name = (EditText)findViewById(R.id.company_name);
        brand_models = (TextView) findViewById(R.id.brand_models);
        brand_models.setOnClickListener(this);
        color = (EditText)findViewById(R.id.color);
        chassis_number = (EditText)findViewById(R.id.chassis_number);
        factory_time = (EditText)findViewById(R.id.factory_time);
        closing_price = (EditText)findViewById(R.id.closing_price);
        forecast_sale_price = (EditText)findViewById(R.id.forecast_sale_price);
        endowment_proportion = (TextView) findViewById(R.id.endowment_proportion);
        endowment_proportion.setOnClickListener(this);
        endowment_price = (EditText)findViewById(R.id.endowment_price);
        new_car_guided = (EditText)findViewById(R.id.new_car_guided);
        car_adress = (TextView) findViewById(R.id.car_adress);
        car_adress.setOnClickListener(this);
        salesman = (EditText)findViewById(R.id.salesman);
        commit_button = (TextView)findViewById(R.id.on_line_button);
        commit_button.setOnClickListener(this);

        pop = LayoutInflater.from(this).inflate(R.layout.car_list_pop, null);
        popupWindow1 = new PopupWindow(pop, 900, 1700);
        popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
        popupWindow1.setFocusable(true);
        popupWindow1.update();
        car_listView = (ListView)pop.findViewById(R.id.car_listView);
        headView = LayoutInflater.from(this).inflate(R.layout.car_list_head_view, null);
        head_button = (RadioButton)headView.findViewById(R.id.car_head_button);
        progressBar = (ProgressBar)pop.findViewById(R.id.progressBar);
        RequestParams params = new RequestParams();
        params.put("id", CustomInfo.getInfo().getDealer_id());
        HttpClient.get_istance().post(NetUrl.CAR_LIST, params, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Gson gson = new Gson();
                listData = gson.fromJson(response.toString(), CarListData.class);
                if(listData.car_list != null && listData.car_list.size() !=  0){
                    adapter = new CarListAdapter(OnLineCarActivity.this, listData.car_list);
                    progressBar.setVisibility(View.GONE);
                    car_listView.setVisibility(View.VISIBLE);
                }
                if(adapter != null){
                    car_listView.addHeaderView(headView);
                    car_listView.setAdapter(adapter);
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.car_head:
                index = 1;
                changeHeadIcon();
                break;
            case R.id.car_side:
                index = 2;
                changeHeadIcon();
                break;
            case R.id.car_place:
                index = 3;
                changeHeadIcon();
                break;
            case R.id.car_control:
                index = 4;
                changeHeadIcon();
                break;
            case R.id.car_dashboard:
                index = 5;
                changeHeadIcon();
                break;
            case R.id.car_nameplate:
                index = 6;
                changeHeadIcon();
                break;
            case R.id.car_engine:
                index = 7;
                changeHeadIcon();
                break;
            case R.id.car_qualified:
                index = 8;
                changeHeadIcon();
                break;
            case R.id.car_skylight:
                index = 9;
                changeHeadIcon();
                break;
            case R.id.brand_models:
                pop();
                break;
            case R.id.endowment_proportion:
                proportionPop();
                break;
            case R.id.chooose:
                popupWindow2.dismiss();
                break;
            case R.id.proportion_one:
                rb = 1;
                endowment_proportion.setText("10%");
                popupWindow2.dismiss();
                break;
            case R.id.proportion_two:
                rb = 2;
                endowment_proportion.setText("20%");
                popupWindow2.dismiss();
                break;
            case R.id.proportion_three:
                rb = 3;
                endowment_proportion.setText("30%");
                popupWindow2.dismiss();
                break;
            case R.id.proportion_four:
                rb = 4;
                endowment_proportion.setText("40%");
                popupWindow2.dismiss();
                break;
            case R.id.proportion_five:
                rb = 5;
                endowment_proportion.setText("50%");
                popupWindow2.dismiss();
                break;
            case R.id.proportion_six:
                rb = 6;
                endowment_proportion.setText("60%");
                popupWindow2.dismiss();
                break;
            case R.id.proportion_seven:
                rb = 7;
                endowment_proportion.setText("70%");
                popupWindow2.dismiss();
                break;
            case R.id.proportion_eight:
                rb = 8;
                endowment_proportion.setText("80%");
                popupWindow2.dismiss();
                break;
            case R.id.on_line_button:
                if (bytes1 == null || bytes1.equals("") || bytes2 == null || bytes2.equals("") ||
                        bytes3 == null || bytes3.equals("") || bytes4 == null || bytes4.equals("") ||
                        bytes5 == null || bytes5.equals("") || bytes6 == null || bytes6.equals("") ||
                        bytes7 == null || bytes7.equals("") || bytes8 == null || bytes8.equals("") ||
                        bytes9 == null || bytes9.equals("") || company_name.getText() == null ||
                        company_name.getText().equals("") || brand_models.getText() == null ||
                        brand_models.getText().equals("") || color.getText() == null || color.getText()
                        .equals("") || chassis_number.getText() == null || chassis_number.getText()
                        .equals("") || factory_time.getText() == null || factory_time.getText()
                        .equals("") || closing_price .getText()== null || closing_price.getText()
                        .equals("") || forecast_sale_price.getText() == null || forecast_sale_price
                        .getText().equals("") || endowment_proportion.getText() == null ||
                        endowment_proportion.getText().equals("") || endowment_price.getText()
                        == null || endowment_price.getText().equals("") || new_car_guided.getText()
                        == null || new_car_guided.getText().equals("") || car_adress.getText() == null
                        || car_adress.getText().equals("") || salesman.getText() == null || salesman
                        .getText().equals("")){
                    Toast.makeText(OnLineCarActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }else {
                    imageCommit();
                }
                break;
            case R.id.car_adress:
                locationPop();
                break;
            case R.id.location_one:
                lrb = 1;
                car_adress.setText("东区");
                popupWindow3.dismiss();
                break;
            case R.id.location_two:
                lrb = 2;
                car_adress.setText("南区");
                popupWindow3.dismiss();
                break;
            case R.id.location_three:
                lrb = 3;
                car_adress.setText("西区");
                popupWindow3.dismiss();
                break;
            case R.id.location_four:
                lrb = 4;
                car_adress.setText("南区");
                popupWindow3.dismiss();
                break;
        }
    }

    private void changeHeadIcon(){
        final CharSequence[] items = {"相册", "拍照"};
        AlertDialog dialog = new AlertDialog.Builder(OnLineCarActivity.this)
                .setTitle("选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            OnLineCarActivityPermissionsDispatcher.photoAlbumWithCheck
                                    (OnLineCarActivity.this);
                        }else {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, 3);
                        }
                    }
                }).create();
        dialog.show();
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void  photoAlbum(){
        Intent intent=new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void photoAlbumWrong(){
        Toast.makeText(OnLineCarActivity.this,
                "获取权限失败，可能无法打开相册", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OnLineCarActivityPermissionsDispatcher.onRequestPermissionsResult
                (this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case  2:
                if (data != null){
                    Uri imageUri = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUri, filePath, null, null, null);
                    cursor.moveToFirst();
                    int imageIndex = cursor.getColumnIndex(filePath[0]);
                    String picturePath = cursor.getString(imageIndex);
                    cursor.close();
                    FileInputStream inputStream;
                    try {
                        File file = new File(picturePath);
                        inputStream = new FileInputStream(file);
                        /*对取出的照片进行压缩*/
                        bitmapP = BitmapFactory.decodeStream(inputStream);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmapP.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                        bytes = byteArrayOutputStream.toByteArray();
                        /*length = inputStream.available();
                        bytes = new byte[length];
                        inputStream.read(bytes);*/
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } /*catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    switch (index){
                        case 1:
                            bytes1 = bytes;
                            image1.setImageBitmap(bitmapP);
                            image1.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case 2:
                            bytes2 = bytes;
                            image2.setImageBitmap(bitmapP);
                            image2.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case 3:
                            bytes3 = bytes;
                            image3.setImageBitmap(bitmapP);
                            image3.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case 4:
                            bytes4 = bytes;
                            image4.setImageBitmap(bitmapP);
                            image4.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case 5:
                            bytes5 = bytes;
                            image5.setImageBitmap(bitmapP);
                            image5.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case 6:
                            bytes6 = bytes;
                            image6.setImageBitmap(bitmapP);
                            image6.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case 7:
                            bytes7 = bytes;
                            image7.setImageBitmap(bitmapP);
                            image7.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case 8:
                            bytes8 = bytes;
                            image8.setImageBitmap(bitmapP);
                            image8.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case 9:
                            bytes9 = bytes;
                            image9.setImageBitmap(bitmapP);
                            image9.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                    }
                }
                break;
            case 3:
                if (data != null ){
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (bitmap != null){
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                        bytes = byteArrayOutputStream.toByteArray();
                        switch (index){
                            case 1:
                                bytes1 = bytes;
                                image1.setImageBitmap(bitmap);
                                image1.setScaleType(ImageView.ScaleType.FIT_XY);
                                break;
                            case 2:
                                bytes2 = bytes;
                                image2.setImageBitmap(bitmap);
                                image2.setScaleType(ImageView.ScaleType.FIT_XY);
                                break;
                            case 3:
                                bytes3 = bytes;
                                image3.setImageBitmap(bitmap);
                                image3.setScaleType(ImageView.ScaleType.FIT_XY);
                                break;
                            case 4:
                                bytes4 = bytes;
                                image4.setImageBitmap(bitmap);
                                image4.setScaleType(ImageView.ScaleType.FIT_XY);
                                break;
                            case 5:
                                bytes5 = bytes;
                                image5.setImageBitmap(bitmap);
                                image5.setScaleType(ImageView.ScaleType.FIT_XY);
                                break;
                            case 6:
                                bytes6 = bytes;
                                image6.setImageBitmap(bitmap);
                                image6.setScaleType(ImageView.ScaleType.FIT_XY);
                                break;
                            case 7:
                                bytes7 = bytes;
                                image7.setImageBitmap(bitmap);
                                image7.setScaleType(ImageView.ScaleType.FIT_XY);
                                break;
                            case 8:
                                bytes8 = bytes;
                                image8.setImageBitmap(bitmap);
                                image8.setScaleType(ImageView.ScaleType.FIT_XY);
                                break;
                            case 9:
                                bytes9 = bytes;
                                image9.setImageBitmap(bitmap);
                                image9.setScaleType(ImageView.ScaleType.FIT_XY);
                                break;
                        }
                    }
                }
                break;
        }
    }

    private void imageCommit(){
        RequestParams imageparams = new RequestParams();
        imageparams.put("a", new ByteArrayInputStream(bytes1));
        imageparams.put("b", new ByteArrayInputStream(bytes2));
        imageparams.put("c", new ByteArrayInputStream(bytes3));
        imageparams.put("d", new ByteArrayInputStream(bytes4));
        imageparams.put("e", new ByteArrayInputStream(bytes5));
        imageparams.put("f", new ByteArrayInputStream(bytes6));
        imageparams.put("g", new ByteArrayInputStream(bytes7));
        imageparams.put("h", new ByteArrayInputStream(bytes8));
        imageparams.put("i", new ByteArrayInputStream(bytes9));
        imageparams.put("jxsId", CustomInfo.getInfo().getDealer_id());
        imageparams.put("brand", carId);
        imageparams.put("color", color.getText().toString());
        imageparams.put("chejiahao", chassis_number.getText().toString());
        imageparams.put("chuchang_shijian", factory_time.getText().toString());
        imageparams.put("shoujia", closing_price.getText().toString());
        imageparams.put("yugumaijia", forecast_sale_price.getText().toString());
        imageparams.put("dianzibili", endowment_proportion.getText().toString());
        imageparams.put("dianzie", endowment_price.getText().toString());
        imageparams.put("xinchezhidaojia", new_car_guided.getText().toString());
        imageparams.put("cheliangsuozaidi", car_adress.getText().toString());
        imageparams.put("yewuyuan", salesman.getText().toString());
        String s = NetUrl.ONLINE_CAR;
        HttpClient.get_istance().post(NetUrl.ONLINE_CAR, imageparams,
                new JsonHttpResponseHandler(){

                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        Gson gson = new Gson();
                        BaseResult data = new BaseResult();
                        data = gson.fromJson(response.toString(), BaseResult.class);
                        if(data.code.equals("1")){
                            Toast.makeText(OnLineCarActivity.this, "提交成功", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        String a = "true";
                    }
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {
                        String b = "false";
                    }
                });
    }

    private void pop(){
        if (listData != null && listData.car_list != null && listData.car_list.size() != 0){
            progressBar.setVisibility(View.GONE);
            car_listView.setVisibility(View.VISIBLE);
            for (CarListData.Data item : listData.car_list){
                if (item.check != false){
                    head_button.setChecked(false);
                }
            }
        }else {
            car_listView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        popupWindow1.showAtLocation(brand_models, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        car_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    popupWindow1.dismiss();
                }else {
                    for (CarListData.Data check : listData.car_list){
                        check.check = false;
                    }
                    brand_models.setText(listData.car_list.get(position - 1).car_name);
                    new_car_guided.setText(listData.car_list.get(position - 1).dealer_pricing + ".00");
                    listData.car_list.get(0).check = false;
                    head_button.setChecked(false);
                    listData.car_list.get(position - 1).check = true;
                    carId = listData.car_list.get(position).aid;
                    popupWindow1.dismiss();
                }
            }
        });
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                car_listView.removeHeaderView(headView);
            }
        });
    }

    private void proportionPop(){
        View pop = LayoutInflater.from(this).inflate(R.layout.proportion_popupwindow, null);
        rb0 = (RadioButton)pop.findViewById(R.id.rb_zero);
        list.add(rb0);
        rb1 = (RadioButton)pop.findViewById(R.id.rb_one);
        list.add(rb1);
        rb2 = (RadioButton)pop.findViewById(R.id.rb_two);
        list.add(rb2);
        rb3 = (RadioButton)pop.findViewById(R.id.rb_three);
        list.add(rb3);
        rb4 = (RadioButton)pop.findViewById(R.id.rb_four);
        list.add(rb4);
        rb5 = (RadioButton)pop.findViewById(R.id.rb_five);
        list.add(rb5);
        rb6 = (RadioButton)pop.findViewById(R.id.rb_six);
        list.add(rb6);
        rb7 = (RadioButton)pop.findViewById(R.id.rb_seven);
        list.add(rb7);
        rb8 = (RadioButton)pop.findViewById(R.id.rb_eight);
        list.add(rb8);
        popupWindow2 = new PopupWindow(pop, 900, 1700);
        popupWindow2.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
        popupWindow2.setFocusable(true);
        popupWindow2.update();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        for (RadioButton radioButton : list){
            radioButton.setChecked(false);
        }
        switch (rb){
            case 0:
                rb0.setChecked(true);
                break;
            case 1:
                rb1.setChecked(true);
                break;
            case 2:
                rb2.setChecked(true);
                break;
            case 3:
                rb3.setChecked(true);
                break;
            case 4:
                rb4.setChecked(true);
                break;
            case 5:
                rb5.setChecked(true);
                break;
            case 6:
                rb6.setChecked(true);
                break;
            case 7:
                rb7.setChecked(true);
                break;
            case 8:
                rb8.setChecked(true);
                break;
        }
        popupWindow2.showAtLocation(endowment_proportion, Gravity.CENTER, 0, 0);
        choose = (LinearLayout)pop.findViewById(R.id.chooose);
        choose.setOnClickListener(this);
        proportion_one = (LinearLayout)pop.findViewById(R.id.proportion_one);
        proportion_one.setOnClickListener(this);
        proportion_two = (LinearLayout)pop.findViewById(R.id.proportion_two);
        proportion_two.setOnClickListener(this);
        proportion_three = (LinearLayout)pop.findViewById(R.id.proportion_three);
        proportion_three.setOnClickListener(this);
        proportion_four = (LinearLayout)pop.findViewById(R.id.proportion_four);
        proportion_four.setOnClickListener(this);
        proportion_five = (LinearLayout)pop.findViewById(R.id.proportion_five);
        proportion_five.setOnClickListener(this);
        proportion_six = (LinearLayout)pop.findViewById(R.id.proportion_six);
        proportion_six.setOnClickListener(this);
        proportion_seven = (LinearLayout)pop.findViewById(R.id.proportion_seven);
        proportion_seven.setOnClickListener(this);
        proportion_eight = (LinearLayout)pop.findViewById(R.id.proportion_eight);
        proportion_eight.setOnClickListener(this);
        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void locationPop(){
        View pop = LayoutInflater.from(this).inflate(R.layout.location_popupwindow, null);
        lrb1 = (RadioButton)pop.findViewById(R.id.lrb_one);
        Llist.add(lrb1);
        lrb2 = (RadioButton)pop.findViewById(R.id.lrb_two);
        Llist.add(lrb2);
        lrb3 = (RadioButton)pop.findViewById(R.id.lrb_three);
        Llist.add(lrb3);
        lrb4 = (RadioButton)pop.findViewById(R.id.lrb_four);
        Llist.add(lrb4);
        popupWindow3 = new PopupWindow(pop, 900, 800);
        popupWindow3.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
        popupWindow3.setFocusable(true);
        popupWindow3.update();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        for (RadioButton radioButton : Llist){
            radioButton.setChecked(false);
        }
        switch (lrb){
            case 1 :
                lrb1.setChecked(true);
                break;
            case 2:
                lrb2.setChecked(true);
                break;
            case 3:
                lrb3.setChecked(true);
                break;
            case 4:
                lrb4.setChecked(true);
                break;
        }
        popupWindow3.showAtLocation(car_adress, Gravity.CENTER, 0, 0);
        location_one = (LinearLayout)pop.findViewById(R.id.location_one);
        location_one.setOnClickListener(this);
        location_two = (LinearLayout)pop.findViewById(R.id.location_two);
        location_two.setOnClickListener(this);
        location_three = (LinearLayout)pop.findViewById(R.id.location_three);
        location_three.setOnClickListener(this);
        location_four = (LinearLayout)pop.findViewById(R.id.location_four);
        location_four.setOnClickListener(this);
        popupWindow3.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

}
