package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.i162174.robot.R;

import autres.BottomNavigationViewHelper;
import autres.Robot;

public class ControlleurActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlleur);
        initNavigationMenu();
    }

    private void initNavigationMenu() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        final String nameClass = getClass().getName();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_scenario:
                        startActivity(new Intent(ControlleurActivity.this, ScenarioActivity.class));
                        finish();
                        break;
                    case R.id.navigation_joystick:
                        break;
                    case R.id.navigation_apropos:
                        startActivity(new Intent(ControlleurActivity.this, CreditActivity.class).putExtra("From", nameClass));
                        finish();
                        break;
                    case R.id.navigation_quitter:
                        Robot.deconnectionRobot(ControlleurActivity.this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            finishAndRemoveTask();
                        }else
                            finish();
                        System.exit(0);
                        break;
                }
                return false;
            }
        });
    }

}