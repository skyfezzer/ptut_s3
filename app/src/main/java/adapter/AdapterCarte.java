package adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i162174.robot.R;

import java.util.ArrayList;

import activity.ScenarioActivity;
import autres.Carte;

public class AdapterCarte extends BaseAdapter{

    private ArrayList<Carte> data;
    private ScenarioActivity context;

    public AdapterCarte(ScenarioActivity pContext, ArrayList<Carte> pData) {
        this.data = pData;
        this.context= pContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int indice, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View v = View.inflate(context, R.layout.item_listview_scenario, null);
        TextView txt = (TextView) v.findViewById(R.id.txt_list);
        txt.setText(data.get(indice).getNom());
        txt.setTextColor(Color.parseColor("#FFFFFF"));

        ImageView imgParametre = (ImageView) v.findViewById(R.id.img_parametre);
        imgParametre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(indice).getNbParametre() != 0){
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_parametres);
                    dialog.setTitle("Changez les paramètre de la carte");

                    LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.layout);

                    final EditText[] tabEditText = new EditText[data.get(indice).getNbParametre()];

                    for(int i = 0; i < tabEditText.length; i++){
                        LinearLayout linearlayout = new LinearLayout(context);
                        linearlayout.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        linearlayout.setLayoutParams(linearlayoutlayoutparams);

                        TextView nomParam = new TextView(context);
                        nomParam.setText(data.get(indice).getNomParametres(i));

                        EditText editText = new EditText(context);
                        editText.setText(data.get(indice).getParametre(i));
                        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        tabEditText[i] = editText;

                        linearlayout.addView(nomParam);
                        linearlayout.addView(editText);

                        layout.addView(linearlayout);
                    }

                    Button buttonOk = new Button(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.CENTER;
                    buttonOk.setLayoutParams(params);
                    buttonOk.setText("Valider");
                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(int i = 0; i < tabEditText.length; i++)
                                data.get(indice).setParametre(i, tabEditText[i].getText().toString());
                            dialog.dismiss();
                        }
                    });
                    layout.addView(buttonOk);

                    dialog.show();
                }else Toast.makeText(context, "Pas de paramètre pour cette fonction !", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView imgTrash = (ImageView) v.findViewById(R.id.img_trash);
        imgTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.listeCarteScenario.remove(indice);
                notifyDataSetChanged();
            }
        });
        v.setTag(indice);
        return v;
    }

}