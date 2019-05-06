package com.example.finallccdata;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.Mat.zeros;

public class Main3Activity extends AppCompatActivity {
    Button button;
    ImageView imageDisplay3;
    Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        if (OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"Open cv working ",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Open cv not working ",Toast.LENGTH_LONG).show();
        }
        button = (Button)findViewById(R.id.buttonseg);
        imageDisplay3 = (ImageView) findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            image = (Bitmap) extras.get("image");
            /*if (image != null) {
                imageDisplay3.setImageBitmap(image);
            }*/
        }
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS ) {
                // now we can call opencv code !

                //Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cmc7);
                Mat myMat = new Mat();
                Utils.bitmapToMat(image, myMat);
                Mat cvtcolormat = new Mat(); // new mat variable
                Imgproc.cvtColor(myMat, cvtcolormat,Imgproc.COLOR_RGB2HSV);

                Scalar lowerThreshold = new Scalar ( 36,0,0 );
                Scalar upperThreshold = new Scalar ( 86,255,255 );
                Mat inrangemat = new Mat();
                Core.inRange(cvtcolormat, lowerThreshold , upperThreshold, inrangemat);

                int n = (int)inrangemat.elemSize();
                int m = (int)inrangemat.elemSize1();

                // have to perform the basic image proceing 

                //Mat lastmat2 = zeros(inrangemat.size(),CV_8UC1);
                //Core.bitwise_and(myMat,lastmat,lastmat2,inrangemat);

                Utils.matToBitmap(inrangemat, image);
                imageDisplay3.setImageBitmap(image);

            } else {
                super.onManagerConnected(status);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_5,this, mLoaderCallback);
        // you may be tempted, to do something here, but it's *async*, and may take some time,
        // so any opencv call here will lead to unresolved native errors.
    }
}
