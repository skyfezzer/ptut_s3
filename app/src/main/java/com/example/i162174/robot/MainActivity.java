package com.example.i162174.robot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoImageView = (ImageView)findViewById(R.id.logoImageView);

        new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {}
                Intent intent = new Intent(MainActivity.this, ConnectionActivity.class);
                startActivity(intent);
            }}.start();

    }

}

