package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.i162174.robot.R;

import java.io.File;
import java.util.ArrayList;

import adapter.AdapterFichier;
import autres.BottomNavigationViewHelper;
import autres.Robot;

public class EnregistrementActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrement);
        initNavigationMenu();

        File repertoire = getFilesDir();
        File[] files = repertoire.listFiles();

        if(files.length == 0)
            Toast.makeText(this, "Pas de sauvegarde trouvé !", Toast.LENGTH_SHORT).show();

        ListView listViewEnregistrement = (ListView) findViewById(R.id.listViewEnregistrement);
        final ArrayList<String> filesName = new ArrayList<>();
        for (File file : files) {
            filesName.add(file.getName());
        }

        AdapterFichier adapter = new AdapterFichier(this, filesName);
        listViewEnregistrement.setAdapter(adapter);

        listViewEnregistrement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(EnregistrementActivity.this, ScenarioActivity.class).putExtra("nomScenario", filesName.get(i)));
                finish();
            }
        });
    }

    private void initNavigationMenu() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        final String nameClass = getClass().getName();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_scenario:
                        startActivity(new Intent(EnregistrementActivity.this, ScenarioActivity.class));
                        finish();
                        break;
                    case R.id.navigation_joystick:
                        startActivity(new Intent(EnregistrementActivity.this, ControlleurActivity.class));
                        finish();
                        break;
                    case R.id.navigation_enregistrement:
                        break;
                    case R.id.navigation_apropos:
                        startActivity(new Intent(EnregistrementActivity.this, CreditActivity.class).putExtra("From", nameClass));
                        finish();
                        break;
                    case R.id.navigation_quitter:
                        Robot.deconnectionRobot(EnregistrementActivity.this);
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