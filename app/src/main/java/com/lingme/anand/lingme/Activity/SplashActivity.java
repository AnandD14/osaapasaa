package com.lingme.anand.lingme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lingme.anand.lingme.R;

/**
 * Created by nepal on 20/10/2015.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timer=new Thread(){
            public void run(){
                try{
                sleep(5000);
                }catch(Exception e){

                }finally{
                   Intent in=new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        };
        timer.start();
    }
}
