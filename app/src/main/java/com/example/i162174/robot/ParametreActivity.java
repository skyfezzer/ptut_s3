package com.example.i162174.robot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class ParametreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation); // Evenement menu
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        menu.getItem(3).setChecked(true);
        menu.getItem(0).setChecked(false);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener // CLick element menu
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(ParametreActivity.this, ScenarioActivity.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_controller:
                    return true;
                case R.id.navigation_parametre:
                    return true;
                case R.id.navigation_credit:
                    return true;
            }
            return false;
        }

    };



}