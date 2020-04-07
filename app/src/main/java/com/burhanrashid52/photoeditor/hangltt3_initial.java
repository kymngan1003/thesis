package com.burhanrashid52.photoeditor;

import com.burhanrashid52.photoeditor.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class hangltt3_initial extends BaseActivity {

    private Button btnGallery;
    private Button btnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        makeFullScreen();

        setContentView(R.layout.hangltt3_initial);

        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnCamera = (Button) findViewById(R.id.btnCamera);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

    }

    public void openActivity(){
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    public void openActivity1(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

}
