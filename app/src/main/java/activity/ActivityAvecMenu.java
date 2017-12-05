package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.i162174.robot.R;

import autres.Robot;
//Tanguy was there
public abstract class ActivityAvecMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initialisationMenu() {
        Button btn_menu_scenario = (Button) findViewById(R.id.btn_menu_scenario);
        Button btn_menu_joystick = (Button) findViewById(R.id.btn_menu_joystick);
        Button btn_menu_apropos = (Button) findViewById(R.id.btn_menu_apropos);
        Button btn_menu_quitter = (Button) findViewById(R.id.btn_menu_quitter);

        btn_menu_scenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityAvecMenu.this, ScenarioActivity.class));
                finish();
            }
        });

        btn_menu_joystick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityAvecMenu.this, ControlleurActivity.class));
                finish();
            }
        });

        final String nameClass = getClass().getName();
        btn_menu_apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityAvecMenu.this, CreditActivity.class).putExtra("From", nameClass));
                finish();
            }
        });

        btn_menu_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Robot.deconnectionRobot(ActivityAvecMenu.this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    finishAndRemoveTask();
                }else
                    finish();
                System.exit(0);
            }
        });

    }

}
