package com.burhanrashid52.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.burhanrashid52.photoeditor.base.BaseActivity;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;
import android.content.Intent;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class CameraActivity extends BaseActivity {

    private ImageView btnCamera;
    private CameraView CameraView;
    private Bitmap bitmap;
    private String msg = "Camera" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangltt3_camera);

        btnCamera = (ImageButton) findViewById(R.id.btnCamera);

        CameraView = (CameraView) findViewById(R.id.CameraView);

        CameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }


            @Override
            public void onError(CameraKitError cameraKitError) {

            }
            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                //Take photo
                bitmap = cameraKitImage.getBitmap();
                openActivity(bitmap);
            }
            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraView.captureImage();
            }
        });

    }

    public void openActivity(Bitmap bmp){


        try {
            //Write file
            showLoading("Please wait to process!");
            String filename = "bitmap.png";
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Cleanup
            stream.close();
            bmp.recycle();

            //Pop intent
            Intent in1 = new Intent(this, Camera2.class);
            in1.putExtra("image", filename);
            hideLoading();
            startActivity(in1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        CameraView.start();
        Log.d(msg, "The onResume() event");
    }

    @Override
    protected void onPause() {
        CameraView.stop();
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");

    }

}
