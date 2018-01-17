package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.i162174.robot.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import adapter.AdapterCarte;
import autres.BottomNavigationViewHelper;
import dialog.DialogSauvegardeScenario;
import vue.ButtonCarte;
import autres.Carte;
import autres.Robot;

public class ScenarioActivity extends Activity {

    private LinearLayout layoutFonction;
    private ListView listViewScenario;
    public ArrayList<ButtonCarte> listeCarte;
    public ArrayList<Carte> listeCarteScenario;
    public AdapterCarte adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        initNavigationMenu();

        layoutFonction = (LinearLayout) findViewById(R.id.layoutFonction);
        listViewScenario = (ListView) findViewById(R.id.listViewScenario);
        Button btn_save = (Button) findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sauvegarderScenario();
            }
        });

        listeCarteScenario = new ArrayList<>();

        adapter = new AdapterCarte(this, listeCarteScenario);
        listViewScenario.setAdapter(adapter);

        initialisationListeFonctionEtCarte();

        String nomScenario = getIntent().getStringExtra("nomScenario");
        if(nomScenario != null) load(nomScenario);
    }

    private void initialisationListeFonctionEtCarte() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String loadListeCarteJSON = preferences.getString("ListeCarteJSON", "");

        Type type = new TypeToken<ArrayList<ButtonCarte>>(){}.getType();
        ArrayList<Carte> loadListeCarte;
        if(loadListeCarteJSON.equals("")) loadListeCarte = new ArrayList<Carte>();
        else loadListeCarte = new Gson().fromJson(loadListeCarteJSON, type);

        listeCarte = new ArrayList<>();
        listeCarte.add(new ButtonCarte(this, new Carte("Avancer", Robot.AVANCER, 2, new String[]{"Durée (s)", "Vitesse"})));
        listeCarte.add(new ButtonCarte(this, new Carte("Reculer", Robot.RECULER, 2, new String[]{"Durée (s)", "Vitesse"})));
        listeCarte.add(new ButtonCarte(this, new Carte("Tourner a gauche", Robot.TOURNER_A_GAUCHE, 1, new String[]{"Degrée (°)"})));
        listeCarte.add(new ButtonCarte(this, new Carte("Tourner a droite", Robot.TOURNER_A_DROITE, 1, new String[]{"Degrée (°)"})));
        listeCarte.add(new ButtonCarte(this, new Carte("Arreter", Robot.ARRETER, 0, null)));
        listeCarte.add(new ButtonCarte(this, new Carte("Tourner le bras", Robot.TOURNER_LE_BRAS, 0, null)));
        listeCarte.add(new ButtonCarte(this, new Carte("Pause", Robot.PAUSE, 1, new String[]{"Durée (s)"})));
        listeCarte.add(new ButtonCarte(this, new Carte("Detecter un son", Robot.DETECTER_SON, 0, null)));
        listeCarte.add(new ButtonCarte(this, new Carte("Bipper", Robot.BIP, 0, null)));
        listeCarte.add(new ButtonCarte(this, new Carte("Jouer Musique", Robot.MUSIQUE, 0, null)));
        for(Carte c : loadListeCarte)
            listeCarte.add(new ButtonCarte(this, c));


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

        Button btn_envoyer = (Button)findViewById(R.id.btn_envoyer);
        btn_envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envoyerScenario();
            }
        });
    }

    private void sauvegarderScenario(){
        if(listeCarteScenario.isEmpty())
            Toast.makeText(this, "Le scénario est vide !", Toast.LENGTH_SHORT).show();
        else
            new DialogSauvegardeScenario(this).show();
    }

    public void save(String nameFile){
        FileOutputStream output;
        String json = new Gson().toJson(listeCarteScenario);
        try {
            output = openFileOutput(nameFile, MODE_PRIVATE);
            output.write(json.getBytes());
            output.close();
            Toast.makeText(this, "Sauvegarde réussi !", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not Found exception", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Erreur sauvegarde !", Toast.LENGTH_SHORT).show();
        }
    }

    public void load(String nomFichier){
        try {
            FileInputStream fis = openFileInput(nomFichier);
            int i;
            StringBuilder readFile = new StringBuilder();
            while((i = fis.read()) != -1) {
                readFile.append((char) i);
            }
            fis.close();
            Type type = new TypeToken<ArrayList<Carte>>(){}.getType();
            listeCarteScenario = new Gson().fromJson(readFile.toString(), type);
            adapter = new AdapterCarte(this, listeCarteScenario);
            listViewScenario.setAdapter(adapter);
            Toast.makeText(this, "Chargement scénario effectué", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void envoyerScenario() {
        for(Carte c : listeCarteScenario){
            if(c.getNbParametre() == 0) Robot.envoyerCommande(this, c.getActionRobot());
            else{
                String command = c.getActionRobot();
                for(String s : c.getParametres())
                    command += s;
                Robot.envoyerCommande(this, command);
            }
        }
    }

    protected void checkResultFlashCode(String result){
        Carte c;
        switch(result){
            case "AVANCER":
                c = new Carte("Avancer", Robot.AVANCER, 2, new String[]{"Durée (s)", "Vitesse"});
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "RECULER":
                c = new Carte("Reculer", Robot.RECULER, 2, new String[]{"Durée (s)", "Vitesse"});
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "TOURNERGAUCHE":
                c = new Carte("Tourner a gauche", Robot.TOURNER_A_GAUCHE, 1, new String[]{"Degrée (°)"});
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "TOURNERDROITE":
                c = new Carte("Tourner a droite", Robot.TOURNER_A_DROITE, 1, new String[]{"Degrée (°)"});
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "ARRETER":
                c = new Carte("Arreter", Robot.ARRETER, 0, null);
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "TOURNERBRAS":
                c = new Carte("Tourner le bras", Robot.TOURNER_LE_BRAS, 0, null);
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "PAUSE":
                c = new Carte("Pause", Robot.PAUSE, 1, new String[]{"Durée (s)"});
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "DETECTERSON":
                c = new Carte("Detecter un son", Robot.DETECTER_SON, 0, null);
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "BIP":
                c = new Carte("Bipper", Robot.BIP, 0, null);
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            case "MUSIQUE":
                c = new Carte("Jouer Musique", Robot.MUSIQUE, 0, null);
                listeCarteScenario.add(c);
                adapter.notifyDataSetChanged();
                break;
            default:
                Toast.makeText(this, "Le code flashé est mauvais !", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initNavigationMenu() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        final String nameClass = getClass().getName();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_scenario:
                        break;
                    case R.id.navigation_joystick:
                        startActivity(new Intent(ScenarioActivity.this, ControlleurActivity.class));
                        finish();
                        break;
                    case R.id.navigation_enregistrement:
                        startActivity(new Intent(ScenarioActivity.this, EnregistrementActivity.class));
                        finish();
                        break;
                    case R.id.navigation_apropos:
                        startActivity(new Intent(ScenarioActivity.this, CreditActivity.class).putExtra("From", nameClass));                        finish();
                        break;
                    case R.id.navigation_quitter:
                        Robot.deconnectionRobot(ScenarioActivity.this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            finishAndRemoveTask();
                        }else
                            finish();
                        System.exit(0);
                        break;
                }
                return false;
            }
        });
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