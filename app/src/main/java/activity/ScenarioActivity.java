package activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.i162174.robot.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import autres.AdapterCarte;
import autres.ButtonCarte;
import autres.Carte;
import autres.Robot;

public class ScenarioActivity extends ActivityAvecMenu {

    private static final String FICHIER_SCENARIO = "scenario.txt";
    private LinearLayout layoutFonction;
    private ListView listViewScenario;
    public ArrayList<ButtonCarte> listeCarte;
    public ArrayList<Carte> listeCarteScenario;
    public AdapterCarte adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        initialisationMenu();

        layoutFonction = (LinearLayout) findViewById(R.id.layoutFonction);
        listViewScenario = (ListView) findViewById(R.id.listViewScenario);
        Button btn_save = (Button) findViewById(R.id.btn_save);
        Button btn_load = (Button) findViewById(R.id.btn_load);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sauvegarderScenario();
            }
        });

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chargerScenario();
            }
        });

        listeCarteScenario = new ArrayList<>();

        adapter = new AdapterCarte(this, listeCarteScenario);
        listViewScenario.setAdapter(adapter);

        initialisationListeFonctionEtCarte();
    }

    private void initialisationListeFonctionEtCarte() {
        ButtonCarte avancer = new ButtonCarte(this, new Carte("Avancer", Robot.AVANCER));
        ButtonCarte reculer = new ButtonCarte(this, new Carte("Reculer", Robot.RECULER));
        ButtonCarte tournerG = new ButtonCarte(this, new Carte("Tourner a gauche", Robot.TOURNER_A_GAUCHE));
        ButtonCarte tournerD = new ButtonCarte(this, new Carte("Tourner a droite", Robot.TOURNER_A_DROITE));
        ButtonCarte arreter = new ButtonCarte(this, new Carte("Arreter", Robot.ARRETER));
        ButtonCarte tournerB = new ButtonCarte(this, new Carte("Tourner le bras", Robot.TOURNER_LE_BRAS));
        ButtonCarte pause = new ButtonCarte(this, new Carte("Pause", Robot.PAUSE));
        ButtonCarte detecterSon = new ButtonCarte(this, new Carte("Detecter un son", Robot.DETECTER_SON));
        ButtonCarte bip = new ButtonCarte(this, new Carte("Bipper", Robot.BIP));
        ButtonCarte musique = new ButtonCarte(this, new Carte("Jouer Musique", Robot.MUSIQUE));

        listeCarte = new ArrayList<>();
        listeCarte.add(avancer);
        listeCarte.add(reculer);
        listeCarte.add(tournerG);
        listeCarte.add(tournerD);
        listeCarte.add(arreter);
        listeCarte.add(tournerB);
        listeCarte.add(pause);
        listeCarte.add(detecterSon);
        listeCarte.add(bip);
        listeCarte.add(musique);

        for(final ButtonCarte btnCarte : listeCarte){
            btnCarte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listeCarteScenario.add(btnCarte.getCarte());
                    adapter.notifyDataSetChanged();
                }
            });
            layoutFonction.addView(btnCarte);
        }


        ImageView img_qrcode =  (ImageView) findViewById(R.id.img_qrcode);
        img_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

                    startActivityForResult(intent, 0);
                }catch(Exception e){
                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                }
            }
        });

        ButtonCarte carteEnvoyer = new ButtonCarte(this, new Carte("Envoyer", ""));
        carteEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envoyerScenario();
            }
        });
        layoutFonction.addView(carteEnvoyer);
    }

    private void sauvegarderScenario(){
        FileOutputStream output;
        String json = new Gson().toJson(listeCarteScenario);
        try {
            output = openFileOutput(FICHIER_SCENARIO, MODE_PRIVATE);
            output.write(json.getBytes());
            output.close();
            Toast.makeText(this, "Sauvegarde réussi !", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not Found exception", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Erreur sauvegarde !", Toast.LENGTH_SHORT).show();
        }

    }

    private void chargerScenario(){
        String readFile = "";
        try {
            FileInputStream fis = openFileInput(FICHIER_SCENARIO);
            int i;
            while((i = fis.read()) != -1) {
                readFile = readFile + (char)i;
            }
            fis.close();
            Type type = new TypeToken<ArrayList<Carte>>(){}.getType();
            listeCarteScenario = new Gson().fromJson(readFile, type);
            adapter = new AdapterCarte(this, listeCarteScenario);
            listViewScenario.setAdapter(adapter);
            Toast.makeText(this, "Chargement scénario effectué", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void envoyerScenario() {
        for(Carte c : listeCarteScenario){
            Robot.envoyerCommande(this, c.getActionRobot());
        }
    }

//    protected void checkResultFlashCode(String result){
//        Carte c;
//        switch(result){
//            case "AVANCER":
//                c = new Carte(this, Robot.AVANCER, "Avancer");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            case "RECULER":
//                c = new Carte(this, Robot.RECULER, "Reculer");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            case "TOURNERGAUCHE":
//                c = new Carte(this, Robot.TOURNER_A_GAUCHE, "Tourner à gauche");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            case "TOURNERDROITE":
//                c = new Carte(this, Robot.TOURNER_A_DROITE, "Tourner à droite");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            case "ARRETER":
//                c = new Carte(this, Robot.ARRETER, "Arrêter");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            case "TOURNERBRAS":
//                c = new Carte(this, Robot.TOURNER_LE_BRAS, "Tourner le bras");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            case "PAUSE":
//                c = new Carte(this, Robot.PAUSE, "Pause");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            case "DETECTERSON":
//                c = new Carte(this, Robot.DETECTER_SON, "Détecter un son");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            case "BIP":
//                c = new Carte(this, Robot.BIP, "Bipper");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            case "MUSIQUE":
//                c = new Carte(this, Robot.MUSIQUE, "Musique");
//                listeCarteScenario.add(c);
//                adapter.notifyDataSetChanged();
//                break;
//            default:
//                Toast.makeText(this, "Le code flashé est mauvais !", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
//            checkResultFlashCode(data.getStringExtra("SCAN_RESULT"));
        }
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Action annulée", Toast.LENGTH_SHORT).show();
        }
    }

}