package activity;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.i162174.robot.R;

import java.io.DataOutputStream;
import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;

public class ScenarioActivity extends ActivityAvecMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        initialisationMenu();
    }

    // Permet l'émission de la commande voulut au robot
    public void sendNXTcommand(int command) {
        DataOutputStream nxtDos = ConnectionActivity.nxtDos;
        if (nxtDos == null)
            return;
        try {
            nxtDos.writeInt(command);
            nxtDos.flush();
        } catch (IOException ioe) {
            Toast.makeText(this, "Problème d'envoi de commande", LENGTH_LONG).show();
        }
    }
}