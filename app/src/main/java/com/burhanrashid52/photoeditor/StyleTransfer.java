package com.burhanrashid52.photoeditor;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.RectF;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;
import android.content.res.AssetManager;

import android.util.Log;

import com.burhanrashid52.photoeditor.base.BaseActivity;

import java.io.IOException;

//import org.tensorflow.contrib.android.TensorFlowInferenceInterface;
import org.tensorflow.lite.Interpreter;

public class    StyleTransfer  extends BaseActivity implements  ST{
    String msg = "Android : ";
    private static final String TAG = "StyleTransfer";
    // Config values.

    private final int inputSize;
    private int imageMean;
    private float imageStd;

    // Pre-allocated buffers.

    private final int[] intValues ;
    private final float[] floatValues ;

    private final float[][][][] outputs ;

    private boolean runStats = false;

    private final Interpreter interpreter;

    private StyleTransfer(Interpreter interpreter) {
        this.interpreter = interpreter;
        this.inputSize = 256;

        this.floatValues = new float[inputSize * inputSize *3];
        this.intValues = new int [inputSize *inputSize];
        this.outputs = new float[1][inputSize][inputSize][3];
        this.imageMean = 0;
        this.imageStd = 255.f;
    }


    public static StyleTransfer styletransfer(AssetManager assetManager, String pathModel) throws  IOException{
        ByteBuffer byteBuffer = loadModelFile(assetManager, pathModel);
        Interpreter interpreter = new Interpreter(byteBuffer);
        return new StyleTransfer(interpreter);
    }

    private static ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    @Override
    public Bitmap recognizeImage(final Bitmap bitmap) {

        ByteBuffer byteBuffer = convertBitmapToByteBuffer(bitmap);
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        if (bitmap != null) {
            interpreter.run(byteBuffer, outputs);
        }

        float[][][] res = outputs[0];
        float[] outputs1Dim = new float[inputSize*inputSize*3];
        int count = 0;
        for (int i = 0; i<inputSize;i++)
            for (int j = 0; j<inputSize;j++)
                for (int k = 0 ; k< 3;k++)
                {
                    outputs1Dim[count] = res [i][j][k];
                    count = count + 1;
                }
        return rescontructImage(outputs1Dim);
    }

    @Override
    public ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(256*256*3*4);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] pixels = new int[256*256];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int pixel : pixels) {
            float rChannel = ((pixel >> 16) & 0xFF) / 255.f;
            float gChannel = ((pixel >> 8) & 0xFF) / 255.f;
            float bChannel = ((pixel) & 0xFF) / 255.f;
            byteBuffer.putFloat(rChannel);
            byteBuffer.putFloat(gChannel);
            byteBuffer.putFloat(bChannel);
        }
        return byteBuffer;
    }

    @Override
    public Bitmap rescontructImage(float[] outputs1Dim){
        System.out.println(outputs1Dim[0]);
        int[] intoutputs = new int[inputSize*inputSize*3];

        for (int i = 0; i < inputSize*inputSize; ++i) {
            intoutputs[i * 3 + 0] =((Math.round( outputs1Dim[i*3 +0]*imageStd +imageMean) ));
            intoutputs[i * 3 + 1] = ((Math.round( outputs1Dim[i*3 +1]*imageStd +imageMean) ) );
            intoutputs[i * 3 + 2] = ((Math.round(outputs1Dim[i*3 +2]*imageStd +imageMean) ) );
        }
        System.out.println(intValues[0]);


        for (int i=0; i<inputSize*inputSize;++i){

            int sign = intValues[i] >> 24;
            if (sign == 0)
                intValues[i] = ((intoutputs[i*3 + 1] << 8) | (intoutputs[i*3 + 2] << 0) ) + ((intoutputs[i*3 + 0] << 16) & 0x7FFFFF) + ((intoutputs[i*3 + 0] << 16) >> 23)*(-8388608);
            else
                intValues[i] = ((intoutputs[i*3 + 0] << 16) | (intoutputs[i*3 + 1] << 8 ) | (intoutputs[i*3 + 2])) - 16777216;
        }


        Bitmap bitmap = Bitmap.createBitmap(inputSize,inputSize, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        return  bitmap;
    }


    @Override
    public void enableStatLogging(boolean debug) {
        runStats = debug;
    }


    @Override
    public void close() {
        interpreter.close();
    }
}

