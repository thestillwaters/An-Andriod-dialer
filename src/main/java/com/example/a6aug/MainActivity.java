package com.example.a6aug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //create a display for activity_main from Resource
        final EditText number = findViewById(R.id.editView);//phone number to call
        final int[] buttonID = {//connect with buttons from Layout
                R.id.button0, R.id.button1,R.id.button2, R.id.button3,
                R.id.button4, R.id.button5,R.id.button6, R.id.button7,
                R.id.button8, R.id.button9,R.id.button10,R.id.button11,
                R.id.button12,R.id.button13};
        Button[] button= new Button[30];//declare buttons including 0-9;

        for(int j=0; j<14;j++){
            button[j]  = findViewById(buttonID[j]);//resource id from activity_main.xml
        }

        int i = 0;
        while( i<14) {//avoid number.setText(... ) repeats
            if(i >= 0 && i<=9){ //what to do after press 0-9
                final String num = String.valueOf(i);
                button[i].setOnClickListener((view) -> number.setText(number.getText().toString() + num));
                //some languages like Hebrew and old Chinese read from right to left.
            }else if(i == 10){      //delete the last character
                button[10].setOnClickListener((view)->{
                    if(number.getText().length() >= 1) {
                        String s = number.getText().toString();//change char sequence to string
                        s = s.substring(0, s.length() - 1);//(begin ind, end ind)
                        number.setText( s);
                    }
                });
            } else if (i == 11){   //* button
                button[11].setOnClickListener(view -> number.setText( number.getText().toString()+ "*"));
            } else if(i == 12){   //# button
                button[12].setOnClickListener(view -> number.setText(number.getText().toString()+"#"));
            } else {//call button
                    button[13].setOnClickListener(view -> {
                        //for Dangerous permissions from API 23
                        if (Build.VERSION.SDK_INT >= 23 && //API level
                         checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            makecall();
                        }
                    });
                }
            i++;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            makecall();
        }
    }

    //create Intent to make phone calls
    private void makecall() {
        final EditText number = findViewById(R.id.editView);//find the Number string
        Intent i = new Intent();
        i.setAction(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+ number.getText().toString())); //get the number from UI
        startActivity(i);
    }
}

