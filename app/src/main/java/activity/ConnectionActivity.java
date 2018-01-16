package activity;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.i162174.robot.R;

import autres.Robot;

import static android.widget.Toast.LENGTH_LONG;

public class ConnectionActivity extends AppCompatActivity {

    public BluetoothAdapter bluetoothAdapter;
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            creerDialogPasDeBluetooth();
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final Boolean debug = preferences.getBoolean("debug", false);

        Button btn_connecter = (Button) findViewById(R.id.btn_connecter);
        btn_connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!debug) connexionBluetooth();
                else{
                    startActivity(new Intent(ConnectionActivity.this, ScenarioActivity.class));
                    finish();
                }
            }
        });

        final String nameClass = getClass().getName();
        Button btn_apropos = (Button) findViewById(R.id.btn_apropos);
        btn_apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConnectionActivity.this, CreditActivity.class).putExtra("From", nameClass));
                finish();
            }
        });

        ImageView btn_parametre = (ImageView) findViewById(R.id.btn_parametre);
        btn_parametre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConnectionActivity.this, ParametreActivity.class));
                finish();
            }
        });

    }

    private void creerDialogPasDeBluetooth() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(getString(R.string.connection_dialog_pasbluetooth_titre))
                .setMessage(getString(R.string.connection_dialog_pasbluetooth_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            finishAndRemoveTask();
                        }else
                            finish();
                        System.exit(0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void connexionBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
        }else
            Robot.connectionRobot(this, bluetoothAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            return;
        if (resultCode == RESULT_OK){ // L'user a activé le bluetooth
            Robot.connectionRobot(ConnectionActivity.this, bluetoothAdapter);
        } else { // L'user n'a pas activé le bluetooth
            Toast.makeText(this, "Veuillez activer votre bluetooth !", LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed(){
    }

}