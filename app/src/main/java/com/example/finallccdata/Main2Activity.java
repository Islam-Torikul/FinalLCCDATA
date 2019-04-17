package com.example.finallccdata;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    ImageView imageDisplay;
    EditText txtEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtEditor=(EditText)findViewById(R.id.editText);


        // Taking the picture from first layout by this segment
        imageDisplay = (ImageView) findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Bitmap image = (Bitmap) extras.get("image");
            if (image != null) {
                imageDisplay.setImageBitmap(image);
            }
        }

    }

    public void write (View view){

        // taking pic format data from previous activity
        String retake = getIntent().getExtras().getString("format");

        String state;
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath()+"/paddyData");
            if (!Dir.exists())
            {
                Dir.mkdir();
            }
            File file = new File(Dir,"Value.txt");
            String Message = txtEditor.getText().toString();
            String frt = retake+" "+Message+" ";
            try
            {
                // May be here i have to save check whether previous value have or not
                FileOutputStream fileOutputStream = new FileOutputStream(file,true);

                fileOutputStream.write( frt.getBytes());
                fileOutputStream.close();
                txtEditor.setText(" ");
                Toast.makeText(getApplicationContext(),"Saved SD Card ",Toast.LENGTH_LONG).show();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"SD Card Not found",Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(intent);
    }




}
