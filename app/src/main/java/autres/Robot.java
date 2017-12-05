package autres;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import activity.ScenarioActivity;

import static android.widget.Toast.LENGTH_LONG;

public class Robot {

    public static final String AVANCER = "00";
    public static final String RECULER = "01";
    public static final String TOURNER_A_DROITE = "02";
    public static final String TOURNER_A_GAUCHE = "03";
    public static final String ARRETER = "04";
    public static final String TOURNER_LE_BRAS = "05";
    public static final String PAUSE = "07";
    public static final String DETECTER_SON = "08";
    public static final String BIP = "09";
    public static final String MUSIQUE = "10";
    public static final String DISCONNECT = "99";

    private static TCPIPCommunication bluetooth = null;

    public static void connectionRobot(Context context, BluetoothAdapter bluetoothAdapter){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();


        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (bluetoothDevice.getName().equals("EV3") || bluetoothDevice.getName().equals("NXT")) {
                try {
                    bluetooth = new TCPIPCommunication(bluetoothDevice.getAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Problème d'ouverture de connexion", LENGTH_LONG).show();
                }
                break;
            }
        }

        if (bluetooth == null) {
            Toast toast = Toast.makeText(context, "Aucun EV3 associé trouvé", LENGTH_LONG);
            toast.show();
            return;
        }

        context.startActivity(new Intent(context, ScenarioActivity.class));
        ((Activity) context).finish();
        Toast.makeText(context, "Connection reussi", LENGTH_LONG).show();

    }

    // Permet l'émission de la commande voulut au robot
    public static void envoyerCommande(Context context, String command) {
        if (bluetooth == null)
            return;
        try {
            bluetooth.send(command);
        } catch (IOException ioe) {
            Toast.makeText(context, "Problème d'envoi de commande", LENGTH_LONG).show();
        }
    }

    public static void deconnectionRobot(Context context) {
        if (bluetooth != null) {
            envoyerCommande(context, ARRETER);
            envoyerCommande(context, DISCONNECT);
            bluetooth.close();
            bluetooth = null;
            Toast.makeText(context, "Déconnexion du robot", LENGTH_LONG).show();
        }
    }
}
