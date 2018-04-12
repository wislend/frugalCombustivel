package com.example.deyvi.frugalcombustive;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.deyvi.frugalcombustive.util.ConstantsAplication;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {


    @AfterViews
    public void init(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(),MainActivity_.class));
                finish();
            }
        }, ConstantsAplication.SPLASH_TIME_OUT);
    }

}
