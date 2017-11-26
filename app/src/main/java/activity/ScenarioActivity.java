package activity;

import android.os.Bundle;

import com.example.i162174.robot.R;

public class ScenarioActivity extends ActivityAvecMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        initialisationMenu();
    }

}