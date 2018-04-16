package com.example.deyvi.frugalcombustive.ui.activitys;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.specials.in.LandingAnimator;
import com.example.deyvi.frugalcombustive.R;
import com.example.deyvi.frugalcombustive.util.ConstantsAplication;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_apresentacao)
public class Apresentacao extends AppCompatActivity {

    @ViewById
    TextView txtIcon;
    @ViewById
    ImageView imgSplash;


    @AfterViews
    public void init(){
        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(5)
                .playOn(findViewById(R.id.imgSplash));

              new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(),Home_.class));
                finish();
            }
        }, ConstantsAplication.SPLASH_TIME_OUT);


    }


}
