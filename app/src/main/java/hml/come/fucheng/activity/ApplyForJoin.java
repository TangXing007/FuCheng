package hml.come.fucheng.activity;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import cz.msebera.android.httpclient.Header;
import hml.come.fucheng.R;
import hml.come.fucheng.activityMyselfChild.PersonDataActivity;
import hml.come.fucheng.net_work.HttpClient;
import hml.come.fucheng.net_work.NetUrl;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by TX on 2017/7/19.
 */
@RuntimePermissions
public class ApplyForJoin extends BaseActivity implements View.OnClickListener {
    private TextView textView, commit_button;
    private ImageView cameraImage;
    private EditText company_name, location, fuzeren, phone, password;
    private byte[] bytes;
    @Override
    public void onCreate(Bundle savedInsatanceState){
        super.onCreate(savedInsatanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_for_join);
        immersion();
        back();
        textView = (TextView)findViewById(R.id.title_text);
        textView.setText("申请加入");
        company_name = (EditText)findViewById(R.id.qiyemingcheng);
        location = (EditText)findViewById(R.id.location);
        fuzeren = (EditText)findViewById(R.id.fuzeren);
        phone = (EditText)findViewById(R.id.phone);
        password = (EditText)findViewById(R.id.denglu_password);
        cameraImage = (ImageView)findViewById(R.id.camera_iamge);
        cameraImage.setOnClickListener(this);
        commit_button = (TextView)findViewById(R.id.submit_button);
        commit_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_iamge:
                changeHeadIcon();
                break;
            case R.id.submit_button:
                if (company_name == null || company_name.equals("") || location == null ||
                        location.equals("") || fuzeren == null || fuzeren.equals("") ||
                        phone == null || phone.equals("") || password == null || password.equals("")
                        || bytes == null || bytes.equals("")){
                    Toast.makeText(ApplyForJoin.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    imageCommit();
                }
        }
    }

    private void changeHeadIcon(){
        final CharSequence[] items = {"相册", "拍照"};
        AlertDialog dialog = new AlertDialog.Builder(ApplyForJoin.this)
                .setTitle("选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            ApplyForJoinPermissionsDispatcher.photoAlbumWithCheck(ApplyForJoin.this);
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
        Toast.makeText(ApplyForJoin.this,
                "获取权限失败，可能无法打开相册", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        int length = inputStream.available();
                        bytes = new byte[length];
                        inputStream.read(bytes);
                        cameraImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, length));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cameraImage.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                break;
            case 3:
                if (data != null ){
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (bitmap != null){
                        cameraImage.setImageBitmap(bitmap);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        bytes = byteArrayOutputStream.toByteArray();
                    }
                }
                break;
        }
    }

    private void imageCommit(){
        RequestParams imageparams = new RequestParams();
        //String imageBASE64 = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
        imageparams.put("enterprise_name", company_name.getText());
        imageparams.put("address", location.getText());
        imageparams.put("name", fuzeren.getText());
        imageparams.put("phone", phone.getText());
        imageparams.put("password", password.getText());
        imageparams.put("head", new ByteArrayInputStream(bytes));
        HttpClient.get_istance().post(NetUrl.IMAGE_COMMIT, imageparams,
                new JsonHttpResponseHandler(){
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        Toast.makeText(ApplyForJoin.this,"提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {
                       Toast.makeText(ApplyForJoin.this,"提交失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ApplyForJoinPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
