package com.example.download.downloadabout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends Activity {

    @Override
    public void onCreate(Bundle splashBundle) {
        super.onCreate(splashBundle);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent startMain = new Intent(SplashScreen.this,MainActivity.class);
                SplashScreen.this.startActivity(startMain);
                SplashScreen.this.finish();
            }
        }, 2000);
    }
}