package autres;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.i162174.robot.R;

public class Carte extends AppCompatButton {
    public String id, nom;

    public Carte(Context context, String id, String nom) {
        super(context);
        this.id = id;
        this.nom = nom;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 5);
        setLayoutParams(params);
        this.setText(nom);
        this.setTextColor(Color.parseColor("#FFFFFF"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setBackground(context.getDrawable(R.drawable.button_style));
        }
    }

    public String getid() {
        return id;
    }

    public String getNom() {
        return nom;
    }

}
