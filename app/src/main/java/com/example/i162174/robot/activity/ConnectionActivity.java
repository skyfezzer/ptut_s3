package com.example.i162174.robot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.i162174.robot.R;

public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        Button btn_connecter = (Button) findViewById(R.id.btn_connecter);
        btn_connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btn_apropos = (Button) findViewById(R.id.btn_apropos);
        btn_apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConnectionActivity.this, CreditActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed(){
    }

}