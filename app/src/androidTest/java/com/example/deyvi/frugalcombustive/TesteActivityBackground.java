package com.example.deyvi.frugalcombustive;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;

import com.example.deyvi.frugalcombustive.ui.activitys.LoadingBackground;
import com.robotium.solo.Solo;

/**
 * Created by WislenDouglasdeSouza on 13/04/2018.
 */

public class TesteActivityBackground extends ActivityInstrumentationTestCase2<LoadingBackground> {


    public TesteActivityBackground(Class<LoadingBackground> activityClass) {
        super(activityClass);
    }


    private Solo solo;


    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }


    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


    @Smoke
    public void testaLista() throws Exception{
        solo.waitForActivity("LoadingBackground", 5000);
        assertTrue(solo.waitForText("Marca 0"));
        solo.goBack();


    }



}
