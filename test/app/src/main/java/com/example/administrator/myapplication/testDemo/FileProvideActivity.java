package com.example.administrator.myapplication.testDemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.myapplication.BaseActivity;
import com.example.administrator.myapplication.BuildConfig;
import com.example.administrator.myapplication.R;

import java.io.File;

public class FileProvideActivity extends BaseActivity {
    private static final int REQUEST_TAKE_PHOTO = 10001;
    private ImageView iv_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_provide);
        iv_photo = findViewById(R.id.iv_photo);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_skipToCamera:
                openCamera();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    Bundle bundle=data.getExtras();
                    if (null != bundle) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        iv_photo.setImageBitmap(bitmap);
                    }
                    break;
            }
        }

    }

    private void openCamera() {//over 24
        //必须在file_paths.xml里设置了此权限
        String filePath = Environment.getExternalStorageDirectory() + "/application/" + System.currentTimeMillis() + ".jpg";
        Log.e("tag", filePath);
        File currentFile = new File(filePath);
        if (!currentFile.getParentFile().exists()) {
            currentFile.getParentFile().mkdir();
        }
        // /storage/emulated/0/myapplication/1530169339900.jpg
        Log.e("tag", currentFile.getAbsolutePath());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //参数2：对应清单文件里的authorities 参数3：要共享的文件，并且这个文件一定位于第二步我们在 file_paths 文件中添加的子目录里面
        Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", currentFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, FileProvideActivity.class));
    }
}
