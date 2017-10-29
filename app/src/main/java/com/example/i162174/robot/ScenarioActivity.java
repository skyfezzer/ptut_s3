package com.example.i162174.robot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class ScenarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation); // Evenement menu
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener // CLick element menu
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_controller:
                    return true;
                case R.id.navigation_parametre:
                    return true;
                case R.id.navigation_credit:
                    Intent i = new Intent(ScenarioActivity.this, CreditActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }

    };



}