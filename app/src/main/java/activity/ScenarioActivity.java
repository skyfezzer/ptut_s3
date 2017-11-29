package activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.i162174.robot.R;

import java.util.ArrayList;

import autres.AdapterCarte;
import autres.Carte;
import autres.Robot;

public class ScenarioActivity extends ActivityAvecMenu {

    LinearLayout layoutFonction;
    ListView listViewScenario;
    public ArrayList<Carte> listeCarteScenario, listeCarte;
    public AdapterCarte adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        initialisationMenu();

        ImageView img_qrcode = (ImageView) findViewById(R.id.img_qrcode);
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

        layoutFonction = (LinearLayout) findViewById(R.id.layoutFonction);
        listViewScenario = (ListView) findViewById(R.id.listViewScenario);
        listeCarteScenario = new ArrayList<Carte>();

        adapter = new AdapterCarte(this, listeCarteScenario);
        listViewScenario.setAdapter(adapter);

        initialisationListeFonction();

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
    }

    private void initialisationListeFonction() {
        Carte avancer = new Carte(this, Robot.AVANCER, "Avancer");
        Carte reculer = new Carte(this, Robot.RECULER, "Reculer");
        Carte tournerG = new Carte(this, Robot.TOURNER_A_GAUCHE, "Tourner a gauche");
        Carte tournerD = new Carte(this, Robot.TOURNER_A_DROITE, "Tourner a droite");
        Carte arreter = new Carte(this, Robot.ARRETER, "Arreter");
        Carte tournerB = new Carte(this, Robot.TOURNER_LE_BRAS, "Tourner le bras");
        Carte pause = new Carte(this, Robot.PAUSE, "Pause");
        Carte detecterSon = new Carte(this, Robot.DETECTER_SON, "Détecter un son");
        Carte bip = new Carte(this, Robot.BIP, "Bipper");
        Carte musique = new Carte(this, Robot.MUSIQUE, "Musique");

        listeCarte = new ArrayList<Carte>();
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
    }

    protected void checkResultFlashCode(String result){
        switch(result){
            case "AVANCER":
                Carte c = new Carte(this, Robot.AVANCER, "Avancer");
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