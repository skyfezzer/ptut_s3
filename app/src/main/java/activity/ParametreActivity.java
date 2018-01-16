package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.example.i162174.robot.R;

public class ParametreActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_parametre);

        Preference ajouterCartePref = findPreference("ajouterCarte");
        Preference supprimerCartePref = findPreference("supprimerCarte");
        Preference checkboxDebug = findPreference("checkboxDebug");

        checkboxDebug.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ParametreActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(newValue.toString().equals("true")){
                    editor.putBoolean("debug", true);

                }else{
                    editor.putBoolean("debug", false);
                }
                editor.commit();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(ParametreActivity.this, ConnectionActivity.class));
        finish();
    }

}

