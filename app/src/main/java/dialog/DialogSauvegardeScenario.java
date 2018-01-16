package dialog;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import java.io.File;

import activity.ScenarioActivity;

public class DialogSauvegardeScenario extends AlertDialog.Builder{

    public DialogSauvegardeScenario(@NonNull final ScenarioActivity context) {
        super(context);

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        setView(input);
        setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File repertoire = context.getFilesDir();
                File[] files = repertoire.listFiles();

                String nomFichier = input.getText().toString();
                Boolean fichierExiste = false;
                for(File file : files){
                    if(nomFichier.equals(file.getName())){
                        creerDialogRemplacer(context, nomFichier);
                        fichierExiste = true;
                    }
                }
                if(!fichierExiste)
                    context.save(nomFichier);
            }
        });
        setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    private void creerDialogRemplacer(final ScenarioActivity context, final String nomFichier) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Fichier déjà existant")
                .setMessage("Le fichier existe déjà, voulez-vous le remplacer ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.save(nomFichier);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
