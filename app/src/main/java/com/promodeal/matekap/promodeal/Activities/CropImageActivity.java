package com.promodeal.matekap.promodeal.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.isseiaoki.simplecropview.CropImageView;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.R;

import java.io.IOException;

public class CropImageActivity extends Activity {
    public static final String TAG = CropImageActivity.class.getSimpleName();

    private final int MAX_WIDTH = 2500;
    private final int MAX_HEIGHT = 2500;

    protected String imgPath;
    protected Bitmap checkedImg = null;
    //    widgets
    CropImageView cropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crop_image);
//        findViews
        cropImageView = (CropImageView) findViewById(R.id.cropimage_acty_cropimgview);
//        get imgPath from the calling activity
        imgPath = getIntent().getStringExtra("EXTRA");

        cropImageView.setImageBitmap(getRotatedImage(BitmapFactory.decodeFile(imgPath), imgPath));
    }

    private Bitmap getRotatedImage(Bitmap source, String imgPath){
        Log.d(TAG, "getRotatedImage(" + imgPath + ")");
//        detect which orientation the image is set on
        try {
//            create an Exchangeable Image File Format from the loaded image
            ExifInterface exifInterface = new ExifInterface(imgPath);
//            get the current orientation state
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            /**
             * Phone cameras are landscape, portrait photos become landscape,
             * and landscape ones rotate 180 degree
             */
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90 :
//                    render the image with required resolution and rotation
                    checkImageResolution(source);
                    Log.d(TAG,checkedImg.toString());
                    return rotateImage(checkedImg, 90F);

                case ExifInterface.ORIENTATION_ROTATE_180 :

                    checkImageResolution(source);
                    Log.d(TAG, checkedImg.toString());
                    return rotateImage(checkedImg, 180F);

                default :
//                    ORIENTATION_UNDEFINED or any other cases
                    checkImageResolution(source);
                    return checkedImg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return checkedImg;
    }

    private void checkImageResolution(final Bitmap source) {
        Log.d(TAG, "checkImageResolution");
        new Thread(new Runnable() {
            @Override
            public void run() {

                int srcWidth = source.getWidth();
                int srcHeight = source.getHeight();
                Log.d(TAG,"srcWidth : " + srcWidth + " srcHeight : " + srcHeight);
                /**
                 * Landscape or portrait cases are both studied,
                 * to handle all pictures formats and orientations
                 */
                if (srcWidth >= MAX_WIDTH && srcHeight >= MAX_HEIGHT) {

                    Log.d(TAG, "ScaledSrcWidth : " + (int) Math.round(srcWidth * 0.6)
                            + " scaledSrcHeight : " + (int ) Math.round(srcHeight * 0.6));
                    checkedImg = Bitmap.createScaledBitmap(source, (int) Math.round(srcWidth * 0.6),
                            (int ) Math.round(srcHeight * 0.6), false);
                } else {

                    checkedImg = source;
                }
            }
        }).run();
    }

    private static Bitmap rotateImage(Bitmap source, float angle) {
        Log.d(TAG, "rotateImage(" + source.toString() + ", " + angle + ")");
        Bitmap rotatedImg;
        Matrix matrix = new Matrix();
//        set the desired rotation
        matrix.postRotate(angle);
        rotatedImg = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, false);

        return rotatedImg;
    }

    public void onBtnDoneClicked(View v){


        Intent resultIntent = new Intent();
//        TODO : Global variables for such procedure are bad
        resultIntent.putExtra(TAG, "Done");
        setResult(Activity.RESULT_OK, resultIntent);
        ((AppController) getApplication()).cropped = cropImageView.getCroppedBitmap();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        checkedImg.recycle();
    }
}
