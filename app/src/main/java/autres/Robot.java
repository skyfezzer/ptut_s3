package autres;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import activity.ScenarioActivity;

import static android.widget.Toast.LENGTH_LONG;

public class Robot {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

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
    private static final String DISCONNECT = "99";

    private static BluetoothSocket socket;
    private static OutputStream oStream;

    public static void connectionRobot(Context context, BluetoothAdapter bluetoothAdapter){
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (bluetoothDevice.getName().equals("EV3") || bluetoothDevice.getName().equals("NXT")) {
                try {
                    socket = creerSocket(bluetoothDevice);
                    assert socket != null;
                    socket.connect();
                    envoyerCommande(context, "01");
                    context.startActivity(new Intent(context, ScenarioActivity.class));
                    ((Activity) context).finish();
                    Toast.makeText(context, "Connecté au robot !", LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Problème d'ouverture de connexion", LENGTH_LONG).show();
                }
                break;
            }
        }

        if (oStream == null) {
            Toast toast = Toast.makeText(context, "Aucun EV3 associé trouvé", LENGTH_LONG);
            toast.show();
        }

    }

    private static BluetoothSocket creerSocket(BluetoothDevice bluetoothDevice){
        try {
            Method m = bluetoothDevice.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(bluetoothDevice, MY_UUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Permet l'émission de la commande voulut au robot
    public static void envoyerCommande(Context context, String command) {
        command +="\n";
        Log.e("MESSAGE ENVOYÉ :", command);
        if (socket == null){
            Toast.makeText(context, "Vous n'êtes pas connecté au robot", LENGTH_LONG).show();
            return;
        }
        try {
            oStream = socket.getOutputStream();
            oStream.write(command.getBytes());
            oStream.flush();
            oStream.close();
            Toast.makeText(context, "Envoyé", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void deconnectionRobot(Context context) {
        if (socket != null) {
            envoyerCommande(context, ARRETER);
            envoyerCommande(context, DISCONNECT);
            try {
                oStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            oStream = null;
            socket = null;
            Toast.makeText(context, "Déconnexion du robot", LENGTH_LONG).show();
        }
    }
}
