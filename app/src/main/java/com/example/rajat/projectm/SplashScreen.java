package com.example.rajat.projectm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


/**
 * Created by Nishant on 27-07-2016.
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7B1FA2"));
        window.setNavigationBarColor(Color.parseColor("#8835ab"));
        final Animation animationFadeIn= AnimationUtils.loadAnimation(this,R.anim.fadein);
        animationFadeIn.start();
        final Animation animationFadeOut= AnimationUtils.loadAnimation(this,R.anim.fadeout);
        animationFadeIn.start();
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,WelcomeActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
