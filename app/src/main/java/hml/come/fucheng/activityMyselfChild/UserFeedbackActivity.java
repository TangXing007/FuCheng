package hml.come.fucheng.activityMyselfChild;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import hml.come.fucheng.R;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by TX on 2017/7/31.
 */
@RuntimePermissions
public class UserFeedbackActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout back_button;
    private TextView commit_button, title_text;
    private EditText feedback_text;
    private ImageView feedback_image;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfeedback);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.bj));
        }
        back_button = (LinearLayout)findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        commit_button = (TextView)findViewById(R.id.feedback_commit_button);
        commit_button.setOnClickListener(this);
        feedback_text = (EditText)findViewById(R.id.feedback_text);
        feedback_image = (ImageView)findViewById(R.id.feedback_image);
        feedback_image.setOnClickListener(this);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("用户反馈");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                finish();
                break;
            case R.id.feedback_image:
                changeHeadIcon();
                break;
        }
    }

    private void changeHeadIcon(){
        final CharSequence[] items = {"相册", "拍照"};
        AlertDialog dialog = new AlertDialog.Builder(UserFeedbackActivity.this)
                .setTitle("选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            UserFeedbackActivityPermissionsDispatcher
                                    .photoAlbumWithCheck(UserFeedbackActivity.this);
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
        Toast.makeText(UserFeedbackActivity.this,
                "获取权限失败，可能无法打开相册", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
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
                        byte[] bytes = new byte[length];
                        inputStream.read(bytes);
                        feedback_image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, length));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    feedback_image.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                break;
            case 3:
                if (data != null ){
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (bitmap != null){
                        feedback_image.setImageBitmap(bitmap);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UserFeedbackActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
