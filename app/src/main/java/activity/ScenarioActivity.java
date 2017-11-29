package activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.i162174.robot.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import autres.AdapterCarte;
import autres.Carte;
import autres.Robot;

public class ScenarioActivity extends ActivityAvecMenu {

    private static final String FICHIER_SCENARIO = "scenario.txt";
    LinearLayout layoutFonction;
    ListView listViewScenario;
    public ArrayList<Carte> listeCarteScenario, listeCarte;
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
        Carte avancer = new Carte(this, Robot.AVANCER, "Avancer");
        Carte reculer = new Carte(this, Robot.RECULER, "Reculer");
        Carte tournerG = new Carte(this, Robot.TOURNER_A_GAUCHE, "Tourner à gauche");
        Carte tournerD = new Carte(this, Robot.TOURNER_A_DROITE, "Tourner à droite");
        Carte arreter = new Carte(this, Robot.ARRETER, "Arrêter");
        Carte tournerB = new Carte(this, Robot.TOURNER_LE_BRAS, "Tourner le bras");
        Carte pause = new Carte(this, Robot.PAUSE, "Pause");
        Carte detecterSon = new Carte(this, Robot.DETECTER_SON, "Détecter un son");
        Carte bip = new Carte(this, Robot.BIP, "Bipper");
        Carte musique = new Carte(this, Robot.MUSIQUE, "Musique");

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

        for(final Carte carte : listeCarte){
            carte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listeCarteScenario.add(carte);
                    adapter.notifyDataSetChanged();
                }
            });
            layoutFonction.addView(carte);
        }


        ImageView img_qrcode = new ImageView(this);
        img_qrcode.setImageResource(R.drawable.qrcode);
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
        layoutFonction.addView(img_qrcode);

        Carte carteEnvoyer = new Carte(this, "", "Envoyer");
        carteEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envoyerScenario();
            }
        });
        layoutFonction.addView(carteEnvoyer);
    }

    private void sauvegarderScenario(){
        FileOutputStream output = null;
        String userName = "Apollidore";

        try {
            output = openFileOutput(FICHIER_SCENARIO, MODE_PRIVATE);
            output.write(userName.getBytes());
            Toast.makeText(this, "SAUVEGARDE REUSSI", Toast.LENGTH_SHORT).show();
            if(output != null)
                output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Erreur sauvegarde ", Toast.LENGTH_SHORT).show();
        }

    }

    private void chargerScenario(){
        FileInputStream input = null;
        String userName = "";
        int octet;

        try {
            input = openFileInput(FICHIER_SCENARIO);
            while((octet = input.read()) != -1){
                userName = ""+new char[octet];
            }
            if(input != null)
                input.close();
            Toast.makeText(this, "LOAD REUSSI : "+userName, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Fichier non trouvé ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Erreur lecture sauvegarde ", Toast.LENGTH_SHORT).show();
        }

    }

    private void envoyerScenario() {
        for(Carte c : listeCarteScenario){
            Robot.envoyerCommande(this, c.getid());
        }
    }

    protected void checkResultFlashCode(String result){
        Carte c;
        switch(result){
            case "AVANCER":
                c = new Carte(this, Robot.AVANCER, "Avancer");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "RECULER":
                c = new Carte(this, Robot.RECULER, "Reculer");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "TOURNERGAUCHE":
                c = new Carte(this, Robot.TOURNER_A_GAUCHE, "Tourner à gauche");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "TOURNERDROITE":
                c = new Carte(this, Robot.TOURNER_A_DROITE, "Tourner à droite");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "ARRETER":
                c = new Carte(this, Robot.ARRETER, "Arrêter");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "TOURNERBRAS":
                c = new Carte(this, Robot.TOURNER_LE_BRAS, "Tourner le bras");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "PAUSE":
                c = new Carte(this, Robot.PAUSE, "Pause");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "DETECTERSON":
                c = new Carte(this, Robot.DETECTER_SON, "Détecter un son");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "BIP":
                c = new Carte(this, Robot.BIP, "Bipper");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "MUSIQUE":
                c = new Carte(this, Robot.MUSIQUE, "Musique");
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            default:
                Toast.makeText(this, "Le code flashé est mauvais !", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            checkResultFlashCode(data.getStringExtra("SCAN_RESULT"));
        }
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Action annulée", Toast.LENGTH_SHORT).show();
        }
    }

}