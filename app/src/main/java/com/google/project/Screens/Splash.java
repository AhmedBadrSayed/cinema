package com.google.project.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.project.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread timer = new Thread() {
            public void run() {

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent login=new Intent(Splash.this, Posters.class);
                    startActivity(login);
                }
            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
