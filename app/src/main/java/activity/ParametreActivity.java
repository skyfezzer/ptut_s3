package activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.i162174.robot.R;

public class ParametreActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_parametre);
    }

}

