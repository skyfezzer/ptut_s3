package activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.i162174.robot.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import static android.widget.Toast.LENGTH_LONG;

public class ConnectionActivity extends AppCompatActivity {

    public BluetoothAdapter bluetoothAdapter;
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;

    public static BluetoothSocket nxtBTsocket = null;
    public static DataOutputStream nxtDos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                finishAndRemoveTask();
            }else
                finish();
            System.exit(0);
        }

        Button btn_connecter = (Button) findViewById(R.id.btn_connecter);
        btn_connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connexionBluetooth();
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

    }

    private void connexionBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
        }else{
            connectionAuNXT();
            startActivity(new Intent(this, ScenarioActivity.class));
            finish();
        }
    }

    private void connectionAuNXT() {
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        BluetoothDevice nxtDevice = null;

        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (bluetoothDevice.getName().equals("NXT")) {
                nxtDevice = bluetoothDevice;
                break;
            }
        }

        if (nxtDevice == null) {
            Toast toast = Toast.makeText(this, "Aucun NXT associé trouvé", LENGTH_LONG);
            toast.show();
            return;
        }

        try {
            nxtBTsocket = nxtDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            nxtBTsocket.connect();
            nxtDos = new DataOutputStream(nxtBTsocket.getOutputStream());
            startActivity(new Intent(this, ScenarioActivity.class));
            finish();
        } catch (IOException e) {
            Toast.makeText(this, "Problème d'ouverture de connexion", LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            return;
        if (resultCode == RESULT_OK){ // L'user a activé le bluetooth
            connectionAuNXT();
        } else { // L'user n'a pas activé le bluetooth
            Toast.makeText(this, "Veuillez activer votre bluetooth !", LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed(){
    }

}