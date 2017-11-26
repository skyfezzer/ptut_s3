package autres;

import android.content.Context;
import android.graphics.Color;

public class Carte extends android.support.v7.widget.AppCompatButton {
    public int id;
    public String nom;
    public boolean dansListe;

    public Carte(Context context, int id, String nom) {
        super(context);
        this.id = id;
        this.nom = nom;
        this.setText(nom);
        this.setTextSize(17);
        this.setTextColor(Color.parseColor("#339933"));
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isDansListe() {
        return dansListe;
    }

    public void setDansListe(boolean b){
        dansListe = b;
    }
}
