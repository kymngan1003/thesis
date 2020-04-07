package com.burhanrashid52.photoeditor;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;

public interface ST {
    public Bitmap recognizeImage(Bitmap bitmap);
    public Bitmap rescontructImage(float[] outputs1Dim);
    public ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap);
    public void enableStatLogging(boolean debug);
    public void close();
}
