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
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.activity.BaseActivity;
import hml.come.fucheng.activityMyselfChild.PersonDataActivity;
import hml.come.fucheng.moudle.ImageBackData;
import hml.come.fucheng.moudle.MyMessageData;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.singleton.CustomInfo;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by TX on 2017/7/31.
 */
@RuntimePermissions
public class MyMessageActivity extends BaseActivity implements View.OnClickListener{
    private EditText company_name, id_number, name, phone, adress;
    private LinearLayout back_button;
    private TextView title_text, commit_button;
    private ImageView id_card_image, business_license;
    private MyMessageData messageData;
    private int index = 0;
    private byte[] bytes;
    private int length;
    private ImageBackData data;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        back();
        immersion();
        company_name = (EditText)findViewById(R.id.my_message_company_name);
        id_number = (EditText)findViewById(R.id.my_message_id_number);
        name = (EditText)findViewById(R.id.my_message_name);
        phone = (EditText)findViewById(R.id.my_message_phone);
        adress = (EditText)findViewById(R.id.my_message_company_adress);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("我的信息");
        commit_button = (TextView)findViewById(R.id.my_message_amend_button);
        commit_button.setOnClickListener(this);
        id_card_image = (ImageView)findViewById(R.id.id_card_image);
        id_card_image.setOnClickListener(this);
        business_license = (ImageView)findViewById(R.id.business_license);
        business_license.setOnClickListener(this);
        myMessage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_card_image:
                changeHeadIcon();
                index = 1;
                break;
            case R.id.business_license:
                changeHeadIcon();
                index = 2;
                break;
            case R.id.my_message_amend_button:
                amendMessage();
                break;
        }
    }

    private void myMessage(){
        RequestParams params = new RequestParams();
        params.put("id", CustomInfo.getInfo().getDealer_id());
        HttpClient.get_istance().post(NetUrl.DEALER_MY_MESSAGE, params, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                messageData = gson.fromJson(response.toString(), MyMessageData.class);
                Picasso.with(MyMessageActivity.this).load(NetUrl.HEAD + messageData.data.headimg)
                        .into(id_card_image);
                Picasso.with(MyMessageActivity.this).load(NetUrl.HEAD + messageData.data.head)
                        .into(business_license);
                company_name.setText(messageData.data.enterprise_name);
                id_number.setText(messageData.data.id_card);
                name.setText(messageData.data.name);
                phone.setText(messageData.data.phone);
                adress.setText(messageData.data.address);
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {

            }
        });
    }

    private void amendMessage(){

    }

    private void changeHeadIcon(){
        final CharSequence[] items = {"相册", "拍照"};
        AlertDialog dialog = new AlertDialog.Builder(MyMessageActivity.this)
                .setTitle("选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            MyMessageActivityPermissionsDispatcher.photoAlbumWithCheck
                                    (MyMessageActivity.this);
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
        Toast.makeText(MyMessageActivity.this,
                "获取权限失败，可能无法打开相册", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyMessageActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
                        length = inputStream.available();
                        bytes = new byte[length];
                        inputStream.read(bytes);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageCommit();
                    switch (index){
                        case 1:
                            id_card_image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, length));
                            id_card_image.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                        case 2:
                            business_license.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, length));
                            business_license.setScaleType(ImageView.ScaleType.FIT_XY);
                            break;
                    }
                }
                break;
            case 3:
                if (data != null ){
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (bitmap != null){
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        bytes = byteArrayOutputStream.toByteArray();
                        imageCommit();
                        switch (index){
                            case 1:
                                id_card_image.setImageBitmap(bitmap);
                                break;
                            case 2:
                                business_license.setImageBitmap(bitmap);
                                break;
                        }
                    }
                }
                break;
        }
    }

    private void imageCommit(){
        RequestParams imageparams = new RequestParams();
        String imageBASE64 = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
        imageparams.put("binaryImgFlow", imageBASE64);
        String a = NetUrl.IMAGE_COMMIT;
        HttpClient.get_istance().post(NetUrl.IMAGE_COMMIT, imageparams,
                new JsonHttpResponseHandler(){
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        Gson gson = new Gson();
                        data = gson.fromJson(response.toString(), ImageBackData.class);
                    }
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {
                        String b = "false";
                    }
                });
    }
}
