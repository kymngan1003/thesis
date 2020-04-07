//package com.burhanrashid52.photoeditor;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//
//import com.wonderkiln.camerakit.CameraKitError;
//import com.wonderkiln.camerakit.CameraKitEvent;
//import com.wonderkiln.camerakit.CameraKitEventListener;
//import com.wonderkiln.camerakit.CameraKitImage;
//import com.wonderkiln.camerakit.CameraKitVideo;
//import com.wonderkiln.camerakit.CameraView;
//
//import com.burhanrashid52.photoeditor.base.BaseActivity;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import java.io.File;
//import android.os.Environment;
//import java.io.IOException;
//import ja.burhanrashid52.photoeditor.SaveSettings;
//import ja.burhanrashid52.photoeditor.PhotoEditor;
//import ja.burhanrashid52.photoeditor.PhotoEditorView;
//import androidx.annotation.NonNull;
//
//import java.io.FileOutputStream;
//import java.io.ObjectOutputStream;
//
//import java.util.Random;
//
//import android.media.MediaScannerConnection;
//import android.media.MediaScannerConnection.MediaScannerConnectionClient;
//
//import android.app.Activity;
//
//import java.io.*;
//import java.util.*;
//import java.text.*;
//
//public class EditImageActivity extends BaseActivity{
//    String msg = "Android : ";
//    private Executor executor = Executors.newSingleThreadExecutor();
//    private CameraView cameraView;
//    private ImageButton btnCamera;
//    private ImageButton btnEdit;
//    PhotoEditor mPhotoEditor;
//    Uri mSaveImageUri;
//    private PhotoEditorView mPhotoEditorView;
//    private static final int PICK_REQUEST = 53;
//    private Bitmap bitmap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.hangltt3_main_ui);
//        Log.d(msg, "The onCreate() event");
//        cameraView = (CameraView) findViewById(R.id.CameraView);
//        btnCamera = (ImageButton) findViewById(R.id.imgCamera);
//        btnEdit = (ImageButton) findViewById(R.id.imgGallery);
////        mPhotoEditor = findViewById(R.id.photoEditorView);
//
//        cameraView.addCameraKitListener(new CameraKitEventListener() {
//            @Override
//            public void onEvent(CameraKitEvent cameraKitEvent) {
//
//            }
//
//
//            @Override
//            public void onError(CameraKitError cameraKitError) {
//
//            }
//            @Override
//            public void onImage(CameraKitImage cameraKitImage) {
//                //Take photo
//                bitmap = cameraKitImage.getBitmap();
////                bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, false);
//                if (bitmap != null)
//                    saveTempBitmap(bitmap);
////                    saveImage(bitmap);
////                    saveImage(bitmap, "123");
////                    saveImage();
//                else
//                    System.out.println("Bitmap is Null");
//
//            }
//            @Override
//            public void onVideo(CameraKitVideo cameraKitVideo) {
//
//            }
//        });
//
//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cameraView.captureImage();
//                System.out.println("Capture Image");
////                if (bitmap != null)
////                    saveTempBitmap(bitmap);
////                else
////                    System.out.println("Bitmap is Null");
////                saveImage();
//            }
//        });
//
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent();
////                intent.setType("image/*");
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
//                openActivity2();
//            }
//        });
//
//    }
//
//
//    public void saveTempBitmap(Bitmap bitmap) {
//        if (isExternalStorageWritable()) {
//            saveImage(bitmap);
//        }else{
//            //prompt the user or do something
//            System.out.println("External Storage Can not be Writable" );
//        }
//    }
//
//    private void saveImage(Bitmap finalBitmap) {
//
//
//        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
////            String paths = Environment.getExternalStorageDirectory()
////                    + File.separator + ""
////                    + System.currentTimeMillis() + ".png";
////            Uri uri = Uri.parse(paths);
////            File myDir = new File(uri.getPath());
//            File myDir = new File(Environment.getExternalStorageDirectory()
//                    + File.separator + ""
//                    + "/MyPhotoEditor");
////            String root = Environment.getExternalStorageDirectory().toString();
////            File myDir = new File(root + "/saved_images");
//
//            myDir.mkdirs();
//
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String fname = "Shutta_" + timeStamp + ".jpg";
//
//            File file = new File(myDir, fname);
//            if (file.exists()) file.delete();
//            try {
//                FileOutputStream out = new FileOutputStream(file);
//                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                out.flush();
//                out.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("Fall to compress");
//            }
//        }
//    }
//
//    /* Checks if external storage is available for read and write */
//    public boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        }
//        return false;
//    }
//
//
//    public void openActivity2(){
//        Intent intent = new Intent(this, Activity2.class);
//        startActivity(intent);
//    }
//
//    private void saveImage(Bitmap finalBitmap, String image_name) {
//
//        String root = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File(root);
//        myDir.mkdirs();
//        String fname = "Image-" + image_name+ ".jpg";
//        File file = new File(myDir, fname);
//        if (file.exists()) file.delete();
//        Log.i("LOAD", root + fname);
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void SaveImage(Bitmap finalBitmap) {
//
//
//
//        String root = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES).toString();
//        File myDir = new File(root + "/saved_images");
//        myDir.mkdirs();
//        Random generator = new Random();
//
//        int n = 10000;
//        n = generator.nextInt(n);
//        String fname = "Image-"+ n +".jpg";
//        File file = new File (myDir, fname);
//        if (file.exists ()) file.delete ();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
////             sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
////                 Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
//            out.flush();
//            out.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // Tell the media scanner about the new file so that it is
//        // immediately available to the user.
//        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
//                new MediaScannerConnection.OnScanCompletedListener() {
//                    public void onScanCompleted(String path, Uri uri) {
//                        Log.i("ExternalStorage", "Scanned " + path + ":");
//                        Log.i("ExternalStorage", "-> uri=" + uri);
//                    }
//                });
//    }
//
//    @SuppressLint("MissingPermission")
//    private void saveImage() {
//        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            showLoading("Saving...");
//            File file = new File(Environment.getExternalStorageDirectory()
//                    + File.separator + ""
//                    + System.currentTimeMillis() + ".png");
//            try {
//                file.createNewFile();
//
//                SaveSettings saveSettings = new SaveSettings.Builder()
//                        .setClearViewsEnabled(true)
//                        .setTransparencyEnabled(true)
//                        .build();
//
//                mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
//                    @Override
//                    public void onSuccess(@NonNull String imagePath) {
//                        hideLoading();
//                        showSnackbar("Image Saved Successfully");
//                        mSaveImageUri = Uri.fromFile(new File(imagePath));
//                        mPhotoEditorView.getSource().setImageURI(mSaveImageUri);
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        hideLoading();
//                        showSnackbar("Failed to save Image");
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//                hideLoading();
//                showSnackbar(e.getMessage());
//            }
//        }
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        cameraView.start();
//        Log.d(msg, "The onResume() event");
//    }
//
//    @Override
//    protected void onPause() {
//        cameraView.stop();
//        super.onPause();
//        Log.d(msg, "The onPause() event");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(msg, "The onDestroy() event");
//
//    }
//
//}
//
//


package com.burhanrashid52.photoeditor;

import com.burhanrashid52.photoeditor.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditImageActivity extends BaseActivity {

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
