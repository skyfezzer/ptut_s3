package vue;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.widget.LinearLayout;

import com.example.i162174.robot.R;

import autres.Carte;

public class ButtonCarte extends AppCompatButton {

    private String nom;
    private Carte carte;

    public ButtonCarte(Context context, Carte c) {
        super(context);
        this.nom = nom;
        this.carte = c;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 5);
        setLayoutParams(params);
        this.setText(c.getNom());
        this.setTextColor(Color.parseColor("#FFFFFF"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setBackground(context.getDrawable(R.drawable.button_style));
        }
    }

    public String getNom() {
        return nom;
    }

    public Carte getCarte() {
        return carte;
    }

}
