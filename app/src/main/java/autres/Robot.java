package autres;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import activity.ScenarioActivity;

import static android.widget.Toast.LENGTH_LONG;

public class Robot {

    public static final int AVANCER = 0;
    public static final int RECULER = 1;
    public static final int TOURNER_A_DROITE = 2;
    public static final int TOURNER_A_GAUCHE = 3;
    public static final int ARRETER = 4;
    public static final int TOURNER_LE_BRAS = 5;
    public static final int PAUSE = 7;
    public static final int DETECTER_SON = 8;
    public static final int BIP = 9;
    public static final int MUSIQUE = 10;
    public static final int DISCONNECT = 99;

    public static DataOutputStream ev3Dos;
    public static BluetoothSocket ev3BTsocket;

    public static void connectionRobot(Context context, BluetoothAdapter bluetoothAdapter){
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        BluetoothDevice ev3Device = null;

        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (bluetoothDevice.getName().equals("EV3") || bluetoothDevice.getName().equals("NXT")) {
                ev3Device = bluetoothDevice;
                break;
            }
        }

        if (ev3Device == null) {
            Toast toast = Toast.makeText(context, "Aucun EV3 associé trouvé", LENGTH_LONG);
            toast.show();
            return;
        }

        try {
            ev3BTsocket = ev3Device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            ev3BTsocket.connect();
            ev3Dos = new DataOutputStream(ev3BTsocket.getOutputStream());
            context.startActivity(new Intent(context, ScenarioActivity.class));
            ((Activity) context).finish();
            Toast.makeText(context, "Connection reussi", LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Problème d'ouverture de connexion", LENGTH_LONG).show();
        }
    }

    // Permet l'émission de la commande voulut au robot
    public static void envoyerCommande(Context context, int command) {
        if (ev3Dos == null)
            return;
        try {
            ev3Dos.writeInt(command);
            ev3Dos.flush();
        } catch (IOException ioe) {
            Toast.makeText(context, "Problème d'envoi de commande", LENGTH_LONG).show();
        }
    }

    public static void deconnectionRobot(Context context) {
        try {
            if (ev3BTsocket != null) {
                envoyerCommande(context, ARRETER);
                envoyerCommande(context, DISCONNECT);
                ev3BTsocket.close();
                ev3BTsocket = null;
            }
            ev3Dos = null;
        } catch (IOException e) {
            Toast toast = Toast.makeText(context, "Problème de fermeture de connexion", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
