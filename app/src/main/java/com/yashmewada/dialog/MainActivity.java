package com.yashmewada.dialog;

import android.app.Activity;
import android.content.Intent;
import android.database.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yashmewada.dialog.Croper.Croperino;
import com.yashmewada.dialog.Croper.CroperinoConfig;
import com.yashmewada.dialog.Croper.CroperinoFileUtil;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.demo_image);

        new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/Punchit/", "/sdcard/Punchit/");
        CroperinoFileUtil.verifyStoragePermissions(this);
        CroperinoFileUtil.setupDirectory(MainActivity.this);
    }

    public void demo(View view) {
        Croperino.prepareGallery(MainActivity.this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    /* Parameters of runCropImage = File, Activity Context, Image is Scalable or Not, Aspect Ratio X, Aspect Ratio Y, Button Bar Color, Background Color */
                    Croperino.runCropImage(CroperinoFileUtil.getmFileTemp(), MainActivity.this, true, 1, 2, 0, 0);
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    CroperinoFileUtil.newGalleryFile(data, MainActivity.this);
                    Croperino.runCropImage(CroperinoFileUtil.getmFileTemp(), MainActivity.this, false, 1, 2, 0, 0);
                    Log.d("FILE", "onActivityResult: " + CroperinoFileUtil.getmFileTemp().getAbsolutePath());
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getmFileTemp());
                    Log.d("FILE", "onActivityResult: " + CroperinoFileUtil.getmFileTemp().getAbsolutePath());
                    imageView.setImageURI(i);
                    //Do saving / uploading of photo method here.
                    //The image file can always be retrieved via CroperinoFileUtil.getmFileTemp()
                }
                break;
            default:
                break;
        }
    }
}
