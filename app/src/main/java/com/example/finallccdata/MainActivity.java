package com.example.finallccdata;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnCaptureImage;
    private static final int CAMERA_REQUEST = 100;
    Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCaptureImage = (Button) findViewById(R.id.btn);


        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            thumbnail = (Bitmap) data.getExtras().get("data");

            // for sending to second activity
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            intent.putExtra("image", thumbnail);


            SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
            String pname = sdf.format(new Date());
            intent.putExtra("format", pname);

            String root = Environment.getExternalStorageDirectory().toString();
            File folder = new File(root+"/paddy_image");
            folder.mkdirs();

            File my_file = new File(folder, pname +".png");
            startActivity(intent);

            try {
                FileOutputStream stream = new FileOutputStream(my_file);
                //SaveImage(my_file);
                thumbnail.compress(Bitmap.CompressFormat.PNG,100,stream);
                //new AndroidBmpUtil().save(thumbnail,stream);
                stream.flush();
                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }



}
