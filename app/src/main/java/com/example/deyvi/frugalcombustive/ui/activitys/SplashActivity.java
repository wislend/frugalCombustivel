package com.example.deyvi.frugalcombustive.ui.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.example.deyvi.frugalcombustive.R;
import com.example.deyvi.frugalcombustive.util.ConstantsAplication;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;


@SuppressLint("Registered")
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


    public void CarregarLista(){



    }






}
